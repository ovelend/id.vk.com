package core.vk.pages;

import com.codeborne.selenide.Condition;
import core.vk.utils.ConfigProvider;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static core.vk.utils.SelenideHelpers.humanizedInput;

public class VKLoginPage extends BasePage {

    private static final By LOGIN_INPUT = By.cssSelector("#index_email");
    private static final By PASSWORD_INPUT = By.cssSelector("#index_pass");
    private static final By ENTER_BUTTON = By.cssSelector("#index_login_button");
    private static final By SIDE_BAR = By.cssSelector("#side_bar");

    @Step
    public static VKLoginPage openPage() {
        return open(VK_COM_BASE_URL, VKLoginPage.class);
    }

    @Step
    public VKLoginPage loginToVkAccount() {
        humanizedInput(LOGIN_INPUT, ConfigProvider.APP_CONFIG.getString("login"));
        humanizedInput(PASSWORD_INPUT, ConfigProvider.APP_CONFIG.getString("password"));
        $(ENTER_BUTTON).click();
        return this;
    }

    @Step
    public VKLoginPage checkLoginSuccessfull() {
        $(SIDE_BAR).shouldBe(Condition.visible, Duration.ofSeconds(8));
        return this;
    }

}
