package com.tinyFrame.helper;

import com.tinyFrame.Constant;
import com.tinyFrame.utils.PropsUtil;

import java.util.Properties;

// 加载了配置文件，需要此类来获取配置文件属性
public class ConfigHelper {
    private static final Properties CONFIG_PROPS = PropsUtil.loadProps("src/main/resources/config.properties");
    //默认配置文件为config.properties
    /**
     * 获取应用基础包名
     * @return
     */
    public static String getAppBasePackage() {
        return PropsUtil.getString(CONFIG_PROPS, Constant.APP_BASE_PACKAGE);
    }
    /**
     * 获取JSP路径
     * @return
     */
    // 在ConfigHelper类里，实现获取JSP路径的功能
    public static String getAppJspPath() {
        return PropsUtil.getString(CONFIG_PROPS, Constant.APP_JSP_PATH, "/WEB-INF/jsp/");
    }
    /**
     * 获取应用静态资源路径
     * @return
     */
    public static String getAppAssetPath() {
        
        return PropsUtil.getString(CONFIG_PROPS, Constant.APP_ASSET_PATH, "/asset/");
    }
}
