package com.pplanello.learning.spring.project.reactor.schedulers;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class CreateFixedPoolExecutor {

    public static ExecutorService apply(Integer numberOfThreads, String namePrefix) {
        return Executors.newFixedThreadPool(numberOfThreads, createThreadFactory(namePrefix));
    }

    private static ThreadFactory createThreadFactory(String namePrefix) {
        return new ThreadFactoryBuilder()
            .setNameFormat(namePrefix + "-%d")
            .build();
    }
}
