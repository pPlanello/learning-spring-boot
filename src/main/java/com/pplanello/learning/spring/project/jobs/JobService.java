package com.pplanello.learning.spring.project.jobs;

import org.springframework.stereotype.Service;

@Service
public class JobService {

    public void executeJob() {
        System.out.println("JobService.executeJob");
    }
}
