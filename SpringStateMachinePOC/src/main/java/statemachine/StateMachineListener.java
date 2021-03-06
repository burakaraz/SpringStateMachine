package statemachine;

import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;


public class StateMachineListener extends StateMachineListenerAdapter<States, Events> {

    @Override
    public void stateChanged(State<States, Events> from, State<States, Events> to) {
        System.out.printf("Transitioned from %s to %s%n", from == null ?
                "none" : from.getId(), to.getId());
    }

}
