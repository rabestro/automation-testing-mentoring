package com.linkedin.studentservice;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@Transactional
@SpringBootTest(webEnvironment = NONE)
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

    @Test
    void trying_to_get_student_by_non_existing_id() {
        // given
        var nonExistingId = -1L;

        //when
        var throwable = catchThrowable(
                () -> studentService.getStudentById(nonExistingId)
        );

        then(throwable)
                .as("the student service thrown an exception")
                .isInstanceOf(StudentNotFoundException.class);
    }
}
