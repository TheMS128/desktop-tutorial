package pages.backoffice.Tests;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.backoffice.Steps.BackofficeSteps;

import java.time.Duration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BackofficeEventsTests {
    static WebDriverWait wait;
    static WebDriver webDriver;
    static BackofficeSteps backofficeSteps;

    @BeforeAll
    static void setUp() {
        webDriver = new ChromeDriver();
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        webDriver.manage().window().maximize();
        wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        backofficeSteps = new BackofficeSteps(webDriver, wait);
        backofficeSteps.gettingAndLoginBackofficePage();
        backofficeSteps.cleaningSports();
        backofficeSteps.addingCricketInSports();
        backofficeSteps.removingEventsFromDefaultLanguage();
    }

    @AfterAll
    static void tearDown() {
        webDriver.quit();
    }

    @Test
    @Order(1)
    @DisplayName("Adding cricket event to default language")
    void addingEventToDefaultLanguage() {
        backofficeSteps.openingListSelectionChampionshipCountry();
        backofficeSteps.randomSelectionEvent();

        assertThat(
                "The event list must contain one event",
                backofficeSteps.gettingNumberOfEvents(),
                is(1)
        );
    }

    @Test
    @Order(2)
    @DisplayName("Setting the event status to Is safe")
    void settingEventStatusIsSafe() {
        backofficeSteps.clickingOnIsSafeButton();
        backofficeSteps.pressingOnSaveConfigButton();

        assertThat(
                "The message does not match what was expected",
                backofficeSteps.gettingAlertMessage(),
                is("The config was saved successfully")
        );
    }

    @Test
    @Order(3)
    @DisplayName("Removing cricket event on default language")
    void removingEventOnDefaultLanguage() {
        backofficeSteps.removingEventFromDefaultLanguage();

        assertThat(
                "The block should disappear from the site",
                backofficeSteps.gettingEventsContainer(),
                is(true)
        );
    }
}