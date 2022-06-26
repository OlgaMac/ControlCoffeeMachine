package net.controlcoffeemachine.model.ingredients;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public abstract class Ingredients {
    protected int water;
    protected int milk;
    protected int beans;
}
