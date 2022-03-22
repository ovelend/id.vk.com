package core.vk.api.validators;

import core.vk.data.PersonalAccountData;
import core.vk.utils.HttpResponseCodes;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;

import static core.vk.utils.ResponseBodyHelpers.getValueFromBody;
import static org.assertj.core.api.Assertions.assertThat;

public class VkApiServiceValidators {

    public static void validateUpdateProfileResp(Response updResp, PersonalAccountData data) {
        SoftAssertions updSoft = new SoftAssertions();
        updSoft.assertThat(updResp.statusCode()).isEqualTo(HttpResponseCodes.SC_OK);
        updSoft.assertThat(getValueFromBody(updResp, "response.name_request.first_name"))
                .isEqualTo(data.getFirstName());
        updSoft.assertThat(getValueFromBody(updResp, "response.name_request.last_name"))
                .isEqualTo(data.getLastName());
        updSoft.assertThat(getValueFromBody(updResp, "response.name_request.status"))
                .isEqualTo("processing");
        updSoft.assertAll();
    }

    public static void validateGetProfileInfoResp(
            Response profResp,
            PersonalAccountData data,
            String previousName,
            String previousLastName) {
        SoftAssertions profSoft = new SoftAssertions();
        profSoft.assertThat(profResp.statusCode()).isEqualTo(HttpResponseCodes.SC_OK);
        profSoft.assertThat(getValueFromBody(profResp, "response.first_name"))
                .isEqualTo(previousName);
        profSoft.assertThat(getValueFromBody(profResp, "response.last_name"))
                .isEqualTo(previousLastName);
        profSoft.assertThat(getValueFromBody(profResp, "response.bdate"))
                .isEqualTo(data.getBdate());
        profSoft.assertAll();
    }

    public static void validateCancelUpdateProfileResp(Response cancelResp) {
        assertThat(cancelResp.statusCode()).isEqualTo(HttpResponseCodes.SC_OK);
        assertThat(getValueFromBody(cancelResp, "response.changed")).isEqualTo("1");
    }
}
