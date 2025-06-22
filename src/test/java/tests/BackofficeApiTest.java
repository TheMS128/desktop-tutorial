import Steps.BackofficeApiSteps;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Date.GenerateFormatDate;
import models.GetChampionships.Champ;
import models.UpdateConfig.Sport;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class BackofficeApiTest {
    public static String baseUrl = "https://sb2admin-altenar2-stage.biahosted.com";
    public static Map<String, String> cookie;
    public static ObjectMapper mapper = new ObjectMapper();
    public static Random rand = new Random();

    @BeforeAll
    public static void setUp() {
        cookie = BackofficeApiSteps.getCookies();
    }

    @Test
    @DisplayName("Add event to Default language")
    public void addEventToDefaultLanguage() throws JsonProcessingException {
        int cricketId = 74;

        // Шаг 1: получить все виды спорта (опционально, если нужно)
        models.SportsList.Root allSports = BackofficeApiSteps.getAllSports();

        // Шаг 2: получить чемпионаты по крикету
        models.GetChampionships.Root championships = BackofficeApiSteps.getCricketChampionships(cricketId);

        // Шаг 3: подготовить список sports для конфигурации
        ArrayList<Sport> sportsForConfig = BackofficeApiSteps.prepareSportsForUpdate(championships);

        // Шаг 4: собрать все чемпионаты в один список
        ArrayList<Champ> allChamps = BackofficeApiSteps.extractAllChamps(championships);

        // Шаг 5: сформировать тело запроса для получения событий
        String requestBody = String.format(
                "{\"dateFrom\":\"%s\", \"dateTo\":\"%s\", \"sportIds\":[74], \"champId\":%d}",
                GenerateFormatDate.getCurrentDate(),
                GenerateFormatDate.getNextYearDate(),
                allChamps.get(BackofficeApiTest.rand.nextInt(allChamps.size())).champId
        );

        // Шаг 6: получить события
        models.SearchEvents.Root events = BackofficeApiSteps.getEventsByRequest(requestBody);

        // Шаг 7: обновить конфигурацию с выбранным событием и видами спорта
        models.UpdateConfig.Response resp = BackofficeApiSteps.updateConfigWithEvent(sportsForConfig, events);

        // Проверка успешного обновления
        assertThat(resp.success, is(true));
    }
}