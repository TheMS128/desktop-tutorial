package steps;

import com.altenar.sb2.backoffice.model.*;
import io.qameta.allure.Step;
import models.Date.GenerateFormatDate;
import models.GetChampionsBody.GetChampionshipsBody;

import java.util.ArrayList;
import java.util.List;

import static helper.HelpMethodClass.*;
import static requests.BackofficeRequests.*;

public class BackofficeApiSteps {
    @Step("Get championships for cricket")
    public static EventSportItemListApiResult getCricketChampionships(
            List<Integer> sportIds
    ) {
        GetChampionshipsBody requestBody = new GetChampionshipsBody(
                sportIds,
                GenerateFormatDate.getCurrentDate(),
                GenerateFormatDate.getNextYearDate()
        );

        return getChampionships(requestBody);
    }

    @Step("Extract sports for update config from championships")
    public static List<SportRequestItem> getSports(
            EventSportItemListApiResult championships
    ) {
        List<SportRequestItem> sports = new ArrayList<>();

        for (var sport : championships.getData()) {
            sports.add(new SportRequestItem(
                    sports.size() + 1,
                    true,
                    sport.getSportId()
            ));
        }
        return sports;
    }

    @Step("Get languagesTabs")
    public static List<LanguageTabRequestItem> getLanguagesTabs(
            int configId
    ) {
        return getLanguageTab(configId);
    }

    @Step("Add event to events")
    public static void addEvents (
            List<Long> eventIds,
            List<HighlightsEventRequestItem> events,
            long eventId,
            boolean isPromo,
            boolean isSafe,
            int order
    ) {
        addInEventIds(eventId, eventIds);
        addInEvents(events, eventId, order, isPromo, isSafe);
    }

    @Step("Update config with event and sports")
    public static void updateConfiguration (
            int configId,
            List<SportRequestItem> sports,
            List<HighlightsEventRequestItem> events,
            List<LanguageTabRequestItem> languages
    ) {
        UpdateHighlightsConfigRequest newConfig = new UpdateHighlightsConfigRequest(
                events,
                languages,
                sports,
                configId
        );

        updateConfig(newConfig);
    }

    @Step("Add first language to LanguagesTabs")
    public static void addLanguageToLanguagesTabs(
            int languageId,
            List<Integer> languageIds,
            List<HighlightsEventRequestItem> events,
            List<LanguageTabRequestItem> languageTabs
    ) {
        addElementToList(languageId, languageIds);
        LanguageTabRequestItem languageTab = createNewLanguageTab(languageId, events);
        addElementToList(languageTab, languageTabs);
    }

    @Step("Remove language from LanguagesTabs")
    public static void removeLanguageFromLanguagesTabs (
            int removeLanguageId,
            List<Integer> languageIds,
            List<LanguageTabRequestItem> languageTabs
    ) {
        languageIds.removeIf(languageId -> languageId.equals(removeLanguageId));
        languageTabs.removeIf(lang -> Integer.valueOf(removeLanguageId).equals(lang.getLanguageId()));
    }

    @Step("Remove event from LanguagesTabs")
    public static void removeEventFromLanguagesTabs (
            long removeEventId,
            List<Long> eventIds,
            List<LanguageTabRequestItem> languageTabs
    ) {
        eventIds.removeIf(eventId -> eventId.equals(removeEventId));
        languageTabs.forEach(lang ->
                lang.getHighlightsEvents().removeIf(eventId -> eventId.getEventId().equals(removeEventId))
        );
    }

    @Step("Remove all events")
    public static void removeAllEvents (
            List<Long> eventIds,
            List<HighlightsEventRequestItem> events
    ) {
        eventIds.clear();
        events.clear();
    }

    @Step("Remove all sports")
    public static void removeAllSports (
            List<Integer> sportIds,
            List<SportRequestItem> sports
    ) {
        sportIds.clear();
        sports.clear();
    }
}