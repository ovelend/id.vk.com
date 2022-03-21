package tests.vk.ui;


import io.qameta.allure.Description;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import core.vk.base.BaseUiTest;
import core.vk.pages.PersonalDataPage;
import core.vk.pages.VKLoginPage;

import static core.vk.accounts.TestAccounts.DEFAULT_POSITIVE_UI_ACCOUNT;
import static core.vk.accounts.TestAccounts.POSITIVE_UI_ACCOUNT;


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
                .rememberOldNameAndLastName(POSITIVE_UI_ACCOUNT)
                .typeFullName(POSITIVE_UI_ACCOUNT)
                .changeGender()
                .changeBirthdayFully(POSITIVE_UI_ACCOUNT)
                .saveUpdates()
                .checkUpdateRequestMessageShown(POSITIVE_UI_ACCOUNT)
                .checkNameAndLastNameStillOld(POSITIVE_UI_ACCOUNT)
                .returnDefaultTestData(DEFAULT_POSITIVE_UI_ACCOUNT);
    }

    @Test
    @Description("Check update request cancellation")
    public void cancelUpdateRequestCancellation() {
        PersonalDataPage
                .openPage()
                .checkPersonalDataPageDisplayed()
                .rememberOldNameAndLastName(POSITIVE_UI_ACCOUNT)
                .typeFullName(POSITIVE_UI_ACCOUNT)
                .changeBirthdayFully(POSITIVE_UI_ACCOUNT)
                .saveUpdates()
                .cancelUpdateRequest()
                .checkNameAndLastNameStillOld(POSITIVE_UI_ACCOUNT)
                .checkNewBirthdaySavedUpdated(POSITIVE_UI_ACCOUNT)
                .returnDefaultTestData(DEFAULT_POSITIVE_UI_ACCOUNT);
    }
}
