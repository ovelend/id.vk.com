package core.vk.api;

import io.qameta.allure.Step;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.Cookie;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import core.vk.data.PersonalAccountData;

import static core.vk.utils.ConfigProvider.APP_CONFIG;
import static io.restassured.RestAssured.given;

public class VkApiService {

    private String cookieValue = APP_CONFIG.getString("vk.com.cookie");
    private Cookie vkCookie = new Cookie.Builder("Cookie", cookieValue)
            .setSecured(true)
            .build();

    @Step
    public String retrieveToken() {
        Response resp = given()
                .baseUri("https://login.vk.com/")
                .cookie(vkCookie)
                .header("accept-language", "en-US,en;q=0.9")
                .header("Accept", "*/*")
                .header("origin", "https://id.vk.com")
                .header("referer", "https://id.vk.com/")
                .header("sec-ch-ua", "\" Not A;Brand\";v=\"99\", \"Chromium\";v=\"99\", \"Google Chrome\";v=\"99\"")
                .header("sec-ch-ua-mobile", "?0")
                .header("sec-ch-ua-platform", "\"Windows\"")
                .header("sec-fetch-dest", "empty")
                .header("sec-fetch-mode", "cors")
                .header("sec-fetch-site", "same-site")
                .header("sec-ch-ua-mobile", "?0")
                .queryParam("act", "web_token")
                .formParam("version", "1")
                .formParam("app_id", "7344294")
                .post();
        return getValueFromBody(resp, "data.access_token");
    }

    @Step
    public Response getProfileInfo(PersonalAccountData data, String token) {
        return given()
                .spec(setSpec())
                .formParam("lang", data.getLang())
                .formParam("v", data.getV())
                .formParam("access_token", token)
                .formParam("vkui", data.getVkui())
                .post("/method/account.getProfileInfo");
    }

    @Step
    public Response updateProfile(PersonalAccountData data, String token) {
        return given()
                .spec(setSpec())
                .formParam("lang", data.getLang())
                .formParam("v", data.getV())
                .formParam("access_token", token)
                .formParam("sex", data.getSex())
                .formParam("bdate", data.getBdate())
                .formParam("first_name", data.getFirstName())
                .formParam("last_name", data.getLastName())
                .formParam("vkui", data.getVkui())
                .post("/method/account.saveProfileInfo");
    }

    @Step
    public Response cancelUpdateProfileRequest(PersonalAccountData data, String cancelReqId, String token) {
        return given()
                .spec(setSpec())
                .formParam("lang", data.getLang())
                .formParam("v", data.getV())
                .formParam("access_token", token)
                .formParam("cancel_request_id", cancelReqId)
                .formParam("vkui", data.getVkui())
                .post("/method/account.saveProfileInfo");
    }

    public static String getValueFromBody(Response resp, String path) {
        if (resp != null) {
            return resp.getBody().path(path).toString();
        } else throw new RuntimeException("The response wasn't recieved");
    }

    private static RequestSpecification setSpec() {
        return new RequestSpecBuilder()
                .setBaseUri("https://api.vk.com")
                .setContentType("application/x-www-form-urlencoded")
                .build();
    }

}
