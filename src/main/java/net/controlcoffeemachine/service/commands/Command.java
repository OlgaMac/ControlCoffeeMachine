package net.controlcoffeemachine.service.commands;


import net.controlcoffeemachine.config.StateMachineConfig.States;
import net.controlcoffeemachine.config.StateMachineConfig.Events;
import org.springframework.statemachine.action.Action;

public interface Command extends Action<States, Events> {
    Events getType();
}
