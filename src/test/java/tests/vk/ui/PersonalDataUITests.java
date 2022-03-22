package tests.vk.ui;


import io.qameta.allure.Description;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import core.vk.base.BaseUiTest;
import core.vk.pages.PersonalDataPage;
import core.vk.pages.VKLoginPage;

import static core.vk.accounts.TestAccounts.*;


public class PersonalDataUITests extends BaseUiTest {

    @BeforeMethod
    public void authorizeManuallyInVkCom() {
        VKLoginPage
                .openPage()
                .loginToVkAccount()
                .checkLoginSuccessfull();
    }

    @Test
    @Description("Check positive scenario")
    public void checkPersonalDataUpdate() {
        PersonalDataPage
                .openPage()
                .checkPersonalDataPageDisplayed()
                .rememberOldNameAndLastName(UI_ACCOUNT_1)
                .typeFullName(UI_ACCOUNT_1)
                .changeGender()
                .changeBirthdayFully(UI_ACCOUNT_1)
                .saveUpdates()
                .checkUpdateRequestMessageShown(UI_ACCOUNT_1)
                .checkNameAndLastNameStillOld(UI_ACCOUNT_1)
                .returnDefaultTestData(DEFAULT_UI_ACCOUNT);
    }

    @Test
    @Description("Check update request cancellation")
    public void cancelUpdateRequestCancellation() {
        PersonalDataPage
                .openPage()
                .checkPersonalDataPageDisplayed()
                .rememberOldNameAndLastName(UI_ACCOUNT_1)
                .typeFullName(UI_ACCOUNT_1)
                .changeBirthdayFully(UI_ACCOUNT_1)
                .saveUpdates()
                .cancelUpdateRequest()
                .checkNameAndLastNameStillOld(UI_ACCOUNT_1)
                .checkNewBirthdaySavedUpdated(UI_ACCOUNT_1)
                .returnDefaultTestData(DEFAULT_UI_ACCOUNT);
    }
}
