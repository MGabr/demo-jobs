package com.github.mgabr.demojobs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import java.time.Clock;

@SpringBootApplication
@EnableWebSecurity
public class DemoJobsApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoJobsApplication.class, args);
    }

    @Bean
    public Clock clock() {
        return Clock.systemUTC();
    }
}
