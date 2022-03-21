package tests.vk.api;

import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import core.vk.api.VkApiService;
import core.vk.base.BaseApiTest;
import core.vk.utils.HttpResponseCodes;

import static core.vk.accounts.TestAccounts.POSITIVE_API_ACCOUNT_2;
import static core.vk.api.VkApiService.getValueFromBody;
import static core.vk.accounts.TestAccounts.POSITIVE_API_ACCOUNT_1;
import static org.assertj.core.api.Assertions.assertThat;

public class ApiServiceTests extends BaseApiTest {

    private VkApiService vkApiService = new VkApiService();
    private String authToken;

    /**
     * Retrieve auth token before each test
     */
    @BeforeMethod
    public void authorize() {
        authToken = vkApiService.retrieveToken();
    }

    //TODO Think about transfering all the assertions to another class
    @Test
    @Description("Update personal info and check that the data is updated properly")
    public void checkApiUpdateAllPersonalInfo() {

        /** Retrieve old profile data */
        Response getResp = vkApiService.getProfileInfo(POSITIVE_API_ACCOUNT_1, authToken);
        String previousName = getValueFromBody(getResp, "response.first_name");
        String previousLastName = getValueFromBody(getResp, "response.last_name");

        /** Update profile data and ensure that the changings are in the process */
        Response updResp = vkApiService.updateProfile(POSITIVE_API_ACCOUNT_1, authToken);
        SoftAssertions updSoft = new SoftAssertions();
        updSoft.assertThat(updResp.statusCode()).isEqualTo(HttpResponseCodes.SC_OK);
        updSoft.assertThat(getValueFromBody(updResp, "response.name_request.first_name"))
                .isEqualTo(POSITIVE_API_ACCOUNT_1.getFirstName());
        updSoft.assertThat(getValueFromBody(updResp, "response.name_request.last_name"))
                .isEqualTo(POSITIVE_API_ACCOUNT_1.getLastName());
        updSoft.assertThat(getValueFromBody(updResp, "response.name_request.status"))
                .isEqualTo("processing");
        updSoft.assertAll();

        /** Get profile again and ensure that first_name and last_name is old, but the birthday has changed */
        Response profResp = vkApiService.getProfileInfo(POSITIVE_API_ACCOUNT_1, authToken);
        SoftAssertions profSoft = new SoftAssertions();
        profSoft.assertThat(profResp.statusCode()).isEqualTo(HttpResponseCodes.SC_OK);
        profSoft.assertThat(getValueFromBody(profResp, "response.first_name"))
                .isEqualTo(previousName);
        profSoft.assertThat(getValueFromBody(profResp, "response.last_name"))
                .isEqualTo(previousLastName);
        profSoft.assertThat(getValueFromBody(profResp, "response.bdate"))
                .isEqualTo(POSITIVE_API_ACCOUNT_1.getBdate());
        profSoft.assertAll();
    }

    @Test()
    @Description("Update personal info and after cancel this request")
    public void checkApiUpdateRequestCancellation() {

        /** Update profile data and retrieve cancel request Id */
        String cancelReqId = getValueFromBody(
                vkApiService.updateProfile(POSITIVE_API_ACCOUNT_2, authToken),
                "response.name_request.id");

        /** Cancel update request and check its status*/
        Response cancelResp = vkApiService.cancelUpdateProfileRequest(POSITIVE_API_ACCOUNT_2, cancelReqId, authToken);
        assertThat(cancelResp.statusCode()).isEqualTo(HttpResponseCodes.SC_OK);
        assertThat(getValueFromBody(cancelResp, "response.changed")).isEqualTo("1");
    }

}
