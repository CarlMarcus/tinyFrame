package com.tinyFrame.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropsUtil {
    /**
     * 加载配置文件
     *
     * @param fileName 配置文件名
     * @return
     */
    public static Properties loadProps(String fileName) {
        // Properties 继承于 Hashtable。是一个键值对象，表示一个持久的属性集.属性列表中每个键及其对应值都是一个字符串
        Properties props = null;
        InputStream is = null;
        try {
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            if (is == null) {
                throw new FileNotFoundException(fileName + " file is not found");
            }
            props = new Properties();
            props.load(is); // 把config.properties装入到property对象中
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
        return props;
    }
    /**
     * 获取字符型属性(默认值为空字符串)
     *
     * @param props
     * @param key
     * @return
     */
    public static String getString(Properties props, String key) {
        return getString(props, key, "");
    }
    /**
     * 获取字符型属性(可指定默认值)
     *
     * @param props
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getString(Properties props, String key, String defaultValue) {
        String value = defaultValue;
        if (props.containsKey(key)) {
            value = props.getProperty(key);
        }
        return value;
    }

}