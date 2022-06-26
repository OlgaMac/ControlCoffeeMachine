package net.controlcoffeemachine.service.commands;

import lombok.RequiredArgsConstructor;

import net.controlcoffeemachine.config.StateMachineConfig.Events;
import net.controlcoffeemachine.config.StateMachineConfig.States;
import net.controlcoffeemachine.service.Machine;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RemainsCommand implements Command {

    private final Machine coffeeMachine;

    @Override
    public void execute(StateContext<States, Events> stateContext) {
        stateContext.getExtendedState().getVariables().put("info", coffeeMachine.remainsSupplies());
    }

    @Override
    public Events getType() {
        return Events.REMAINING;
    }
}
