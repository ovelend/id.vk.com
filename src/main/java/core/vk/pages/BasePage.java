package core.vk.pages;

import static core.vk.utils.ConfigProvider.APP_CONFIG;

public class BasePage {

    public static final String VK_COM_BASE_URL = APP_CONFIG.getString("vk.com.base.url");
    public static final String VK_ID_BASE_URL = APP_CONFIG.getString("vk.id.base.url");

    /** Here we can define common behavior for all other pages if it's required*/

}
