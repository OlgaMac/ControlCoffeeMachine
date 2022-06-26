package net.controlcoffeemachine.service;



import net.controlcoffeemachine.model.coffee.CoffeeType;

import java.util.function.Consumer;

public interface Machine {
    String turnOn();
    String turnOff();
    String clean();
    String make(CoffeeType type);
    String remainsSupplies();
    void afterTask(Consumer<Boolean> action);
}
