package tests;

import com.altenar.sb2.backoffice.model.*;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static helper.HelpMethodClass.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static steps.BackofficeApiSteps.*;

public class BackofficeApiTest {
    public static final int configId = 126;
    public static final int cricketId = 74;

    public EventSportItemListApiResult championships;
    public List<SportRequestItem> sports;
    public List<Integer> sportIds;
    public List<Integer> languageIds;
    public List<Long> eventIds;
    public List<HighlightsEventRequestItem> events;
    public List<LanguageTabRequestItem> languageTabs;
    public List<?> beforeRequest;
    public List<?> afterRequest;
    public boolean isPromo;
    public boolean isSafe;
    public int order;
    public long eventId;
    public int languageId;

    @BeforeEach
    void setUp() throws Exception {
        sports = new ArrayList<>();
        sportIds = new ArrayList<>();
        languageIds = new ArrayList<>();
        languageTabs = new ArrayList<>();
        events = new ArrayList<>();
        eventIds = new ArrayList<>();
        beforeRequest = new ArrayList<>();
        afterRequest = new ArrayList<>();

        isPromo = false;
        isSafe = false;
        order = 1;
        languageId = getFirstLanguageId();

        sportIds.add(cricketId);
        championships = getCricketChampionships(sportIds);
        eventId = getFirstEventId(championships, sportIds);
        sports = getSports(championships);
        languageIds = getLanguageIds(configId);

         updateConfiguration(
                configId,
                sports,
                new ArrayList<>(),
                new ArrayList<>()
        );
    }

    @Test
    @DisplayName("Add an event with the correct IsPromo or IsSafe parameters")
    void addEventsWithCorrectIsPromoOrIsSafe() throws Exception {
        isPromo = false;
        isSafe = true;
        order = 1;
        eventId = getFirstEventId(championships, sportIds);

        beforeRequest = getHighlightEventsFromDefaultLanguage(configId);

        addEvents(
                eventIds,
                events,
                eventId,
                isPromo,
                isSafe,
                order
        );

        updateConfiguration(
                configId,
                sports,
                events,
                languageTabs
        );

        afterRequest = getHighlightEventsFromDefaultLanguage(configId);

        assertThat(
                "The list size after adding the event must be larger than the list before adding the event by 1",
                afterRequest.size(),
                is(beforeRequest.size() + 1)
        );
    }

    @Test
    @DisplayName("Remove event from Default language")
    void removeAllEventFromDefaultLanguage() {
        beforeRequest = getHighlightEventsFromDefaultLanguage(configId);

        addEvents(
                eventIds,
                events,
                eventId,
                isPromo,
                isSafe,
                order
        );

        updateConfiguration(
                configId,
                sports,
                events,
                languageTabs
        );

        removeAllEvents(
                eventIds,
                events
        );

        updateConfiguration(
                configId,
                sports,
                events,
                languageTabs
        );

        afterRequest = getHighlightEventsFromDefaultLanguage(configId);

        assertThat(
                "After deleting all events, the list should be empty",
                afterRequest.size(),
                is(beforeRequest.size())
        );
    }

    @Test
    @DisplayName("Add an event with an incorrect value of the IsPromo and IsSafe parameters")
    void addEventWithAnIncorrectIsPromoAndIsSafeParameters() {
        isPromo = true;
        isSafe = true;

        beforeRequest = getHighlightEventsFromDefaultLanguage(configId);

        addEvents(
                eventIds,
                events,
                eventId,
                isPromo,
                isSafe,
                order
        );

        updateConfiguration(
                configId,
                sports,
                events,
                languageTabs
        );

        afterRequest = getHighlightEventsFromDefaultLanguage(configId);

        assertThat(
                "The size of the list before and after attempting to set an invalid event must be equal",
                afterRequest.size(),
                is(beforeRequest.size())
        );
    }

    // Найден баг. При добавлении значения -100 не возникает ошибка 400, как в остальных случаях.
    // Данная ошибка видна на frontend Backoffice
    @Test
    @DisplayName("Add an event with an incorrect order parameter")
    void addEventWithAnIncorrectOrderParameter() {
        order = -100;

        beforeRequest = getHighlightEventsFromDefaultLanguage(configId);

        addEvents(
                eventIds,
                events,
                eventId,
                isPromo,
                isSafe,
                order
        );

        updateConfiguration(
                configId,
                sports,
                events,
                languageTabs
        );

        afterRequest = getHighlightEventsFromDefaultLanguage(configId);

        assertThat(
                "The size of the list before and after attempting to set an invalid event must be equal",
                afterRequest.size(),
                is(beforeRequest.size())
        );
    }

    @Test
    @DisplayName("Add a language to the configuration with event")
    void addLanguageToTheConfigurationWithEvent() {
        beforeRequest = getFirstLanguageEventsFromConfig(configId);

        addEvents(
                eventIds,
                events,
                eventId,
                isPromo,
                isSafe,
                order
        );

        addLanguageToLanguagesTabs(
                languageId,
                languageIds,
                events,
                languageTabs
        );

        updateConfiguration(
                configId,
                sports,
                events,
                languageTabs
        );

        afterRequest = getFirstLanguageEventsFromConfig(configId);

        assertThat(
                "The list after setting the correct event must be greater than the list before setting the correct event by 1",
                afterRequest.size(),
                is(beforeRequest.size() + 1)
        );
    }

    @Test
    @DisplayName("Remove event from language")
    void removeEventFromLanguage() {
        beforeRequest = getHighlightEventsFromFirstLanguage(configId);

        addEvents(
                eventIds,
                events,
                eventId,
                isPromo,
                isSafe,
                order
        );

        addLanguageToLanguagesTabs(
                languageId,
                languageIds,
                events,
                languageTabs
        );

        updateConfiguration(
                configId,
                sports,
                events,
                languageTabs
        );

        removeEventFromLanguagesTabs(
                eventIds.getFirst(),
                eventIds,
                languageTabs
        );

        updateConfiguration(
                configId,
                sports,
                events,
                languageTabs
        );

        afterRequest =
                getHighlightEventsFromFirstLanguage(configId);

        assertThat(
                "The list before and after adding and deleting an event must be the same size",
                afterRequest.size(),
                is(beforeRequest.size())
        );
    }

    @Test
    @DisplayName("Remove language from configuration")
    void removeLanguageFromConfiguration() {
        beforeRequest = getLanguageTab(configId);

        addLanguageToLanguagesTabs(
                languageId,
                languageIds,
                events,
                languageTabs
        );

        updateConfiguration(
                configId,
                sports,
                events,
                languageTabs
        );

        removeLanguageFromLanguagesTabs(
                languageIds.getFirst(),
                languageIds,
                languageTabs
        );

        updateConfiguration(
                configId,
                sports,
                events,
                languageTabs
        );

        afterRequest = getLanguageTab(configId);

        assertThat(
                "The list before and after adding and removing a language must be the same size",
                afterRequest.size(),
                is(beforeRequest.size())
        );
    }

    @Test
    @DisplayName("Save configuration without sport")
    void saveConfigurationWithoutSport() {
        beforeRequest = getSportsFromConfig(configId);

        removeAllSports(
                sportIds,
                sports
        );

        updateConfiguration(
                configId,
                sports,
                events,
                languageTabs
        );

        afterRequest = getSportsFromConfig(configId);

        assertThat(
                "The list before and after attempting to save the config incorrectly should be the same",
                afterRequest.size(),
                is(beforeRequest.size())
        );
    }
}