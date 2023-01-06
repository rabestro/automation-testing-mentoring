package ch05_05;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.time.Clock;
import java.time.Duration;
import java.util.List;

public class MessageHistoryComponent {
    @FindBy(how = How.CSS, using = "#multi-list li")
    private List<WebElement> multiMessages;

    @FindBy(how = How.CSS, using = "#single-list li")
    private List<WebElement> singleMessages;

    public MessageHistoryComponent(final WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public int countSingleHistoryMessages() {
        return singleMessages.size();
    }

    public String getSingleHistoryMessage(final int index) {
        if (singleMessages.size() > index) {
            return singleMessages.get(index).getText();
        }
        return "";
    }

    public void waitTillReady() {
        var timeOutInSeconds = Duration.ofSeconds(10);
        var clock = Clock.systemDefaultZone();
        var end = clock.instant().plus(timeOutInSeconds);

        while (clock.instant().isBefore(end)) {
            if (singleMessages.size() > 0 || multiMessages.size() > 0) {
                return;
            }
        }

        throw new TimeoutException("Component not ready");
    }
}
