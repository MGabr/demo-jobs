package com.github.mgabr.demojobs;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

@TestConfiguration
public class DemoJobsTestConfiguration {

    @Bean
    @Primary
    public Clock testClock() {
        return Clock.fixed(Instant.parse("2021-06-08T08:00:00.000Z"), ZoneOffset.UTC);
    }
}
