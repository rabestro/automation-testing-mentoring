package ch05_05;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.time.Clock;
import java.time.Duration;
import java.util.List;

final class MessageHistoryComponent {
    private static final Duration TIME_OUT = Duration.ofSeconds(10);

    @FindBy(css = "#multi-list li")
    private List<WebElement> multiMessages;

    @FindBy(css = "#single-list li")
    private List<WebElement> singleMessages;

    MessageHistoryComponent(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    int countSingleHistoryMessages() {
        return singleMessages.size();
    }

    String getSingleHistoryMessage(int index) {
        return singleMessages.size() > index
                ? singleMessages.get(index).getText()
                : "";
    }

    void waitTillReady() {
        var clock = Clock.systemDefaultZone();
        var end = clock.instant().plus(TIME_OUT);

        while (clock.instant().isBefore(end)) {
            if (singleMessages.size() > 0 || multiMessages.size() > 0) {
                return;
            }
        }

        throw new TimeoutException("Component not ready");
    }
}
