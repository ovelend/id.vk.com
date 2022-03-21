package core.vk.utils;

import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;

public class SelenideHelpers {

    public static void humanizedInput(By by, @NotNull String text) {
        final WebDriver webDriver = WebDriverRunner.getWebDriver();
        final WebElement element = webDriver.findElement(by);
        element.clear();
        text.chars()
                .forEach(i -> element.sendKeys(String.valueOf((char) i)));
    }
}

