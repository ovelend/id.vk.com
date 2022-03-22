package core.vk.utils;


import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDate;

import static core.vk.data.TestDataNames.*;

public class NameAndDateGenerator {

    private static final LocalDate current_date = LocalDate.now();
    public static final String[] MONTH =
            {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    public static String getRandomDay() {
        return String.valueOf(1 + (int) (Math.random() * 27));
    }

    public static String getRandomMonth() {
        return MONTH[(int) (Math.random() * 12)];
    }

    public static String getRandomYear() {
        return String.valueOf(
                (current_date.getYear() - 105) +          // the lowest possible year
                        (int) (Math.random() * 91));      // define the range so that user's age wouldn't be more than 14
    }

    /**
     * The methods below get random names and last names from ready arrays
     */
    public static String getRandomMaleName() {
        return MALE_NAMES[(int) (Math.random() * MALE_NAMES.length)];
    }

    public static String getRandomFemaleName() {
        return FEMALE_NAMES[(int) (Math.random() * FEMALE_NAMES.length)];
    }

    public static String getRandomMaleLastName() {
        return MALE_LAST_NAMES[(int) (Math.random() * MALE_LAST_NAMES.length)];
    }

    public static String getRandomFemaleLastName() {
        return FEMALE_LAST_NAMES[(int) (Math.random() * FEMALE_LAST_NAMES.length)];
    }

    /**
     * Generates string in a name/lastname format Xxxxxxx from 2 to 10 symbols
     */
    public static String generateAnyString() {
        String genStr = RandomStringUtils.randomAlphabetic(2, 10);
        return genStr.substring(0, 1).toUpperCase() + genStr.substring(1).toLowerCase();
    }

    public static String generateBDateForApiTests() {
        String day = getRandomDay();
        String month = String.valueOf(
                ((int) (Math.random() * 11)) + 1);
        String year = getRandomYear();
        return String.format("%s.%s.%s", day, month, year);
    }
}
