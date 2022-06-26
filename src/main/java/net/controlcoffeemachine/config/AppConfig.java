package net.controlcoffeemachine.config;

import lombok.extern.slf4j.Slf4j;

import net.controlcoffeemachine.model.coffee.CoffeeRecipe;
import net.controlcoffeemachine.model.coffee.CoffeeType;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Scope;


import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
@EnableAspectJAutoProxy
@Slf4j
public class AppConfig {

    @Bean
    public Map<CoffeeType, CoffeeRecipe> coffeeFactory(List<CoffeeRecipe> coffeeRecipeList) {
        log.info("Start coffee factory");
        return coffeeRecipeList.stream().collect(Collectors.toMap(CoffeeRecipe::getType, Function.identity()));
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public ExecutorService coffeeMachineService() {
        log.info("Start coffee machine service");
        return Executors.newSingleThreadExecutor();
    }
}
