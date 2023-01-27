package com.epam.engx.selenium.pages.gcpc.component;

import com.epam.engx.selenium.pages.UnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.openqa.selenium.WebElement;
import spock.lang.Subject;

import java.util.function.Consumer;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@UnitTest
class CheckboxComponentTest {
    @Mock
    WebElement element;
    @Mock
    Consumer<WebElement> jsActionClick;

    @Subject
    CheckboxComponent checkbox;

    @BeforeEach
    void setUp() {
        checkbox = new CheckboxComponent(element, jsActionClick);
    }

    @ParameterizedTest
    @CsvSource({"true, true", "false, false"})
    void checkbox_state_equals_to_target_state(String checkboxState, String desiredState) {

        given(element.getDomAttribute("aria-checked"))
                .willReturn(checkboxState);

        when:
        checkbox.set(desiredState);

        then(jsActionClick)
                .shouldHaveNoInteractions();
    }

    @ParameterizedTest
    @CsvSource({"true, false", "false, true"})
    void checkbox_state_not_equals_to_target_state(String checkboxState, String desiredState) {

        given(element.getDomAttribute("aria-checked"))
                .willReturn(checkboxState);

        when:
        checkbox.set(desiredState);

        then(jsActionClick)
                .should().accept(element);
    }
}
