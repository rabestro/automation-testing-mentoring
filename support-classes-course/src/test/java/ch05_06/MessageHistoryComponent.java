package ch05_06;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.SlowLoadableComponent;

import java.time.Clock;
import java.util.List;

final class MessageHistoryComponent extends SlowLoadableComponent<MessageHistoryComponent> {
    @FindBy(how = How.CSS, using = "#multi-list li")
    List<WebElement> multiMessages;

    @FindBy(how = How.CSS, using = "#single-list li")
    List<WebElement> singleMessages;

    public MessageHistoryComponent(WebDriver driver) {
        super(Clock.systemDefaultZone(), 10);
        PageFactory.initElements(driver, this);
    }

    public int countSingleHistoryMessages() {
        return singleMessages.size();
    }


    public String getSingleHistoryMessage(int index) {
        if (singleMessages.size() > index) {
            return singleMessages.get(index).getText();
        }
        return "";
    }


    @Override
    protected void load() {
        // tend not to implement load for components
    }

    @Override
    protected void isLoaded() throws Error {
        boolean ready = false;

        try {
            ready = singleMessages.size() > 0 || multiMessages.size() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!ready) {
            throw new Error("Component not ready");
        }
    }
}
