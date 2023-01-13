package com.linkedin.studentclient;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest
@AutoConfigureWireMock
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StudentClientTest {
    @Autowired
    private StudentClient studentClient;

    @Test
    void get_student_for_given_student_id() {
        //given
        var id = 1L;

        stubFor(get("/students/" + id).willReturn(okJson("""
                {
                  "id": 1,
                  "studentName": "Mark",
                  "grade": 10
                }
                """)));

        //when
        var student = studentClient.getStudent(id);

        //then
        then(student.getId())
                .isNotNull();

        then(student.getStudentName())
                .isEqualTo("Mark");

        then(student.getGrade())
                .isEqualTo(10);
    }
}
