package core.vk.utils;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * Here we can define all the necessary variables for our
 * test framework, for example clusters, namespaces, any env
 * variables and so on
 */

public class ConfigProvider {

    /**
     * Retrieve the values from reference.conf
     * if it's present otherwise use system environment variables:
     * VK_LOGIN_ENV_VARIABLE and VK_PASS_ENV_VARIABLE
     */
    public static final Config APP_CONFIG = ConfigFactory.load();

}