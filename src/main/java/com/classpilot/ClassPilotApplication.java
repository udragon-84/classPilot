package com.classpilot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ClassPilotApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClassPilotApplication.class, args);
    }

}
