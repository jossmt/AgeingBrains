package com.brain.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.brain.core"})
public class BrainApplication {

    public static void main(String[] args) {
        SpringApplication.run(BrainApplication.class, args);
    }

}
