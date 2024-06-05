package com.pplanello.learning.spring.project.reactor.subscribevspublish;

import reactor.core.scheduler.Scheduler;

/**
 * This is a placeholder for the comparison between the {@code subscribeOn} and {@code publishOn} operators.
 *
 * <p>
 * - {@code subscribeOn}: Run subscribe, onSubscribe and request on a specified Scheduler's Scheduler.Worker.
 * As such, placing this operator anywhere in the chain will also impact the execution context of
 * onNext/onError/onComplete signals from the beginning of the chain up to the next occurrence of a publishOn.
 * <br>
 * Typically used for slow publisher e.g., blocking IO, fast consumer(s) scenarios.
 * </p>
 * <p>
 * - {@code publishOn}: Run onNext, onComplete and onError on a supplied {@link Scheduler} {@link Scheduler.Worker Worker}.
 * This operator influences the threading context where the rest of the operators in the chain below it will execute,
 * up to a new occurrence of publishOn.
 * <br>
 * Typically used for fast publisher, slow consumer(s) scenarios.
 * </p>
 */
public class SubscribeOnVsPublishOn {

    public static void main(String[] args) {
        //TODO: implement
    }
}
