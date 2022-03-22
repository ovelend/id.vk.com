package core.vk.api.services;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import core.vk.data.PersonalAccountData;

import static core.vk.api.ResponseBodeHelper.ResponseBodyHelper.getValueFromBody;
import static core.vk.api.helpers.VkApiServiceHelper.*;
import static io.restassured.RestAssured.given;

public class VkApiService {

    @Step
    public String retrieveToken() {
        Response resp = given()
                .spec(getRetrieveTokenReqSpec())
                .post();
        return getValueFromBody(resp, "data.access_token");
    }

    @Step
    public Response getProfileInfo(PersonalAccountData data, String token) {
        return given()
                .spec(getProfileInfoReqSpec(data, token))
                .post("/method/account.getProfileInfo");
    }

    @Step
    public Response updateProfile(PersonalAccountData data, String token) {
        return given()
                .spec(getUpdateProfileReqSpec(data, token))
                .post("/method/account.saveProfileInfo");
    }

    @Step
    public Response cancelUpdateProfileRequest(PersonalAccountData data, String cancelReqId, String token) {
        return given()
                .spec(getCancelUpdateProfileReqSpec(data, cancelReqId, token))
                .post("/method/account.saveProfileInfo");
    }

}
