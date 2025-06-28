package tests;

import com.altenar.sb2.backoffice.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

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

    static Stream<Arguments> incorrectOrderTest() {
        return Stream.of(
                Arguments.of(-100)
//                Arguments.of(1.25)
//                Arguments.of('c'),
//                Arguments.of("just string"),
//                Arguments.of(new BigInteger("12345671212121289012345678901234567890")),
//                Arguments.of(new int[] {1, 2, 3}),
//                Arguments.of(true)
        );
    }

    @BeforeEach
    void setUp() throws JsonProcessingException {
        sports = new ArrayList<>();
        sportIds = new ArrayList<>();
        languageIds = new ArrayList<>();
        languageTabs = new ArrayList<>();
        events = new ArrayList<>();
        eventIds = new ArrayList<>();

        sportIds.add(cricketId);
        championships = getCricketChampionships(sportIds);
        sports = getSports(championships);
        languageIds = getLanguageIds(configId);

        ObjectMapper mapper = new ObjectMapper();

        ApiResult response = updateConfiguration(
                configId,
                sports,
                new ArrayList<>(),
                new ArrayList<>()
        );

        System.out.println(mapper.writeValueAsString(sports));
        System.out.println(mapper.writeValueAsString(response));
    }

    @Test
    @DisplayName("Add an event with the correct IsPromo or IsSafe parameters")
    void addEventsWithCorrectIsPromoOrIsSafe() throws Exception {
        List<HighlightsEventRequestItem> firstLanguageEventsBeforeRequest =
                getHighlightEventsFromDefaultLanguage(configId);
        boolean isPromo = false;
        boolean isSafe = true;
        int order = 1;

        addEvents(
                championships,
                eventIds,
                events,
                sportIds,
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

        List<HighlightsEventRequestItem> firstLanguageEventsAfterRequest =
                getHighlightEventsFromDefaultLanguage(configId);

        assertThat(
                "The list size after adding the event must be larger than the list before adding the event by 1",
                firstLanguageEventsAfterRequest.size(),
                is(firstLanguageEventsBeforeRequest.size() + 1)
        );
    }

    @Test
    @DisplayName("Remove event from Default language")
    void removeAllEventFromDefaultLanguage() throws Exception {
        boolean isPromo = false;
        boolean isSafe = true;
        int order = 1;

        addEvents(
                championships,
                eventIds,
                events,
                sportIds,
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

        removeEvents(
                eventIds,
                events
        );

        updateConfiguration(
                configId,
                sports,
                events,
                languageTabs
        );

        List<HighlightsEventRequestItem> firstLanguageEventsAfterRequest =
                getHighlightEventsFromDefaultLanguage(configId);

        assertThat(
                "After deleting all events, the list should be empty",
                firstLanguageEventsAfterRequest.size(),
                is(0)
        );
    }

    @Test
    @DisplayName("Add an event with an incorrect value of the IsPromo and IsSafe parameters")
    void addEventWithAnIncorrectIsPromoAndIsSafeParameters() throws Exception {
        List<HighlightsEventRequestItem> firstLanguageEventsBeforeRequest =
                getHighlightEventsFromDefaultLanguage(configId);

        boolean isPromo = true;
        boolean isSafe = true;
        int order = 1;

        addEvents(
                championships,
                eventIds,
                events,
                sportIds,
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

        List<HighlightsEventRequestItem> firstLanguageEventsAfterRequest =
                getHighlightEventsFromDefaultLanguage(configId);

        assertThat(
                "The size of the list before and after attempting to set an invalid event must be equal",
                firstLanguageEventsAfterRequest.size(),
                is(firstLanguageEventsBeforeRequest.size())
        );
    }


    // Найден баг. При добавлении значения -100 не возникает ошибка 400, как в остальных случаях.
    // Данная ошибка видна на frontend Backoffice
    @ParameterizedTest
    @MethodSource("incorrectOrderTest")
    @DisplayName("Add an event with an incorrect order parameter")
    void addEventWithAnIncorrectOrderParameter(
            int order
    ) throws Exception {
        List<HighlightsEventRequestItem> firstLanguageEventsBeforeRequest =
                getHighlightEventsFromDefaultLanguage(configId);

        boolean isPromo = true;
        boolean isSafe = false;

        addEvents(
                championships,
                eventIds,
                events,
                sportIds,
                isPromo,
                isSafe,
                order
        );

        ApiResult res = updateConfiguration(
                configId,
                sports,
                events,
                languageTabs
        );

        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writeValueAsString(res));

        List<HighlightsEventRequestItem> firstLanguageEventsAfterRequest =
                getHighlightEventsFromDefaultLanguage(configId);

        System.out.println(firstLanguageEventsAfterRequest.size());
        System.out.println(firstLanguageEventsBeforeRequest.size());

        assertThat(
                "The size of the list before and after attempting to set an invalid event must be equal",
                firstLanguageEventsAfterRequest.size(),
                is(firstLanguageEventsBeforeRequest.size())
        );
    }

    @Test
    @DisplayName("Add a language to the configuration with event")
    void addLanguageToTheConfigurationWithEvent() throws Exception {
        List<ConfigEvent> firstLanguageEventsBeforeRequest =
                getFirstLanguageEventsFromConfig(configId);
        boolean isPromo = false;
        boolean isSafe = false;
        int order = 1;

        addEvents(
                championships,
                eventIds,
                events,
                sportIds,
                isPromo,
                isSafe,
                order
        );

        addFirstLanguageToLanguagesTabs(
                events,
                languageIds,
                languageTabs
        );

        updateConfiguration(
                configId,
                sports,
                events,
                languageTabs
        );

        List<ConfigEvent> firstLanguageEventsAfterRequest =
                getFirstLanguageEventsFromConfig(configId);

        assertThat(
                "The list after setting the correct event must be greater than the list before setting the correct event by 1",
                firstLanguageEventsAfterRequest.size(),
                is(firstLanguageEventsBeforeRequest.size() + 1)
        );
    }

    @Test
    @DisplayName("Remove event from language")
    void removeEventFromLanguage() throws Exception {
        List<HighlightsEventRequestItem> firstLanguageEventsBeforeRequest =
                getHighlightEventsFromFirstLanguage(configId);

        boolean isPromo = false;
        boolean isSafe = false;
        int order = 1;

        addEvents(
                championships,
                eventIds,
                events,
                sportIds,
                isPromo,
                isSafe,
                order
        );

        addFirstLanguageToLanguagesTabs(
                events,
                languageIds,
                languageTabs
        );

        updateConfiguration(
                configId,
                sports,
                events,
                languageTabs
        );

        removeFirstEventFromLanguagesTabs(
                languageTabs
        );

        updateConfiguration(
                configId,
                sports,
                events,
                languageTabs
        );

        List<HighlightsEventRequestItem> firstLanguageEventsAfterRequest =
                getHighlightEventsFromFirstLanguage(configId);

        assertThat(
                "The list before and after adding and deleting an event must be the same size",
                firstLanguageEventsAfterRequest.size(),
                is(firstLanguageEventsBeforeRequest.size())
        );
    }

    @Test
    @DisplayName("Remove language from configuration")
    void removeLanguageFromConfiguration() throws Exception {
        List<LanguageTabRequestItem> languagesBeforeRequest =
                getLanguageTab(configId);
        boolean isPromo = false;
        boolean isSafe = false;
        int order = 1;

        addEvents(
                championships,
                eventIds,
                events,
                sportIds,
                isPromo,
                isSafe,
                order
        );

        addFirstLanguageToLanguagesTabs(
                events,
                languageIds,
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

        List<LanguageTabRequestItem> languagesAfterRequest =
                getLanguageTab(configId);

        assertThat(
                "The list before and after adding and removing a language must be the same size",
                languagesAfterRequest.size(),
                is(languagesBeforeRequest.size())
        );
    }

    @Test
    @DisplayName("Save configuration without sport")
    void saveConfigurationWithoutSport() {
        List<ConfigSport> configSportsBeforeRequest = getSportsFromConfig(configId);

        removeSports(
                sportIds,
                sports
        );

        updateConfiguration(
                configId,
                sports,
                events,
                languageTabs
        );

        List<ConfigSport> configSportsAfterRequest = getSportsFromConfig(configId);

        assertThat(
                "The list before and after attempting to save the config incorrectly should be the same",
                configSportsAfterRequest.size(),
                is(configSportsBeforeRequest.size())
        );
    }
}