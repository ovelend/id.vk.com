package core.vk.data;

public enum GenderEnum {

    MALE("MALE"),
    FEMALE("FEMALE");

    String value;

    GenderEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public String toString() {
        return String.valueOf(this.value);
    }

}