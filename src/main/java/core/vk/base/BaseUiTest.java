package core.vk.base;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import core.vk.utils.ConfigProvider;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;

public class BaseUiTest extends BaseApiTest {

    /**
     * Here it's possible to add any preferences for Selenide like
     * headless, browser and its options directly or retrieve some settings from config
     */
    @BeforeSuite
    public void setSelenidePreferences() {
        Configuration.headless = Boolean.valueOf(
                ConfigProvider.APP_CONFIG.getString("selenide.headless")
        );
    }

    @AfterMethod
    public void closeDriver() {
        WebDriverRunner.closeWebDriver();
    }

}
