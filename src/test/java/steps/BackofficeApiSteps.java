package steps;

import com.altenar.sb2.backoffice.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import io.qameta.allure.Step;
import models.Date.GenerateFormatDate;

import java.util.ArrayList;
import java.util.List;

import static helper.HelpMethodClass.*;
import static requests.BackofficeRequests.*;

public class BackofficeApiSteps {
    @Step("Get championships for cricket")
    public static EventSportItemListApiResult getCricketChampionships(
            List<Integer> sportIds
    ) throws JsonProcessingException {
        GetHighlightsChampionshipsRequest requestBody = new GetHighlightsChampionshipsRequest(
                GenerateFormatDate.getCurrentDateTime(),
                GenerateFormatDate.getNextYearDateTime(),
                sportIds
        );

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JodaModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        System.out.println(mapper.writeValueAsString(requestBody));

        return getChampionships(requestBody);
    }

    @Step("Extract sports for update config from championships")
    public static List<SportRequestItem> getSports(
            EventSportItemListApiResult championships
    ) {
        List<SportRequestItem> sports = new ArrayList<>();

        for (var sport : championships.getData()) {
            sports.add(new SportRequestItem(
                    sport.getSportId(),
                    true,
                    1
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
            EventSportItemListApiResult championships,
            List<Long> eventIds,
            List<HighlightsEventRequestItem> events,
            List<Integer> sportIds,
            boolean isPromo,
            boolean isSafe,
            int order
    ) throws Exception {
        long eventId = getFirstEventId(championships, sportIds);
        addInEventIds(eventId, eventIds);
        addInEvents(events, eventId, order, isPromo, isSafe);
    }

    @Step("Update config with event and sports")
    public static ApiResult updateConfiguration (
            int configId,
            List<SportRequestItem> sports,
            List<HighlightsEventRequestItem> defaultEvents,
            List<LanguageTabRequestItem> languages
    ) {
        UpdateHighlightsConfigRequest newConfig = new UpdateHighlightsConfigRequest(
                defaultEvents,
                languages,
                sports,
                configId
        );

        return updateConfig(newConfig);
    }

    @Step("Add first language to LanguagesTabs")
    public static void addFirstLanguageToLanguagesTabs(
            List<HighlightsEventRequestItem> events,
            List<Integer> languageIds,
            List<LanguageTabRequestItem> languageTabs
    ) throws Exception {
        int languageId = getFirstLanguageId();
        addElementToList(languageId, languageIds);

        LanguageTabRequestItem languageTab = createNewLanguageTab(languageId, events);
        addElementToList(languageTab, languageTabs);
    }

    @Step("Remove language from LanguagesTabs")
    public static void removeLanguageFromLanguagesTabs (
            int removeLanguageId,
            List<Integer> languageIds,
            List<LanguageTabRequestItem> languageTabs
    )  {
        languageIds.removeIf(languageId -> languageId.equals(removeLanguageId));
        languageTabs.removeIf(lang -> Integer.valueOf(removeLanguageId).equals(lang.getLanguageId()));
    }

    @Step("Remove first event from LanguagesTabs")
    public static void removeFirstEventFromLanguagesTabs (
            List<LanguageTabRequestItem> languageTabs
    ) {
//        if (languageTabs.isEmpty()) {
//            return;
//        }
//
//        if (languageTabs.getFirst().getHighlightsEvents().isEmpty()) {
//            return;
//        }

        languageTabs.forEach(lang ->
                lang.getHighlightsEvents().removeFirst()
        );
    }

    @Step("Remove events")
    public static void removeEvents (
            List<Long> eventIds,
            List<HighlightsEventRequestItem> events

    )  {
        eventIds.clear();
        events.clear();
    }

    @Step("Remove sports")
    public static void removeSports (
            List<Integer> sportIds,
            List<SportRequestItem> sports

    )  {
        sportIds.clear();
        sports.clear();
    }
}