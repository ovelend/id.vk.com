package core.vk.api.specs;

import core.vk.data.PersonalAccountData;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.Cookie;
import io.restassured.specification.RequestSpecification;

import static core.vk.utils.ConfigProvider.APP_CONFIG;

/**
 * These are specifications what VkApiService use building the requests.
 * There are also some data what can be defined in .conf files and use in our specifications.
 */

public class VkApiServiceSpecs {

    private static final String loginVkComURL = APP_CONFIG.getString("api_urls.login.vk.com");
    private static final String apiVkCom = APP_CONFIG.getString("api_urls.api.vk.com");
    private static String cookieValue = APP_CONFIG.getString("vk.com.cookie");

    private static Cookie vkCookie = new Cookie.Builder("Cookie", cookieValue)
            .setSecured(true)
            .build();

    private static RequestSpecification commonSpec = new RequestSpecBuilder()
            .setBaseUri(apiVkCom)
            .setContentType("application/x-www-form-urlencoded")
            .build();

    public static RequestSpecification getProfileInfoReqSpec(PersonalAccountData data, String token) {
        return new RequestSpecBuilder()
                .addRequestSpecification(commonSpec)
                .addFormParam("lang", data.getLang())
                .addFormParam("v", data.getV())
                .addFormParam("access_token", token)
                .addFormParam("vkui", data.getVkui())
                .build();
    }

    public static RequestSpecification getRetrieveTokenReqSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(loginVkComURL)
                .addCookie(vkCookie)
                .setContentType("application/x-www-form-urlencoded")
                .addHeader("accept-language", "en-US,en;q=0.9")
                .addHeader("Accept", "*/*")
                .addHeader("origin", "https://id.vk.com")
                .addHeader("referer", "https://id.vk.com/")
                .addHeader("sec-ch-ua", "\" Not A;Brand\";v=\"99\", \"Chromium\";v=\"99\", \"Google Chrome\";v=\"99\"")
                .addHeader("sec-ch-ua-mobile", "?0")
                .addHeader("sec-ch-ua-platform", "\"Windows\"")
                .addHeader("sec-fetch-dest", "empty")
                .addHeader("sec-fetch-mode", "cors")
                .addHeader("sec-fetch-site", "same-site")
                .addHeader("sec-ch-ua-mobile", "?0")
                .addQueryParam("act", "web_token")
                .addFormParam("version", "1")
                .addFormParam("app_id", "7344294")
                .build();
    }

    public static RequestSpecification getUpdateProfileReqSpec(PersonalAccountData data, String token) {
        return new RequestSpecBuilder()
                .addRequestSpecification(commonSpec)
                .addFormParam("lang", data.getLang())
                .addFormParam("v", data.getV())
                .addFormParam("access_token", token)
                .addFormParam("sex", data.getSex())
                .addFormParam("bdate", data.getBdate())
                .addFormParam("first_name", data.getFirstName())
                .addFormParam("last_name", data.getLastName())
                .addFormParam("vkui", data.getVkui())
                .build();
    }

    public static RequestSpecification getCancelUpdateProfileReqSpec(PersonalAccountData data, String cancelReqId, String token) {
        return new RequestSpecBuilder()
                .addRequestSpecification(commonSpec)
                .addFormParam("lang", data.getLang())
                .addFormParam("v", data.getV())
                .addFormParam("access_token", token)
                .addFormParam("cancel_request_id", cancelReqId)
                .addFormParam("vkui", data.getVkui())
                .build();
    }

}
