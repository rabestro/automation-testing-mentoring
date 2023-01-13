package com.linkedin.studentclient;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;

import static org.assertj.core.api.BDDAssertions.then;
import static org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties.StubsMode.LOCAL;

@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@AutoConfigureStubRunner(ids = "com.linkedin.student-service:student-service:+:8080", stubsMode = LOCAL)
class StudentClientTest {
    @Autowired
    private StudentClient studentClient;

    @Test
    void get_student_for_given_student_id() {
        //given
        var id = 1L;

        //when
        var student = studentClient.getStudent(id);

        then(student.getId())
                .isNotNull();

        then(student.getName())
                .isEqualTo("Mark");

        then(student.getGrade())
                .isEqualTo(10);
    }
}
