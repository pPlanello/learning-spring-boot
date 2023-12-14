package com.pplanello.learning.spring.project.jobs;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class JobScheduler {

    @Scheduled(fixedDelayString = "${app.scheduler.delay-ms}")
    public void execute() {
        System.out.println("JobScheduler.execute");
        jobService.executeJob();
    }

    public JobScheduler(JobService jobService) {
        this.jobService = jobService;
    }

    private final JobService jobService;
}
