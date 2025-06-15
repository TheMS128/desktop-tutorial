package pages.frontend;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class HighlightsTests {
    static WebDriver webDriver;
    static WebDriverWait wait;
    static HighlightsPage highlightsPage;
    static HighlightsSteps highlightsSteps;

    @BeforeAll
    static void setUp() {
        webDriver = new ChromeDriver();
        wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        highlightsPage = new HighlightsPage(webDriver, wait);
        highlightsSteps = new HighlightsSteps(webDriver, wait, highlightsPage);
    }

    @AfterAll
    static void closeDriver() {
        webDriver.quit();
    }

    @Test
    @Order(1)
    @DisplayName("Displaying a site with skintest integration but without the culture parameter")
    public void displaySiteWithoutCultureParameter() {
        highlightsSteps.gettingFrontendPageWithoutCultureParameter();

        assertThat(
                "If the culture parameter is missing, the page language must be English",
                highlightsPage.getLiveNowTitle(),
                is("LIVE NOW")
        );
    }

    @Test
    @Order(2)
    @DisplayName("Displaying a site with skintest integration and polish culture parameter")
    public void displaySiteWithPolishCultureParameter() {
        highlightsSteps.gettingFrontendPageWithPolishCultureParameter();

        assertThat(
                "If the culture parameter is set to pl-pl, then the page language must be Polish",
                highlightsPage.getLiveNowTitle(),
                is("NA ŻYWO TERAZ")
        );
    }

    @Test
    @Order(3)
    @DisplayName("Displaying a site with skintest integration and incorrect culture parameter")
    public void displaySiteWithIncorrectCultureParameter() {
        highlightsSteps.gettingFrontendPageWithIncorrectCultureParameter();

        assertThat(
                "If the culture parameter does not exist or is invalid, the page language must be English",
                highlightsPage.getLiveNowTitle(),
                is("LIVE NOW")
        );
    }
}