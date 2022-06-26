package net.controlcoffeemachine.model.coffee;

import lombok.Getter;
import net.controlcoffeemachine.model.ingredients.Ingredients;


@Getter
public abstract class CoffeeRecipe extends Ingredients {
    protected int timeToMake;

    public abstract CoffeeType getType();
}
