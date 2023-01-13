package com.linkedin.studentservice;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank
    private String name;
    private boolean active;
    @Min(0)
    @Max(100)
    private int grade;

    public Student(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Student student = (Student) o;
        return active == student.active && grade == student.grade && id.equals(student.id) && name.equals(student.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, active, grade);
    }
}
