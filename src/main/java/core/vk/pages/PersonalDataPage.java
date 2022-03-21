package core.vk.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import core.vk.data.DateEnum;
import io.qameta.allure.Step;
import org.apache.commons.lang3.ArrayUtils;
import org.assertj.core.api.SoftAssertions;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import core.vk.data.PersonalAccountData;

import java.time.Duration;
import java.util.function.Function;

import static com.codeborne.selenide.Selenide.*;
import static core.vk.data.PersonalAccountData.getFullName;
import static core.vk.utils.NameAndDateGenerator.*;
import static core.vk.utils.SelenideHelpers.humanizedInput;

public class PersonalDataPage extends BasePage {

    private static final By SIDE_BAR = By.cssSelector("nav.NavigationMenu__nav");
    private static final By FIRST_NAME_INPUT = By.cssSelector("input[name='first_name']");
    private static final By LAST_NAME_INPUT = By.cssSelector("input[name='last_name']");
    private static final By GENDER_DROPDOWN = By.cssSelector("div.Select__container");
    private static final By GENDER_UNCHECKED_DROPDOWN = By.cssSelector("div[role='option'][aria-selected='false']");
    private static final By DAY_DROPDOWN = By.cssSelector("div.DatePicker__day div.Select");
    private static final By DAY_VALUE_CELL = By.cssSelector("div.DatePicker__day div.Select div span");
    private static final By MONTH_DROPDOWN = By.cssSelector("div.DatePicker__month div.Select");
    private static final By MONTH_VALUE_CELL = By.cssSelector("div.DatePicker__month div.Select div span");
    private static final By YEAR_DROPDOWN = By.cssSelector("div.DatePicker__year div.Select");
    private static final By YEAR_VALUE_CELL = By.cssSelector("div.DatePicker__year div.Select div span");
    private static final By SAVE_BUTTON = By.xpath("//span[contains(text(),'Save')]");
    private static final By HEADER_USER_NAME = By.cssSelector("h3");
    private static final By HEADER_VERIFICATION_MESSAGE = By.xpath("//span[contains(text(),'Your name change request has been sent for verification')]");
    private static final By HEADER_VERIFICATION_NAME = By.xpath("//span[@class='NameRequest__fullName']");
    private static final By CANCEL_REQUEST_BUTTON = By.xpath("//button[contains(text(),'Cancel request')]");
    private static Function<String, By> CHOOSE_ITEM_IN_DROPDOWN =
            (value) -> By.cssSelector(
                    ("div[role='option'][aria-selected='false'][title='%s']").formatted(value));

    @Step
    public static PersonalDataPage openPage() {
        return open(VK_ID_BASE_URL, PersonalDataPage.class);
    }

    @Step
    public PersonalDataPage checkPersonalDataPageDisplayed() {
        $(SIDE_BAR).shouldBe(Condition.visible, Duration.ofSeconds(8));
        return this;
    }

    @Step
    public PersonalDataPage typeFirstName(String firstName) {
        $(FIRST_NAME_INPUT).shouldBe(Condition.visible);
        humanizedInput(FIRST_NAME_INPUT, firstName);
        return this;
    }

    @Step
    public PersonalDataPage typeLastName(String lastName) {
        $(LAST_NAME_INPUT).shouldBe(Condition.visible);
        humanizedInput(LAST_NAME_INPUT, lastName);
        return this;
    }

    @Step
    public PersonalDataPage typeFullName(PersonalAccountData data) {
        typeFirstName(data.getFirstName());
        typeLastName(data.getLastName());
        return this;
    }

    @Step
    public PersonalDataPage changeGender() {
        $(GENDER_DROPDOWN).shouldBe(Condition.visible).click();
        $(GENDER_UNCHECKED_DROPDOWN).hover().click();
        return this;
    }

    @Step
    public PersonalDataPage changeDayOfBirth(String day) {
        String uniqueDay = checkActualDataDiffersFromUIData(DAY_VALUE_CELL, day, DateEnum.DAY);
        $(DAY_DROPDOWN).shouldBe(Condition.visible).click();
        $(CHOOSE_ITEM_IN_DROPDOWN.apply(uniqueDay)).hover().click();
        return this;
    }

