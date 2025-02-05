package com.pplanello.learning.spring.project.reactor.schedulers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.scheduler.Scheduler;

/**
 * This is a class to configure the schedulers of all project.
 */
@Configuration
public class SchedulerConfig {

    @Bean("example1Scheduler")
    public Scheduler getExample1Scheduler(SchedulersFactory schedulersFactory) {
        return schedulersFactory.createReactorScheduler(
                "example1-thread-pool",
                2,
                "example1-thread-pool");
    }
}
