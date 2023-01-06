package com.epam.engx.selenium.pages.gcpc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;

import static org.assertj.core.api.BDDAssertions.and;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("AccessStaticViaInstance")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
final class ByModelSelectTest {
    @Mock
    private SearchContext searchContext;
    private ArgumentCaptor<By> argumentCaptor;

    @BeforeEach
    void setUp() {
        argumentCaptor = ArgumentCaptor.forClass(By.class);
    }

    @Test
    void create_a_locator_for_dropdown_menu_by_model_name() {
        // given
        var bySelect = new ByModelSelect("computeServer.os");

        // when
        bySelect.findElements(searchContext);

        then(searchContext).should()
                .findElements(argumentCaptor.capture());

        and.then(argumentCaptor.getValue())
                .as("CSS selector generated based on the model name")
                .isEqualTo(bySelect)
                .extracting(By::toString)
                .isEqualTo("By.cssSelector: md-select[ng-model$='computeServer.os']");
    }
}
