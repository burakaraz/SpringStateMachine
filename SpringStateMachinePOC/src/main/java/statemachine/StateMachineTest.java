package statemachine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;

@SpringBootApplication
public class StateMachineTest implements ApplicationRunner {

    @Autowired
    private StateMachine<States, Events> stateMachine;

    public static void main(String[] args) {
        SpringApplication.run(StateMachineTest.class, args);
    }

    @Bean
    public StateMachineListener stateMachineListener() {
        StateMachineListener listener = new StateMachineListener();
        stateMachine.addStateListener(listener);
        return listener;
    }

    @Override
    public void run(ApplicationArguments applicationArguments) {
        stateMachine.start();
        boolean b = stateMachine.sendEvent(Events.DOWNLOAD);
        if (!b) {
            throw new IllegalStateException(
                    "Event is not accepted for current State: " + stateMachine.getState() + " event: "
                            + Events.ACTIVATING);
        }
        State<States, Events> state = stateMachine.getState();
        System.out.println(state.getId());
    }
}
