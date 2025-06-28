package requests;

import com.altenar.sb2.backoffice.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Objects;

import static io.restassured.RestAssured.given;
import static util.ConfigLoader.getPassword;
import static util.ConfigLoader.getUsername;

public class BackofficeRequests {
    private static final Logger log = LoggerFactory.getLogger(BackofficeRequests.class);
    public static Map<String, String> cookie;

    static {
        RestAssured.baseURI = "https://sb2admin-altenar2-stage.biahosted.com";
        setCookies();
    }

    public static void setCookies() {
        cookie = given()
                .formParam("UserName", getUsername())
                .formParam("Password", getPassword())
                .formParam("ReturnUrl", "/")
            .when()
                .post("/Account/Login")
            .then()
                .statusCode(302)
                .extract()
                .cookies();
    }

    public static LanguageItemListApiResult getLanguages() {
        return given()
                .cookies(cookie)
                .contentType(ContentType.JSON)
            .when()
                .get("/Api/HighlightsManager/LanguagesList")
            .then()
                .statusCode(200)
                .extract()
                .as(LanguageItemListApiResult.class);
    }

    public static HighlightsConfigSettingsApiResult getConfigSettings(int configId) {
        return given()
                .cookies(cookie)
                .contentType(ContentType.JSON)
                .queryParam("configId", Integer.toString(configId))
            .when()
                .get("Api/HighlightsManager/GetConfigSettings")
            .then()
                .statusCode(200)
                .extract()
                .as(HighlightsConfigSettingsApiResult.class);
    }

    public static EventSportItemListApiResult getChampionships(GetHighlightsChampionshipsRequest requestBody) {
        return given()
                .cookies(cookie)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .log().all()
            .when()
                .post("/Api/HighlightsManager/GetChampionships")
            .then()
                .statusCode(200)
                .log().all()
                .extract()
                .as(EventSportItemListApiResult.class);
    }

    public static EventCandidateItemListApiResult getEvents(models.SearchEventsBody.Root requestBody) {
        return given()
                .cookies(cookie)
                .contentType(ContentType.JSON)
                .body(requestBody)
            .when()
                .post("/Api/HighlightsManager/SearchEvents")
            .then()
                .statusCode(200)
                .extract()
                .as(EventCandidateItemListApiResult.class);
    }

    public static ApiResult updateConfig(UpdateHighlightsConfigRequest requestBody) {
        return given()
                .cookies(cookie)
                .contentType(ContentType.JSON)
                .body(requestBody)
            .when()
                .post("/Api/HighlightsManager/UpdateConfig")
            .then()
                .extract()
                .as(ApiResult.class);
    }
}
