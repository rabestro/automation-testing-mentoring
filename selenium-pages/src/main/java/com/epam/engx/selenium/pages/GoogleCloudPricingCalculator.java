package com.epam.engx.selenium.pages;

import com.epam.engx.selenium.pages.gcpc.Dropdown;
import com.paulhammant.ngwebdriver.ByAngular;
import com.paulhammant.ngwebdriver.NgWebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.ByAll;
import org.openqa.selenium.support.ui.LoadableComponent;

import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static com.paulhammant.ngwebdriver.ByAngularModel.FindBy;
import static java.util.stream.Collectors.toUnmodifiableMap;

public final class GoogleCloudPricingCalculator extends LoadableComponent<GoogleCloudPricingCalculator> {
    private static final By VALUE = By.cssSelector("md-select-value div.md-text");

    final NgWebDriver ngDriver;
    private final WebDriver driver;
    @FindBy(model = "listingCtrl.computeServer.quantity")
    private WebElement numberOfInstances;

    public GoogleCloudPricingCalculator(WebDriver driver) {
        this.driver = driver;
        var js = (JavascriptExecutor) driver;
        ngDriver = new NgWebDriver(js);
        PageFactory.initElements(driver, this);
    }

    public String getInstances() {
        return numberOfInstances.getAttribute("value");
    }

    public GoogleCloudPricingCalculator setInstances(int instances) {
        numberOfInstances.click();
        numberOfInstances.sendKeys(String.valueOf(instances));
        return this;
    }

    public GoogleCloudPricingCalculator set(Dropdown dropdown, String value) {
        return this.new Menu(dropdown.model()).select(value);
    }

    public String get(Dropdown dropdown) {
        return new Menu(dropdown.model()).text();
    }

    public Map<String, String> parameters() {
        return driver.findElements(new ByAll(
                        By.cssSelector("md-select[ng-model^='listingCtrl.']"),
                        By.cssSelector("input[ng-model^='listingCtrl.']"),
                        By.cssSelector("md-checkbox[ng-model^='listingCtrl.']")
                ))
                .stream()
                .collect(toUnmodifiableMap(this::getModel, this::getValue));
    }

    private String getModel(WebElement element) {
        return element
                .getAttribute("ng-model")
                .substring("listingCtrl.".length());
    }

    private String getValue(WebElement element) {
        return switch (element.getTagName()) {
            case "md-select" -> element.getText();
            case "input" -> element.getAttribute("value");
            case "md-checkbox" -> element.getDomAttribute("aria-checked");
            default -> "unknown";
        };
    }

    @Override
    protected void load() {
        driver.get("https://cloud.google.com/products/calculator");
        ngDriver.waitForAngularRequestsToFinish();
        var mainFrame = driver.findElement(By.xpath("//iframe"));
        driver.switchTo().frame(mainFrame);
        var myFrame = driver.findElement(By.id("myFrame"));
        driver.switchTo().frame(myFrame);
    }

    @Override
    protected void isLoaded() throws Error {
        try {
            var isLoaded = driver.getTitle()
                    .equals("Google Cloud Pricing Calculator");

            if (isLoaded) {
                return;
            }
        } catch (Exception ignored) {
            // ignored
        }
        throw new Error("Page has not loaded");
    }

    @SuppressWarnings("CssInvalidHtmlTagReference")
    public final class Menu {
        private final WebElement select;

        public Menu(String model) {
            select = driver.findElement(ByAngular.model(model));
        }

        public String text() {
            return select.getText();
        }

        public GoogleCloudPricingCalculator select(String prefix) {
            var option = options()
                    .filter(startsIgnoreCase(prefix))
                    .findFirst()
                    .orElseThrow();
            if (!option.getText().equalsIgnoreCase(text())) {
                option.select();
            }
            return GoogleCloudPricingCalculator.this;
        }

        public Stream<Option> options() {
            var area = select.getAttribute("aria-owns");
            var container = driver.findElement(By.id(area));
            var options = container.findElements(By.cssSelector("md-option"));
            return options.stream().map(Option::new);
        }

        private Predicate<Option> startsIgnoreCase(String prefix) {
            return option -> option.getText()
                    .toLowerCase()
                    .startsWith(prefix.toLowerCase());
        }

        public final class Option {
            private final WebElement item;

            Option(WebElement item) {
                this.item = item;
            }

            public String getValue() {
                return item.getAttribute("value");
            }

            public String getText() {
                return item.getAttribute("textContent").strip();
            }

            public void select() {
                select.click();
                ngDriver.waitForAngularRequestsToFinish();
                ngDriver.evaluateScript(item, "arguments[0].click();");
                ngDriver.waitForAngularRequestsToFinish();
            }
        }
    }
}
