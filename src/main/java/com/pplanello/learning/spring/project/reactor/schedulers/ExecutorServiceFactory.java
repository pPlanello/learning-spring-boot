package com.pplanello.learning.spring.project.reactor.schedulers;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.binder.jvm.ExecutorServiceMetrics;
import io.micrometer.tracing.Tracer;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutorService;

@Component
public class ExecutorServiceFactory {

    public ExecutorService createExecutorService(String executorServiceName, Integer numberOfThreads, String threadNamePrefix) {
        var executorService = CreateFixedPoolExecutor.apply(numberOfThreads, threadNamePrefix);
        this.executorServices.add(executorService);

        new ExecutorServiceMetrics(executorService, executorServiceName, Tags.empty()).bindTo(meterRegistry);

        return tracer.currentTraceContext().wrap(executorService);
    }

    @PreDestroy
    public void shutdown() {
        executorServices.forEach(ExecutorService::shutdown);

        this.executorServices.clear();
    }

    public ExecutorServiceFactory(MeterRegistry meterRegistry,
                                  Tracer tracer) {
        this.meterRegistry = meterRegistry;
        this.tracer = tracer;
    }

    private final MeterRegistry meterRegistry;
    private final Tracer tracer;

    private final Collection<ExecutorService> executorServices = new ArrayList<>();
}
