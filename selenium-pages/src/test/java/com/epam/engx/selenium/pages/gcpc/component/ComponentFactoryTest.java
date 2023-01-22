package com.epam.engx.selenium.pages.gcpc.component;

import com.epam.engx.selenium.pages.UnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@UnitTest
class ComponentFactoryTest {
    @Mock
    SearchContext root;
    @Mock
    Consumer<WebElement> jsActionClick;
    @Mock
    WebElement element;

    ComponentFactory componentFactory;

    @BeforeEach
    void setUp() {
        componentFactory = new ComponentFactory(root, jsActionClick);
        when(root.findElement(any(ByModel.class))).thenReturn(element);
    }

    @ParameterizedTest(name = "select element with tag ''{0}''")
    @CsvSource(delimiter = '|', textBlock = """
            input       | com.epam.engx.selenium.pages.gcpc.component.InputComponent
            md-select   | com.epam.engx.selenium.pages.gcpc.component.SelectComponent
            md-checkbox | com.epam.engx.selenium.pages.gcpc.component.CheckboxComponent
            """)
    void create_a_component_for_model_with_supported_tag(String tagName, Class<Component> expectedClass) {

        given(element.getTagName())
                .willReturn(tagName);

        // when
        var component = componentFactory.apply("some-model-name");

        then(component)
                .isInstanceOf(expectedClass);
    }

    @ParameterizedTest(name = "select element with unknown tag ''{0}''")
    @ValueSource(strings = {"select", "button"})
    void attempt_to_create_component_for_model_with_unsupported_tag(String unknownTag) {

        given(element.getTagName())
                .willReturn(unknownTag);

        // when
        var thrown = catchThrowable(
                () -> componentFactory.apply("some-model-name")
        );

        then(thrown)
                .isInstanceOf(UnsupportedOperationException.class)
                .message()
                .startsWith("no implementation for")
                .contains(unknownTag);
    }
}
