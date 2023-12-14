package com.pplanello.learning.spring.project.jobs;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.Duration;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.*;


@SpringBootTest(
    classes = ScheduledConfig.class,
    properties = {
        "app.scheduler.delay-ms=1500"
    })
@ExtendWith(MockitoExtension.class)
class JobSchedulerIntegratedTest {

    @Test
    void it_should_execute_one_time() {
        await()
            .atMost(Duration.ofMillis(1_500))
            .untilAsserted(() -> verify(jobService, only()
                .description("It should execute one time"))
                .executeJob());
    }


    @Test
    void it_should_execute_two_times() {
        await()
            .atMost(Duration.ofMillis(3_000))
            .untilAsserted(() -> verify(jobService, times(2)
                .description("It should execute two times"))
                .executeJob());
    }

    @AfterEach
    void clearMockito() {
        reset(jobService);
    }

    @MockBean
    private JobService jobService;
}