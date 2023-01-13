package com.linkedin.studentservice;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StudentControllerTest {
    @MockBean
    private StudentService studentService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void get_student_for_existing_student() throws Exception {
        //given
        given(studentService.getStudentById(anyLong())).willReturn(
                Student.builder()
                        .id(1L)
                        .name("Mark")
                        .grade(10)
                        .build()
        );

        //when then
        mockMvc.perform(
                get("/students/1")
        ).andExpectAll(
                status().isOk(),
                jsonPath("id").value(1L),
                jsonPath("name").value("Mark"),
                jsonPath("grade").value(10)
        );
    }

    @Test
    void get_student_for_missing_student() throws Exception {
        //given
        given(studentService.getStudentById(anyLong()))
                .willThrow(StudentNotFoundException.class);

        //when then
        mockMvc.perform(get("/students/1"))
                .andExpect(status().isNotFound());
    }
}