    @Step
    public PersonalDataPage changeMonthOfBirth(String month) {
        String uniqueMonth = checkActualDataDiffersFromUIData(MONTH_VALUE_CELL, month, DateEnum.MONTH);
        $(MONTH_DROPDOWN).shouldBe(Condition.visible).click();
        $(CHOOSE_ITEM_IN_DROPDOWN.apply(uniqueMonth)).hover().click();
        return this;
    }

    @Step
    public PersonalDataPage changeYearOfBirth(String year) {
        String uniqueYear = checkActualDataDiffersFromUIData(YEAR_VALUE_CELL, year, DateEnum.YEAR);
        $(YEAR_DROPDOWN).shouldBe(Condition.visible).click();
        $(CHOOSE_ITEM_IN_DROPDOWN.apply(uniqueYear)).hover().click();
        return this;
    }

    @Step
    public PersonalDataPage changeBirthdayFully(PersonalAccountData data) {
        changeDayOfBirth(data.getDayOfBirth());
        changeMonthOfBirth(data.getMonthOfBirth());
        changeYearOfBirth(data.getYearOfBirth());
        return this;
    }

    @Step
    public PersonalDataPage saveUpdates() {
        $(HEADER_USER_NAME).click();
        $(SAVE_BUTTON).shouldBe(Condition.enabled).click();
        Selenide.sleep(4000);
        return this;
    }

    @Step
    public PersonalDataPage checkUpdateRequestMessageShown(PersonalAccountData data) {
        if ($(HEADER_VERIFICATION_MESSAGE).isDisplayed()) {
            $(HEADER_VERIFICATION_MESSAGE).shouldBe(Condition.visible, Duration.ofSeconds(7));
            $(HEADER_VERIFICATION_NAME).shouldHave(Condition.exactText(getFullName(data)));
        }
        return this;
    }

    @Step
    public PersonalDataPage cancelUpdateRequest() {
        $(CANCEL_REQUEST_BUTTON).click();
        refresh();
        return this;
    }

    /**
     * Retrieve and set old data from UI for making comparison after
     */
    @Step
    public PersonalDataPage rememberOldNameAndLastName(PersonalAccountData data) {
        data.setPreviousFirstName($(FIRST_NAME_INPUT).getAttribute("value"));
        data.setPreviousLastName($(LAST_NAME_INPUT).getAttribute("value"));
        return this;
    }

    @Step
    public PersonalDataPage checkNameAndLastNameStillOld(PersonalAccountData data) {
        refresh();
        checkPersonalDataPageDisplayed();
        $(HEADER_USER_NAME).shouldHave(Condition.exactText(data.getPreviousFirstName() + " " + data.getPreviousLastName()));
        return this;
    }

    @Step
    public PersonalDataPage checkNewBirthdaySavedUpdated(PersonalAccountData data) {
        SoftAssertions soft = new SoftAssertions();
        soft.assertThat($(DAY_DROPDOWN).getAttribute("value"))
                .isEqualTo(data.getDayOfBirth());
        soft.assertThat(Integer.valueOf($(MONTH_DROPDOWN).getAttribute("value")))    // The value on UI is always int (like Jan=1,Dec=12)
                .isEqualTo(
                        ArrayUtils.indexOf(MONTH, data.getMonthOfBirth()) + 1);  // Get index of any month and increase by 1
        soft.assertThat($(YEAR_DROPDOWN).getAttribute("value"))
                .isEqualTo(data.getYearOfBirth());
        soft.assertAll();
        return this;
    }

    @Step
    public PersonalDataPage returnDefaultTestData(PersonalAccountData data) {
        refresh();
        if (!getHeaderNameText().equals(data.getPreviousFirstName() + " " + data.getPreviousLastName())) {
            typeFullName(data);
            saveUpdates();
        }
        return this;
    }

    private String getHeaderNameText() {
        return $(HEADER_USER_NAME).getText();
    }

    /**
     * If any data is already chosen on UI, generate new one
     */
    private String checkActualDataDiffersFromUIData(@NotNull By CELL, String value, DateEnum date) {
        while ($(CELL).getText().equals(value)) {
            value = findRandomValue(date);
        }
        return value;
    }

    private String findRandomValue(DateEnum date) {
        return switch (date) {
            case DAY -> getRandomDay();
            case MONTH -> getRandomMonth();
            case YEAR -> getRandomYear();
            default -> "none";
        };
    }
}
