package core.vk.pages;

import static core.vk.utils.ConfigProvider.APP_CONFIG;

/**
 * Here we can define common behavior for all other pages if it's required
 */

public class BasePage {

    public static final String VK_COM_BASE_URL = APP_CONFIG.getString("ui_urls.vk.com.base.url");
    public static final String VK_ID_BASE_URL = APP_CONFIG.getString("ui_urls.vk.id.base.url");
    
}
