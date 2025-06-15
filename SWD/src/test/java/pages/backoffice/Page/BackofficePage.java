package pages.backoffice.Page;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.ConfigLoader;

import java.util.List;
import java.util.Random;

public class BackofficePage {
    private final WebDriverWait wait;
    private final Random random;
    private final WebDriver webDriver;

    public BackofficePage(WebDriver webDriver, WebDriverWait wait) {
        this.wait = wait;
        random = new Random();
        this.webDriver = webDriver;
    }

    public void login() {
        WebElement acceptCookieButton = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[@id=\"accept-cookie-btn\"]")));

        WebElement usernameInput = webDriver.findElement(
                By.xpath("//input[@id=\"username_input\"]"));

        WebElement passwordInput = webDriver.findElement(
                By.xpath("//input[@id=\"password_input\"]"));

        WebElement loginButton = webDriver.findElement(
                By.xpath("//button[@id=\"login-button\"]"));

        String login = ConfigLoader.getUsername();
        String password = ConfigLoader.getPassword();

        acceptCookieButton.click();
        usernameInput.sendKeys(login);
        passwordInput.sendKeys(password);
        loginButton.click();
    }

    public void clickOnAddSportTree() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(" //div[contains(@class, \"RowWrapperstyles__RowWrapper-sc-1f6r5o4-0 AddSportTreestyles__AddSportsHeader-sc-eyy1cy-1\")]")))
            .click();
    }

    public void clickOnSportsTreeCleaningButton() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[text()=\"CLEAR\"]")))
            .click();
    }

    public void clickOnApplyButtonInASportsTree() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(" //button[text()=\"Apply\"]")))
            .click();
    }

    public void closeAlertWindow() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[contains(@class, \"sc-iTFTee fapJii MuiButtonBase-root sc-iAEawV euiTnO MuiIconButton-root MuiIconButton-colorInherit MuiIconButton-sizeSmall\")]")))
            .click();
    }

    public void clickOnRandomSportsCheckbox() {
         List<WebElement> availableSports = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.xpath("(//div[//h6[text()=\"Add sport\"]]//span[contains(@class, \"MuiCheckbox-sizeSmall\")])[position() > 1]")));

        availableSports
             .get(random.nextInt(availableSports.size()))
             .click();
    }

    public void clickOnSaveConfigButton() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[text()=\"Save config\"]")))
            .click();
    }

    public List<WebElement> getListOfAddedSports() {
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.xpath("//div[contains(@class, \"SportTreeItemstyles__SportItemWrapper-sc-nljcz8-1\")]")));
    }

    public void clickOnSportCheckBox() {
        wait.until(ExpectedConditions.presenceOfElementLocated (
                By.xpath("//div[contains(@class, \"RowWrapperstyles__RowWrapper-sc-1f6r5o4-0 erIETV\")]//input")))
            .click();
    }

    public void clickOnSportsRemovalButton() {
        wait
            .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@aria-label=\"Delete\"]/button")))
            .click();
    }

    public boolean getSportsContainer() {
        return wait.until(driver -> driver.findElements(
                By.xpath("//div[contains(@class, \"ColumnWrapperstyles__ColumnWrapper-sc-1aq8y79-0 SportsTreestyles__SettingsSportWrapper-sc-nzr011-0 dcLCDw hBcqmz\")]"))
                .isEmpty());
    }

    public WebElement getAlertMessageElement() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//p[contains(@class, \"sc-ilhmMj hxhwWf MuiTypography-root MuiTypography-body2 Alertstyles__Typography-sc-xgqvaq-2 btPLnG\")]")));
    }

    public void clickOnCricketSportsCheckbox() {
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[text()=\"Cricket\"]/span")))
            .click();
    }

    public void clickOnCricketSports() {
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[contains(@class, \"RowWrapperstyles__RowWrapper-sc-1f6r5o4-0 SportTreeItemstyles__SportItemWrapper-sc-nljcz8-1 cDTcWd bxLTri\")]")))
            .click();
    }

    public void selectRandomChampionshipCountry() {
        List<WebElement> availableChampionshipCountries = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.xpath("//div[contains(@class, \"sc-fXqpFg bqoatA MuiCollapse-wrapperInner MuiCollapse-vertical\")]//div[not(@*)]")));

        availableChampionshipCountries
                .get(random.nextInt(availableChampionshipCountries.size()))
                .click();
    }

    public void selectRandomChampionship() {
        List<WebElement> availableChampionships = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.xpath("//div[contains(@class, \"ChampItemstyles__ChampWrapper-sc-1nuctcd-0 hGVoXK\")]")));

        availableChampionships
                .get(random.nextInt(availableChampionships.size()))
                .click();
    }

    public void selectRandomEvent() {
        List<WebElement> availableEvents = wait
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                    By.xpath("//button[text()=\"add\"]")));

        availableEvents
                .getFirst()
                .click();
    }

    public int getNumberOfEvents() {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.xpath("//div[contains(@class, \"RowWrapperstyles__RowWrapper-sc-1f6r5o4-0 EventItemstyles__ManualCard-sc-1pbqrqb-0 hnsmWl bXYsNg\")]")))
            .size();
    }

    public void searchCricket() {
        WebElement searchField = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[contains(@class, \"sc-dkrFOg sc-kDvujY jnbzva ihlWhN MuiInputBase-input MuiOutlinedInput-input MuiInputBase-inputSizeSmall\")]")));
        searchField.sendKeys("Cricket");
    }

    public void removeEventFromDefaultLanguage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//span[@aria-label=\"Delete\"]/button")))
            .click();
    }

    public void removeEventsFromDefaultLanguage() {
        try {
            List<WebElement> events = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.xpath("//span[@aria-label=\"Delete\"]/button")));

            if (!events.isEmpty()) {
                for (var event : events) {
                    event.click();
                }
            }
        } catch (TimeoutException e) {
            return;
        }
    }

    public void clickOnDefaultLanguage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[text()=\"Default\"]")))
            .click();
    }

    public boolean getEventsContainer() {
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(
            By.xpath("//div[contains(@class, \"sc-idXgbr jBcoBm MuiPaper-root MuiPaper-elevation MuiPaper-rounded MuiPaper-elevation1 common__Container-sc-n9awfh-2 frNTEe\")]")));
    }

    public void clickOnIsSafeButton() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//span[@aria-label=\"is safe\"]")));
    }
}
