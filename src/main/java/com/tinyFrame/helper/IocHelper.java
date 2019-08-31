package com.tinyFrame.helper;

import com.tinyFrame.annotation.Autowired;
import com.tinyFrame.utils.ArrayUtil;
import com.tinyFrame.utils.BeanFactory;
import com.tinyFrame.utils.CollectionUtil;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 依赖注入
 */
public final class IocHelper {

    static {
        Map<String, Object> beanContainer = BeanContainer.getBeanContainer();
        if (CollectionUtil.isNotEmpty(beanContainer)) {
            initIOC(beanContainer);
        } else {
            throw new RuntimeException("Empty bean container, check your configuration please.");
        }
    }

    private static void initIOC( Map<String, Object> beanContainer) {
        for (Map.Entry<String, Object> beanEntry : beanContainer.entrySet()) {
            String className = beanEntry.getKey();
            Object beanInstance = beanEntry.getValue();
            Class<?> beanClass = null;
            try {
                beanClass = Class.forName(className); // 根据bean类全限定名获取class类型
                System.out.println(className);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            //Controller类中定义的属性
            Field[] beanFields = beanClass.getDeclaredFields();
            if (ArrayUtil.isNotEmpty(beanFields)) {
                for (Field beanField : beanFields) {
                    // 对于被@Autowired注解的成员属性，它将被依赖注入
                    if (beanField.isAnnotationPresent(Autowired.class)) {
                        //成员属性的类型
                        Class<?> beanFieldClass = beanField.getType();
                        //根据成员属性的类型从bean容器获取实例
                        Object beanFieldInstance = beanContainer.get(beanFieldClass.getName());
                        if (beanFieldInstance != null) {
                            //依赖注入
                            BeanFactory.injectField(beanInstance, beanField, beanFieldInstance);
                        }
                    }
                }
            } else {
                throw new RuntimeException("No field @Autowired, check your configuration please.");
            }
        }
    }
}