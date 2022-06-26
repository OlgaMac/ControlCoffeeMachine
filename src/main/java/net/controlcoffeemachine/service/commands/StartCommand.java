package net.controlcoffeemachine.service.commands;

import lombok.RequiredArgsConstructor;

import net.controlcoffeemachine.service.Machine;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;
import net.controlcoffeemachine.config.StateMachineConfig.States;
import net.controlcoffeemachine.config.StateMachineConfig.Events;

@Component
@RequiredArgsConstructor
public class StartCommand implements Command {

    private final Machine coffeeMachine;

    @Override
    public void execute(StateContext<States, Events> stateContext) {
        String info = coffeeMachine.turnOn();
        stateContext.getExtendedState().getVariables().put("info", info);
    }

    @Override
    public Events getType() {
        return Events.STARTING;
    }
}