package com.linkedin.studentclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class StudentClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentClientApplication.class, args);
    }

    @Bean
    WebClient webClient(WebClient.Builder builder) {
        return builder.baseUrl("http://localhost:8080").build();
    }
}
