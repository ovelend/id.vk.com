package core.vk.base;

import core.vk.restassured.RestAssuredLogLevel;
import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.testng.annotations.BeforeSuite;

import static core.vk.utils.ConfigProvider.APP_CONFIG;

/**
 * Before the tests start executing we can set any configurations for rest assured like
 * logging, filtering, timeouts and so on
 */

public class BaseApiTest {

    private static final RestAssuredLogLevel REST_ASSURED_LOG_LEVEL =
            RestAssuredLogLevel.valueOf(APP_CONFIG.getString("REST_ASSURED_LOG_LEVEL"));
    private static LogDetail requestLogDetail;
    private static LogDetail responseLogDetail;

    @BeforeSuite
    public void initRestAssuredSettings() {
        if (REST_ASSURED_LOG_LEVEL != RestAssuredLogLevel.NONE) {
            setLogFilterParams();
            RestAssured.filters(
                    new RequestLoggingFilter(requestLogDetail),
                    new ResponseLoggingFilter(responseLogDetail)
            );
        }
    }

    private static void setLogFilterParams() {

        switch (REST_ASSURED_LOG_LEVEL) {
            case FULL -> {
                requestLogDetail = LogDetail.ALL;
                responseLogDetail = LogDetail.ALL;
            }
            case NORMAL -> {
                requestLogDetail = LogDetail.PARAMS;
                responseLogDetail = LogDetail.HEADERS;
            }
            case SHORT -> {
                requestLogDetail = LogDetail.URI;
                responseLogDetail = LogDetail.STATUS;
            }
            case NONE -> {
                requestLogDetail = null;
                responseLogDetail = null;
            }
            default -> throw new RuntimeException(String.format("Unknown logging level %s in config", REST_ASSURED_LOG_LEVEL));
        }
    }
}
