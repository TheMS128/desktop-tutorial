package pages.frontend;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HighlightsPage {
    WebDriver webDriver;
    WebDriverWait wait;

    public HighlightsPage(WebDriver webDriver, WebDriverWait wait) {
        this.webDriver = webDriver;
        this.wait = wait;
    }

    public String getLiveNowTitle() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("(//div[contains(@class, \"asb-flex-sc _asb_top-events-header-label\")])[1]"))
        ).getText();
    }
}
