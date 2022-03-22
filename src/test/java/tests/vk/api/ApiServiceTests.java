package tests.vk.api;

import core.vk.api.services.VkApiService;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import core.vk.base.BaseApiTest;

import static core.vk.accounts.TestAccounts.*;
import static core.vk.api.validators.VkApiServiceValidators.*;
import static core.vk.utils.ResponseBodyHelpers.getValueFromBody;

public class ApiServiceTests extends BaseApiTest {

    private final VkApiService vkApiService = new VkApiService();
    private String authToken;

    // Retrieve auth token before each test
    @BeforeMethod
    public void authorize() {
        authToken = vkApiService.retrieveToken();
    }


    @Test
    @Description("Update personal info and check that the data is updated properly")
    public void checkApiUpdateAllPersonalInfo() {

        // Retrieve old profile data
        Response getResp = vkApiService.getProfileInfo(API_ACCOUNT_1, authToken);
        String previousName = getValueFromBody(getResp, "response.first_name");
        String previousLastName = getValueFromBody(getResp, "response.last_name");

        // Update profile data and ensure that the changings are in the process
        Response updResp = vkApiService.updateProfile(API_ACCOUNT_1, authToken);
        validateUpdateProfileResp(updResp, API_ACCOUNT_1);

        // Get profile again and ensure that first_name and last_name are old, but the birthday has changed
        Response getProfResp = vkApiService.getProfileInfo(API_ACCOUNT_1, authToken);
        validateGetProfileInfoResp(getProfResp, API_ACCOUNT_1, previousName, previousLastName);

        // TODO when we have a documentation we'll be able to check
        //  if data are correct in some period of time
    }

    @Test()
    @Description("Update personal info and after cancel this request")
    public void checkApiUpdateRequestCancellation() {

        // Retrieve old profile data
        Response getResp = vkApiService.getProfileInfo(API_ACCOUNT_2, authToken);
        String previousName = getValueFromBody(getResp, "response.first_name");
        String previousLastName = getValueFromBody(getResp, "response.last_name");

        // Update profile data and retrieve cancel request Id
        String cancelReqId = getValueFromBody(
                vkApiService.updateProfile(API_ACCOUNT_2, authToken),
                "response.name_request.id");

        // Cancel update request and check its status
        Response cancelResp = vkApiService.cancelUpdateProfileRequest(API_ACCOUNT_2, cancelReqId, authToken);
        validateCancelUpdateProfileResp(cancelResp);

        // Get profile again and ensure that first_name and last_name are old, but the birthday has changed
        Response getProfResp = vkApiService.getProfileInfo(API_ACCOUNT_2, authToken);
        validateGetProfileInfoResp(getProfResp, API_ACCOUNT_2, previousName, previousLastName);

        // TODO when we have a documentation we'll be able to check if
        //  data are correct in some period of time
    }

}
