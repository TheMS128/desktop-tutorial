package pages.frontend.Steps;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.frontend.Page.HighlightsPage;

public class HighlightsSteps {
    WebDriverWait wait;
    WebDriver webDriver;
    HighlightsPage highlightsPage;

    public HighlightsSteps(
            WebDriver webDriver,
            WebDriverWait wait,
            HighlightsPage highlightsPage
    ) {
        this.wait = wait;
        this.webDriver = webDriver;
        this.highlightsPage = highlightsPage;
    }


    @Step("Getting frontend page without culture parameter")
    public void gettingFrontendPageWithoutCultureParameter() {
        webDriver.get("https://sb2clientstatic-altenar2-stage.biahosted.com/?integration=skintest&#/");
    }


    @Step("Get frontend page with poland culture parameter")
    public void gettingFrontendPageWithPolishCultureParameter() {
        webDriver.get("https://sb2clientstatic-altenar2-stage.biahosted.com/?integration=skintest&culture=pl-pl#/");
    }


    @Step("Get frontend page with incorrect culture parameter")
    public void gettingFrontendPageWithIncorrectCultureParameter() {
        webDriver.get("https://sb2clientstatic-altenar2-stage.biahosted.com/?integration=skintest&culture=null-null#/");
    }
}