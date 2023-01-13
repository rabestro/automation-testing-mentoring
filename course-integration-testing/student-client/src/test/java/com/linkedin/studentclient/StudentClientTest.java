package com.linkedin.studentclient;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;

import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest
@AutoConfigureWireMock
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StudentClientTest {
    @Autowired
    private StudentClient studentClient;

    @Test
    void get_student_for_given_student() {
        //given
        Long studentId = 1L;

        //when
        Student student = studentClient.getStudent(studentId);

        //then
        then(student.getId())
                .isNotNull();

        then(student.getStudentName())
                .isEqualTo("Mark");

        then(student.getGrade())
                .isEqualTo(10);
    }
}
