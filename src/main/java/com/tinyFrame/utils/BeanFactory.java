package com.tinyFrame.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/* Bean工厂
 * 通过加载配置文件获取应用基础包名，加载基础包名下所有的类，获取Controller,Service类。
 * 但是我们只是加载了类，但是无法通过获取的类来实例化对象。因此需要一个反射工具，来实例化类
 */
public class BeanFactory {

    /**
     * 创建实例
     *
     * @param clazz
     * @return
     */
    public static Object newInstance(Class<?> clazz) { // newInstance()方法，实例化目标类
        Object instance;
        try {
            instance = clazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return instance;
    }

    /**
     * 通过反射实现方法调用
     *
     * @param obj
     * @param method
     * @param args
     * @return
     */
    public static Object invokeMethod(Object obj, Method method, Object... args) { // 通过反射机制来调用类中的方法
        Object result;
        try {
            method.setAccessible(true);
            result = method.invoke(obj, args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 通过反射设置成员变量值
     *
     * @param obj
     * @param field
     * @param value
     */
    public static void injectField(Object obj, Field field, Object value) { // 通过反射机制为类成员赋值
        try {
            field.setAccessible(true);
            field.set(obj, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}