package net.controlcoffeemachine.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import net.controlcoffeemachine.model.Supplies;
import net.controlcoffeemachine.model.coffee.CoffeeRecipe;
import net.controlcoffeemachine.model.coffee.CoffeeType;
import net.controlcoffeemachine.util.aspect.DatabaseLogging;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

@Component
//@DependsOn({"dataSource"})
@Slf4j
@RequiredArgsConstructor
public class CoffeeMachineEquipment implements Machine {
    private final Map<CoffeeType, CoffeeRecipe> coffeeFactory;
    private final Supplies supplies;
    private final ObjectFactory<ExecutorService> prototypeBeanObjectFactory;

    private ExecutorService equipment;
    @Getter
    private CompletableFuture<Boolean> runningTask;

    public ExecutorService startEquipment() {
        return prototypeBeanObjectFactory.getObject();
    }

    @Override
    @DatabaseLogging
    public String turnOn() {
        equipment = startEquipment();
        return "Turn on equipment";
    }

    @Override
    @DatabaseLogging
    public String make(CoffeeType coffeeType) {
        CoffeeRecipe coffeeRecipe = coffeeFactory.get(coffeeType);
        if (!supplies.isEnoughFor(coffeeRecipe)) {
            return String.format("Not enough ingredients for %s: %s", coffeeRecipe.getType(), supplies.getNotEnough());
        }

        supplies.allocate(coffeeRecipe);
        startTask(coffeeRecipe.getTimeToMake());
        return String.format("Start making coffee %s", coffeeRecipe.getType());
    }

    @Override
    @DatabaseLogging
    public String clean() {
        startTask(6000);
        return "Start cleaning coffee machine";
    }

    @Override
    @DatabaseLogging
    public String remainsSupplies() {
        return supplies.toString();
    }

    @Override
    @DatabaseLogging
    public String turnOff() {
        shutDownCoffeeMachineService();
        return "Turn of equipment";
    }

    @Override
    public void afterTask(Consumer<Boolean> action) {
        runningTask.thenAccept(action);
    }

    private boolean processing(int millis) {
        try {
            Thread.sleep(millis);
            return true;
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
        return false;
    }

    private void startTask(int millis) {
        runningTask = CompletableFuture
                .supplyAsync(() -> processing(millis), equipment);
    }

    private void shutDownCoffeeMachineService() {
        // Disable new tasks from being submitted
        equipment.shutdown();
        try {
            // Wait a while for existing tasks to terminate
            if (!equipment.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                // Cancel currently executing tasks
                equipment.shutdownNow();
            }
        } catch (InterruptedException ie) {
            // (Re-)Cancel if current thread also interrupted
            equipment.shutdownNow();
            // Preserve interrupt status
            Thread.currentThread().interrupt();
        } finally {
            equipment = null;
        }
    }
}