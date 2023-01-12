package com.linkedin.studentservice;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.stream.Stream;

import static org.assertj.core.api.BDDAssertions.then;

@DataJpaTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StudentRepositoryTest {
    public static final Offset<Double> DELTA = Offset.offset(0.1);

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void get_student_by_name() {
        //given
        var savedStudent = testEntityManager
                .persistFlushFind(new Student(null, "Mark"));

        //when
        var student = studentRepository.getStudentByName("Mark");

        then(student.getId())
                .isNotNull();

        then(student.getName())
                .isEqualTo(savedStudent.getName());
    }

    @Test
    void get_average_grade_of_active_students() {
        // given
        var mark = Student.builder().name("Mark").active(true).grade(100).build();
        var susan = Student.builder().name("Susan").active(true).grade(80).build();
        var piter = Student.builder().name("Piter").active(false).grade(50).build();

        Stream.of(mark, susan, piter).forEach(testEntityManager::persistFlushFind);

        // when
        var averageGrade = studentRepository.averageGradeForActiveStudents();

        then(averageGrade).isCloseTo(90, DELTA);
    }
}
