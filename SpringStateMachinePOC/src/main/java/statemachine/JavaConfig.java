package statemachine;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.Arrays;
import java.util.HashSet;


@Configuration
@EnableStateMachine
public class JavaConfig extends StateMachineConfigurerAdapter<States, Events> {

    @Bean
    public Action<States, Events> initAction() {
        return ctx -> System.out.println(ctx.getTarget().getId());
    }

    @Bean
    public Action<States, Events> errorAction() {
        return ctx -> System.out.println(
                "Error " + ctx.getSource().getId() + ctx.getException());
    }

    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states)
            throws Exception {

        states
                .withStates()
                .initial(States.CREATED)
                .end(States.ACTIVATED)
                .end(States.CANCELLED)
                .end(States.FAILED)
                .states(
                        new HashSet<>(
                                Arrays.asList(States.DOWNLOAD, States.DOWNLOADING, States.DOWNLOADED, States.INSTALL,
                                        States.INSTALLING,
                                        States.INSTALLED, States.ACTIVATE, States.ACTIVATING)));

    }

    @Override
    public void configure(
            StateMachineTransitionConfigurer<States, Events> transitions)
            throws Exception {

        transitions
                .withExternal()
                .source(States.CREATED)
                .target(States.DOWNLOAD)
                .event(Events.DOWNLOAD).action(initAction(), errorAction())
                .and()
                .withExternal()
                .source(States.CREATED)
                .target(States.CANCELLED)
                .event(Events.CANCELLED).action(initAction(), errorAction())
                .and()
                .withExternal()
                .source(States.DOWNLOAD)
                .target(States.DOWNLOADING)
                .event(Events.DOWNLOADING).action(initAction(), errorAction())
                .and()
                .withExternal()
                .source(States.DOWNLOADING)
                .target(States.DOWNLOADING)
                .event(Events.DOWNLOADING).action(initAction())
                .and()
                .withExternal()
                .source(States.DOWNLOADING)
                .target(States.DOWNLOADED)
                .event(Events.DOWNLOADED).action(initAction())
                .and()
                .withExternal()
                .source(States.DOWNLOADING)
                .target(States.FAILED)
                .event(Events.FAILED).action(initAction())
                .and()
                .withExternal()
                .source(States.DOWNLOADED)
                .target(States.INSTALL)
                .event(Events.INSTALL).action(initAction())
                .and()
                .withExternal()
                .source(States.DOWNLOADED)
                .target(States.CANCELLED)
                .event(Events.CANCELLED).action(initAction())
                .and()
                .withExternal()
                .source(States.INSTALL)
                .target(States.INSTALLING)
                .event(Events.INSTALLING).action(initAction())
                .and()
                .withExternal()
                .source(States.INSTALLING)
                .target(States.INSTALLING)
                .event(Events.INSTALLING).action(initAction())
                .and()
                .withExternal()
                .source(States.INSTALLED)
                .target(States.INSTALLED)
                .event(Events.INSTALLED).action(initAction())
                .and()
                .withExternal()
                .source(States.INSTALLING)
                .target(States.FAILED)
                .event(Events.FAILED).action(initAction())
                .and()
                .withExternal()
                .source(States.INSTALLED)
                .target(States.ACTIVATE)
                .event(Events.ACTIVATE).action(initAction())
                .and()
                .withExternal()
                .source(States.INSTALLED)
                .target(States.CANCELLED)
                .event(Events.CANCELLED).action(initAction())
                .and()
                .withExternal()
                .source(States.ACTIVATE)
                .target(States.ACTIVATING)
                .event(Events.ACTIVATING).action(initAction())
                .and()
                .withExternal()
                .source(States.ACTIVATING)
                .target(States.ACTIVATING)
                .event(Events.ACTIVATING).action(initAction())
                .and()
                .withExternal()
                .source(States.ACTIVATING)
                .target(States.FAILED)
                .event(Events.FAILED).action(initAction())
                .and()
                .withExternal()
                .source(States.ACTIVATING)
                .target(States.ACTIVATED)
                .event(Events.ACTIVATED).action(initAction());
    }

}
