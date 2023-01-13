package com.linkedin.studentclient;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class StudentClient {
    private final WebClient webClient;

    public Student getStudent(Long studentId) {
        return webClient
                .get()
                .uri("/students/{studentId}", studentId)
                .retrieve()
                .bodyToMono(Student.class)
                .block();
    }
}
