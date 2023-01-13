package com.linkedin.studentservice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Student getStudentByName(String name);

    @Query("SELECT avg(grade) FROM Student WHERE active=TRUE")
    double averageGradeForActiveStudents();
}
