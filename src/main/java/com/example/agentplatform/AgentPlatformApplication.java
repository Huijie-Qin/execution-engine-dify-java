package com.example.agentplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main bootstrap class for the Agent Platform application. It starts the Spring Boot context
 * with WebFlux, MyBatis, and the workflow engine infrastructure configured by auto configuration
 * components.
 */
@SpringBootApplication
public class AgentPlatformApplication {

    /**
     * Entry point for the Agent Platform application.
     *
     * @param args standard Spring Boot command line arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(AgentPlatformApplication.class, args);
    }
}
