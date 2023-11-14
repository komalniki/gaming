package com.game;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.game.repository")
@SpringBootApplication
@ComponentScan
@EnableAutoConfiguration
public class GamingApplication {
    public static void main(String[] args) {
        SpringApplication.run(GamingApplication.class, args);
    }
}