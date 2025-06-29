package requests;

import com.altenar.sb2.backoffice.model.*;
import com.github.viclovsky.swagger.coverage.CoverageOutputWriter;
import com.github.viclovsky.swagger.coverage.FileSystemOutputWriter;
import com.github.viclovsky.swagger.coverage.SwaggerCoverageRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import models.GetChampionsBody.GetChampionshipsBody;
import models.SearchEventsBody.SearchEventsBody;

import java.nio.file.Paths;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static util.ConfigLoader.getPassword;
import static util.ConfigLoader.getUsername;

public class BackofficeRequests {
    private static Map<String, String> cookie;
    private static SwaggerCoverageRestAssured filter;
    private static final String pathToSwaggerCoverage = "swagger_coverage_outputs";

    static {
        RestAssured.baseURI = "https://sb2admin-altenar2-stage.biahosted.com";
        CoverageOutputWriter writer = new FileSystemOutputWriter(Paths.get(pathToSwaggerCoverage));
        filter = new SwaggerCoverageRestAssured(writer);
        setCookies();
    }

    public static void setCookies() {
        cookie = given()
                .formParam("UserName", getUsername())
                .formParam("Password", getPassword())
                .formParam("ReturnUrl", "/")
                .filter(filter)
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
                .filter(filter)
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
                .filter(filter)
            .when()
                .get("Api/HighlightsManager/GetConfigSettings")
            .then()
                .statusCode(200)
                .extract()
                .as(HighlightsConfigSettingsApiResult.class);
    }

    public static EventSportItemListApiResult getChampionships(GetChampionshipsBody requestBody) {
        return given()
                .cookies(cookie)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .filter(filter)
            .when()
                .post("/Api/HighlightsManager/GetChampionships")
            .then()
                .statusCode(200)
                .extract()
                .as(EventSportItemListApiResult.class);
    }

    public static EventCandidateItemListApiResult getEvents(SearchEventsBody requestBody) {
        return given()
                .cookies(cookie)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .filter(filter)
            .when()
                .post("/Api/HighlightsManager/SearchEvents")
            .then()
                .statusCode(200)
                .extract()
                .as(EventCandidateItemListApiResult.class);
    }

    public static void updateConfig(UpdateHighlightsConfigRequest requestBody) {
        given()
                .cookies(cookie)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .filter(filter)
            .when()
                .post("/Api/HighlightsManager/UpdateConfig");
    }
}
