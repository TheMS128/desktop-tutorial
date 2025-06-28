package helper;

import com.altenar.sb2.backoffice.model.*;
import models.Date.GenerateFormatDate;
import requests.BackofficeRequests;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static requests.BackofficeRequests.getConfigSettings;
import static requests.BackofficeRequests.getEvents;

public class HelpMethodClass {
    public static <T> T getFirstElement(List<T> list) throws Exception {
        if (list.isEmpty())  {
            throw new Exception("The list is empty");
        }
        return list.getFirst();
    }

    public static List<LanguageTabRequestItem> getLanguageTab(int configId) {
        List<ConfigLanguageTab> oldTabs = getConfigSettings(configId).getData().getLanguageTabs();

        return oldTabs.stream()
                .map(oldTab -> {
                    List<HighlightsEventRequestItem> highlightsEvents = oldTab.getTopEvents().stream()
                            .map(event -> new HighlightsEventRequestItem(
                                    event.getOrder(),
                                    event.getIsPromo(),
                                    event.getIsSafe(),
                                    event.getEventId()
                            ))
                            .collect(Collectors.toList());

                    return new LanguageTabRequestItem(
                            highlightsEvents,
                            oldTab.getLanguageId()
                    );
                })
                .collect(Collectors.toList());
    }

    public static List<HighlightsEventRequestItem> getHighlightEventsFromDefaultLanguage(int configId) {
        List<ConfigEvent> configEvents = getConfigSettings(configId).getData().getEvents();

        return configEvents.stream()
            .map(configEvent -> new HighlightsEventRequestItem(
                    configEvent.getOrder(),
                    configEvent.getIsPromo(),
                    configEvent.getIsSafe(),
                    configEvent.getEventId()
                ))
                .collect(Collectors.toList());
    }

    public static List<HighlightsEventRequestItem> getHighlightEventsFromFirstLanguage(int configId) {
        List<ConfigLanguageTab> configEvents =
                getConfigSettings(configId).getData().getLanguageTabs();

        if (configEvents.isEmpty()) {
            return new ArrayList<>();
        }

        return configEvents.getFirst().getTopEvents().stream()
                .map(configEvent -> new HighlightsEventRequestItem(
                        configEvent.getOrder(),
                        configEvent.getIsPromo(),
                        configEvent.getIsSafe(),
                        configEvent.getEventId()
                ))
                .collect(Collectors.toList());
    }

    public static List<Integer> getLanguageIds(int configId) {
        List<ConfigLanguageTab> languages =
                getConfigSettings(configId).getData().getLanguageTabs();

        return languages.stream()
                .map(ConfigLanguageTab::getLanguageId)
                .collect(Collectors.toList());
    }

    public static List<ConfigSport> getSportsFromConfig(int configId) {
        return getConfigSettings(configId).getData().getSports();
    }

    public static List<ConfigEvent> getFirstLanguageEventsFromConfig(int configId) {
        if (getConfigSettings(configId).getData().getLanguageTabs().isEmpty()) {
            return new ArrayList<>();
        }
        return getConfigSettings(configId).getData().getLanguageTabs().getFirst().getTopEvents();
    }

    public static List<EventChampItem> extractAllChamps(
            EventSportItemListApiResult championships
    ) {
        return championships.getData().stream()
                .filter(datum -> datum.getCategories() != null)
                .flatMap(datum -> datum.getCategories().stream())
                .filter(category -> category.getChamps() != null)
                .flatMap(category -> category.getChamps().stream())
                .toList();
    }

    public static EventCandidateItemListApiResult getEventsByRequest(
            EventSportItemListApiResult championships,
            List<Integer> sportIds
    ) throws Exception {
        models.SearchEventsBody.Root requestBody = new models.SearchEventsBody.Root(
                GenerateFormatDate.getCurrentDate(),
                GenerateFormatDate.getNextYearDate(),
                sportIds,
                getFirstElement(extractAllChamps(championships)).getChampId()
        );

        return getEvents(requestBody);
    }

    public static long getFirstEventId(
            EventSportItemListApiResult championships,
            List<Integer> sportIds
    ) throws Exception {
        List<EventCandidateItem> events = getEventsByRequest(championships, sportIds).getData();
        return getFirstElement(events).getEventId();
    }

    public static void addInEventIds(
            long eventId,
            List<Long> eventIds
    ) {
        eventIds.add(eventId);
    }

    public static void addInEvents(
            List<HighlightsEventRequestItem> events,
            long eventId,
            int order,
            boolean isPromo,
            boolean isSafe
    ) {
        events.add(new HighlightsEventRequestItem(
                order,
                isPromo,
                isSafe,
                eventId
        ));
    }

    public static int getFirstLanguageId() throws Exception {
        LanguageItemListApiResult languages = BackofficeRequests.getLanguages();
        List<LanguageItem> languagesData = languages.getData();
        return getFirstElement(languagesData).getLanguageId();
    }

    public static void addLanguageIdToLanguageIds(
            int languageId,
            List<Integer> languageIds
    ) {
        languageIds.add(languageId);
    }

    public static LanguageTabRequestItem createNewLanguageTab(
            int languageId,
            List<HighlightsEventRequestItem> events
    ) {
        return new LanguageTabRequestItem(
                events,
                languageId
        );
    }

    public static <T> void addElementToList(
            T element,
            List<T> collection
    ) {
        collection.add(element);
    }
}
