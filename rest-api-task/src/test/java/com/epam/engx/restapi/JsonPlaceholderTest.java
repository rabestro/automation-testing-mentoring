package com.epam.engx.restapi;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class JsonPlaceholderTest {
    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
        .setBaseUri("https://jsonplaceholder.typicode.com")
        .build();

    @Test
    void perform_get_request_and_assert_status_code() {
        given(requestSpec)
            .when().get("/users")
            .then()
            .statusCode(HttpStatus.SC_OK);
    }

    @Test
    void perform_get_request_and_assert_header() {
        given(requestSpec)
            .when().get("/users")
            .then()
            .header("Content-Type", notNullValue())
            .header("Content-Type", equalTo("application/json; charset=utf-8"));
    }

    @Test
    void perform_get_request_and_assert_body() {
        given(requestSpec)
            .when().get("/users")
            .then()
            .body("", hasSize(10))
            .body("id", everyItem(notNullValue()))
            .body("name", everyItem(not(emptyString())))
            .body("username", everyItem(not(emptyString())));
    }
}
