package ch05_03;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

final class SupportPage extends PageFactory {
    @FindBy(how = How.ID, using = "resend-select")
    WebElement singleResendButton;

    @FindBy(how = How.CSS, using = "#message")
    private WebElement message;

    SupportPage(WebDriver driver) {
        //initElements(new AjaxElementLocatorFactory(driver, 10), this);
        initElements(new VisibleAjaxElementFactory(driver, 10), this);
    }

    String waitForMessage() {
        return message.getText();
    }
}
