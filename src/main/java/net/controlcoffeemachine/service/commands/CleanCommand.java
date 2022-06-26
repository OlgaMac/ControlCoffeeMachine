package net.controlcoffeemachine.service.commands;

import lombok.RequiredArgsConstructor;

import net.controlcoffeemachine.service.Machine;
import org.springframework.messaging.support.MessageBuilder;

import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import net.controlcoffeemachine.config.StateMachineConfig.States;
import net.controlcoffeemachine.config.StateMachineConfig.Events;

@Component
@RequiredArgsConstructor
public class CleanCommand implements Command {

    private final Machine coffeeMachine;

    @Override
    public void execute(StateContext<States, Events> stateContext) {
        String info = coffeeMachine.clean();
        stateContext.getExtendedState().getVariables().put("info", info);
        coffeeMachine.afterTask(result -> {
            if (result) {
                stateContext.getStateMachine()
                        .sendEvent(Mono.just(MessageBuilder
                                .withPayload(Events.DONE).build()))
                        .subscribe();
            }
        });
    }

    @Override
    public Events getType() {
        return Events.CLEANING;
    }
}
