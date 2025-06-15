package pages.backoffice;

import io.qameta.allure.Step;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BackofficeSteps {
    private final WebDriverWait wait;
    private final WebDriver webDriver;
    private BackofficePage backofficePage;

    public BackofficeSteps(WebDriver webDriver, WebDriverWait wait) {
        this.wait = wait;
        this.webDriver = webDriver;
        backofficePage = new BackofficePage(webDriver, wait);
    }

    @Step("Precondition. Getting the Backoffice page and the entrance to it")
    public void gettingAndLoginBackofficePage() {
        webDriver.get("https://sb2admin-altenar2-stage.biahosted.com/v2/highlights/configs/126");
        backofficePage.login();
    }

    @Step("Precondition. Getting the Backoffice page and the entrance to it")
    public void cleaningSports() {
        backofficePage.clickOnAddSportTree();
        backofficePage.clickOnSportsTreeCleaningButton();
        backofficePage.clickOnApplyButtonInASportsTree();
    }

    @Step("Precondition. Add cricket in sport tree")
    public void addingCricketInSports() {
        backofficePage.clickOnAddSportTree();
        backofficePage.searchCricket();
        backofficePage.clickOnCricketSportsCheckbox();
        backofficePage.clickOnApplyButtonInASportsTree();
    }

    @Step("Precondition. Removing all events from Default language")
    public void removingEventsFromDefaultLanguage() {
        backofficePage.clickOnDefaultLanguage();
        backofficePage.removeEventsFromDefaultLanguage();
    }


    @Step("Adding a random sport from the Add sport list")
    public void addingRandomSport() {
        backofficePage.clickOnAddSportTree();
        backofficePage.clickOnRandomSportsCheckbox();
        backofficePage.clickOnApplyButtonInASportsTree();
    }

    @Step("Pressing on the Save  button")
    public void pressingOnSaveConfigButton() {
        backofficePage.clickOnSaveConfigButton();
    }

    @Step("Close alert message")
    public void closeAlertMessage() {
        backofficePage.closeAlertWindow();
    }

    @Step("Getting the number of sports")
    public int gettingTheNumberOfSports() {
        return backofficePage.getListOfAddedSports().size();
    }

    @Step("Pressing on checkbox, canceling the choice of sport")
    public void abolitionChoiceSports() {
        backofficePage.clickOnSportCheckBox();
    }

    @Step("Pressing the deletion button of the unexplored sport")
    public void pressingOnSportsRemovalButton() {
        backofficePage.clickOnSportsRemovalButton();
    }

    @Step("Trying to get a sports container")
    public boolean tryingGetSportsContainer() {
        return backofficePage.getSportsContainer();
    }

    @Step("Getting the status of conservation of a message from Alert")
    public String gettingAlertMessage() {
        return backofficePage.getAlertMessageElement().getText();
    }


    @Step("Opening the list of sports choosing a country and championship")
    public void openingListSelectionChampionshipCountry() {
        backofficePage.clickOnCricketSports();
        backofficePage.selectRandomChampionshipCountry();
        backofficePage.selectRandomChampionship();
    }

    @Step("Random selection of the event")
    public void randomSelectionEvent() {
        backofficePage.selectRandomEvent();
    }

    @Step("Getting number of events")
    public int gettingNumberOfEvents() {
        return backofficePage.getNumberOfEvents();
    }

    // Второй тест событий
    @Step("Removing event from default language")
    public void removingEventFromDefaultLanguage() {
        backofficePage.removeEventFromDefaultLanguage();
    }

    @Step("Getting events container")
    public boolean gettingEventsContainer() {
        return backofficePage.getEventsContainer();
    }


    @Step("Clicking on the Is Safe button")
    public void clickingOnIsSafeButton() {
        backofficePage.clickOnIsSafeButton();
    }
}
