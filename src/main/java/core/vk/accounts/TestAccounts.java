package core.vk.accounts;


import core.vk.data.GenderEnum;
import core.vk.data.PersonalAccountData;

import static core.vk.utils.NameAndDateGenerator.*;


public class TestAccounts {

    public static final PersonalAccountData DEFAULT_POSITIVE_UI_ACCOUNT = PersonalAccountData
            .builder()
            .firstName("Oleg")
            .lastName("Karlov")
            .gender(GenderEnum.MALE)
            .dayOfBirth("1")
            .monthOfBirth("Apr")
            .yearOfBirth("1993")
            .build();

    public static final PersonalAccountData POSITIVE_UI_ACCOUNT = PersonalAccountData
            .builder()
            .firstName(getRandomMaleName())
            .lastName(getRandomMaleLastName())
            .gender(GenderEnum.MALE)
            .dayOfBirth(getRandomDay())
            .monthOfBirth(getRandomMonth())
            .yearOfBirth(getRandomYear())
            .build();

    public static final PersonalAccountData POSITIVE_API_ACCOUNT_1 = PersonalAccountData
            .builder()
            .firstName(generateAnyString())
            .lastName(generateAnyString())
            .sex("2")
            .lang("3")
            .v("5.127")
            .bdate("2.8.1985")
            .vkui("1")
            .build();

    public static final PersonalAccountData POSITIVE_API_ACCOUNT_2 = PersonalAccountData
            .builder()
            .firstName(generateAnyString())
            .lastName(generateAnyString())
            .sex("2")
            .lang("3")
            .v("5.127")
            .bdate("2.3.1999")
            .vkui("1")
            .build();
}

