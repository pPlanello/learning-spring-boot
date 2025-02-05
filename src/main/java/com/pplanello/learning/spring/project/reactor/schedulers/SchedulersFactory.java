package com.pplanello.learning.spring.project.reactor.schedulers;

import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.contextpropagation.ObservationThreadLocalAccessor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Hooks;
import reactor.core.scheduler.Scheduler;

import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.Collection;

import static reactor.core.scheduler.Schedulers.fromExecutorService;

@Component
public class SchedulersFactory {

    public Scheduler createReactorScheduler(String executorServiceName, Integer numberOfThreads, String threadNamePrefix) {
        var executorService = executorServiceFactory.createExecutorService(executorServiceName, numberOfThreads, threadNamePrefix);
        var scheduler = fromExecutorService(executorService);
        reactorSchedulers.add(scheduler);
        return scheduler;
    }

    @PreDestroy
    public void shutdown() {
        reactorSchedulers.forEach(Scheduler::dispose);
        reactorSchedulers.clear();
    }

    private void configureTracePropagationInReactor() {
        Hooks.enableAutomaticContextPropagation();
        ObservationThreadLocalAccessor.getInstance().setObservationRegistry(observationRegistry);
    }

    public SchedulersFactory(ExecutorServiceFactory executorServiceFactory,
                             ObservationRegistry observationRegistry) {
        this.executorServiceFactory = executorServiceFactory;
        this.observationRegistry = observationRegistry;

        configureTracePropagationInReactor();
    }

    private final ExecutorServiceFactory executorServiceFactory;
    private final ObservationRegistry observationRegistry;
    private final Collection<Scheduler> reactorSchedulers = new ArrayList<>();
}
