package com.linkedin.studentservice;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@SpringBootTest(webEnvironment = NONE)
@Transactional
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StudentServiceTest {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentService studentService;

    @Test
    void returning_saved_student_from_service_layer() {
        //given
        var savedStudent = studentRepository.save(new Student(null, "Mark"));

        //when
        var retrievedStudent = studentService.getStudentById(savedStudent.getId());

        then(retrievedStudent.getName())
                .isEqualTo("Mark");

        then(retrievedStudent.getId())
                .isNotNull();
    }
}



