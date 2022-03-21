package core.vk.base;

import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.testng.annotations.BeforeSuite;

public class BaseApiTest {

    /**
     * Before the tests start executing we can set any configurations for rest assured like
     * logging, filtering, timeouts and so on
     */
    @BeforeSuite
    public void initRestAssuredSettings() {
        RestAssured.filters(new ResponseLoggingFilter(LogDetail.ALL));
    }

}
