package core.vk.restassured;

public enum RestAssuredLogLevel {

    FULL("FULL"),
    NORMAL("NORMAL"),
    SHORT("SHORT"),
    NONE("NONE");

    final String value;

    RestAssuredLogLevel(String value) {
        this.value = value;
    }
}