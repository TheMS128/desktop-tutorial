package pages.backoffice;

import org.junit.jupiter.api.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BackofficeTests {
    static WebDriverWait wait;
    static WebDriver webDriver;
    static BackofficeSteps backofficeSteps;

    @BeforeAll
    static void setUp() {
        webDriver = new ChromeDriver();
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        webDriver.manage().window().maximize();
        wait = new WebDriverWait(webDriver, Duration.ofSeconds(40));
        backofficeSteps = new BackofficeSteps(webDriver, wait);
        backofficeSteps.gettingAndLoginBackofficePage();
        backofficeSteps.cleaningSports();
    }

    @AfterAll
    static void tearDown() {
        webDriver.quit();
    }

    @Test
    @Order(1)
    @DisplayName("Adding a new sport to the backing office")
    void addingSport() {
        backofficeSteps.addingRandomSport();
        backofficeSteps.pressingOnSaveConfigButton();
        backofficeSteps.closeAlertMessage();

        assertThat(
                "The sport should be one",
                backofficeSteps.gettingTheNumberOfSports(),
                is(1)
        );
    }

    @Test
    @Order(2)
    @DisplayName("Removing a new sport to the backing office")
    void removingSport() {
        backofficeSteps.abolitionChoiceSports();
        backofficeSteps.pressingOnSportsRemovalButton();

        assertThat(
                "Sports container should not be on the page",
                backofficeSteps.tryingGetSportsContainer(),
                is(true)
        );
    }

    @Test
    @Order(3)
    @DisplayName("Trying to keep config without sports")
    void invalidSaveConfig() {
        backofficeSteps.pressingOnSaveConfigButton();

        assertThat(
                "Alert message is incorrect",
                backofficeSteps.gettingAlertMessage(),
                is("List of sports cannot be empty")
        );
    }
}
