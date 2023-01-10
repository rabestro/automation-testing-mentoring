package com.epam.engx.selenium.pages.gcpc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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
class ByModelTest {
    @Mock
    private SearchContext searchContext;
    private ArgumentCaptor<By> argumentCaptor;

    @BeforeEach
    void setUp() {
        argumentCaptor = ArgumentCaptor.forClass(By.class);
    }

    @ParameterizedTest(name = "model: {0}")
    @CsvSource({
            "listingCtrl.computeServer.os, model(listingCtrl.computeServer.os)",
            "computeServer.os, model(listingCtrl.computeServer.os)",
            "os, model(listingCtrl.computeServer.os)",
            "persistentDisk.location, model(listingCtrl.persistentDisk.location)",
            "location, model(listingCtrl.computeServer.location)"
    })
    void create_a_locator_by_angular_model_name(String model, String locator) {
        // given
        var byModel = new ByModel(model);

        // when
        byModel.findElements(searchContext);

        then(searchContext).should()
                .findElements(argumentCaptor.capture());

        and.then(argumentCaptor.getValue())
                .as("a locator created from the model name")
                .isEqualTo(byModel)
                .extracting(By::toString)
                .isEqualTo(locator);
    }
}
