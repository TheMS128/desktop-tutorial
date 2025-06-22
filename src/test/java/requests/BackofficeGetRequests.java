package Requests;

import Tests.BackofficeApiTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class BackofficeRequests {
    public Response getHighlights() {
        Response response = given()
                .queryParam("timezoneOffset", "180")
                .queryParam("langId", 8)
                .queryParam("skinName", "betsonic")
                .queryParam("configid", 1)
                .queryParam("culture", "en-gb")
                .queryParam("countryCode", "RU")
                .queryParam("deviceTуре", "Desktop")
                .queryParam("numformat", "en")
                .queryParam("integration", "skintest")
                .queryParam( "sportId", 66)
                .queryParam("showALT.Events", false)
                .queryParam("count", 10)
                .when()
                .get("https://sb2frontend-altenar2-stage.biahosted.com/api/Sportsbook/GetLivenow")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .extract()
                .response();
        return response;
    }

    public static Response getBackOffice() {
        Response response = given()
                .queryParam("ReturnUrl", "%2F")
                .when()
                .get("https://sb2admin-altenar2-stage.biahosted.com/Account/Login")
                .then()
                .statusCode(200)
                .contentType("text/html")
                .extract().response();
        return response;
    }

    public static Response backofficeLogin() {
        Response response = given()
                .formParam("UserName", "test_user_qa1")
                .formParam("Password", "12vDEO~lTE$")
                .formParam("ReturnUrl", "/")
                .formParam("DeviceSecret", "00000000-0000-0000-0000-000000000000")
                .formParam("__RequestVerificationToken", "CfDJ8L6vrdOuk2tAllXaBx7HLKpiz6PCIXvfCcoi13vb46gsWVTUlR5hbNoIn-Hr2Z5HesfauG0Myc9s1Cd4wyR7mV_kQZ6yerh93ZL9GpEo4Vc1W_CDPq5sZZ9Dff2TPJR4MXvqeUU5_TEIQAoi-3U3Nus")
                .when()
                .post("https://sb2admin-altenar2-stage.biahosted.com/Account/Login")
                .then()
                .statusCode(302)
                .extract().response();
        return response;
    }

    public static Response getBackOfficeWithSkintestConfig() {
        Response response = given()
                .queryParam("ReturnUrl", "%2F")
                .cookies(BackofficeApiTest.cookie)
                .contentType(ContentType.HTML)
                .when()
                .get("https://sb2admin-altenar2-stage.biahosted.com/v2/highlights/configs/126")
                .then()
                .statusCode(200)
                .extract().response();
        return response;
    }

    public static Response getEvents(String requestBody) {
        Response response = given()
                .cookies(BackofficeApiTest.cookie)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("https://sb2admin-altenar2-stage.biahosted.com/Api/HighlightsManager/SearchEvents")
                .then()
                .statusCode(200)
                .extract().response();
        return response;
    }

    public static Response getSports() {
        Response response = given()
                .cookies(BackofficeApiTest.cookie)
                .contentType(ContentType.JSON)
                .when()
                .get("https://sb2admin-altenar2-stage.biahosted.com/Api/HighlightsManager/SportsList")
                .then()
                .statusCode(200)
                .extract().response();
        return response;
    }

    public static Response getChampionships(String requestBody) {
//        String requestBody = String.format(
//                "{\"SportIds\":[%d],\"dateFrom\":\"%s\",\"dateTo\":\"%s\"}",
//                74, "2025-06-20 10:54:05", "2026-06-20 23:59:59"
//        );

        Response response = given()
                .cookies(BackofficeApiTest.cookie)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("https://sb2admin-altenar2-stage.biahosted.com/Api/HighlightsManager/GetChampionships")
                .then()
                .statusCode(200)
                .extract().response();
        return response;
    }

    public static Response getLanguages() {
        Response response = given()
                .cookies(BackofficeApiTest.cookie)
                .contentType(ContentType.JSON)
                .when()
                .get("https://sb2admin-altenar2-stage.biahosted.com/Api/HighlightsManager/LanguagesList")
                .then()
                .statusCode(200)
                .extract().response();
        return response;
    }

    public static Response updateConfig(String requestBody) {
        Response response = given()
                .cookies(BackofficeApiTest.cookie)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("https://sb2admin-altenar2-stage.biahosted.com/Api/HighlightsManager/UpdateConfig")
                .then()
                .statusCode(200)
                .extract().response();
        return response;
    }
}
