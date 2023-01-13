package com.linkedin.studentclient;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest
class StudentClientApplicationTests {

    @Test
    void contextLoads() {
		then(true).as("the spring context loaded successful")
				.isTrue();
    }
}
