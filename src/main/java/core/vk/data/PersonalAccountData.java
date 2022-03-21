package core.vk.data;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PersonalAccountData {

    String firstName;
    String lastName;
    String previousFirstName;
    String previousLastName;
    GenderEnum gender;
    String dayOfBirth;
    String monthOfBirth;
    String yearOfBirth;
    String lang;
    String v;
    String sex;
    String bdate;
    String vkui;

    public static String getFullName(PersonalAccountData data) {
        return String.format("%s %s", data.getFirstName(), data.getLastName());
    }

    public void setFirstName() {
        this.previousFirstName = previousFirstName;
    }

    public void setPreviousLastName(String previousLastName) {
        this.previousLastName = previousLastName;
    }
}
