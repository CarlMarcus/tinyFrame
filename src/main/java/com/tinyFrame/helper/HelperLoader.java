package com.tinyFrame.helper;

import com.tinyFrame.utils.ClassUtil;
/**
 * 初始化框架
 */
public class HelperLoader {

    public static void init() {
        Class<?>[] cs = {ClassHelper.class, BeanContainer.class, IocHelper.class, ControllerHelper.class};
        for (Class<?> c: cs) {
            ClassUtil.loadClass(c.getName(),true);
        }
    }
}