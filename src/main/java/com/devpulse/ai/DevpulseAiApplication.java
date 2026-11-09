package com.devpulse.ai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DevpulseAiApplication {

    public static void main(String[] args) {
        SpringApplication.run(DevpulseAiApplication.class, args);
    }

}
