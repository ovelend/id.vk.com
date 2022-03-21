package core.vk.data;

public enum DateEnum {

    DAY("day"),
    MONTH("month"),
    YEAR("year");

    String value;

    DateEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public String toString() {
        return String.valueOf(this.value);
    }

}
