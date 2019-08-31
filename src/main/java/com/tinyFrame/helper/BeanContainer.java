package com.tinyFrame.helper;

import com.tinyFrame.utils.BeanFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* 调用BeanFactory.newInstance(Class<?> clazz)方法来实例化类的对象，缓存在Map beanContainer中，需要随时获取
 * Map说明：使用类全名称作为key,类的实例对象作为value值
 */
public class BeanContainer {
    /**
     * 存放 Bean类名 和 Bean实例 的映射关系
     */
    private static final Map<String, Object> beanContainer = new HashMap<String, Object>();

    static {
        // 获取所有的Bean类（被@Controller和@Service注解）
        ArrayList<Class<?>> beanClasses = ClassHelper.getBeanClasses();
        for (Class<?> beanClass : beanClasses) {
            Object obj = BeanFactory.newInstance(beanClass);// 调用Bean工厂方法newInstance()方法来实例化Bean类
            beanContainer.put(beanClass.getName(), obj);
        }
    }

    /**
     * 获取Bean映射
     *
     * @return
     */
    public static Map<String, Object> getBeanContainer() {
        return beanContainer;
    }

    /**
     * 获取Bean实例
     */
    public static <T> T getBean(String className) {
        if (!beanContainer.containsKey(className)) {
            throw new RuntimeException("can not get bean by className: " + className);
        }
        return (T) beanContainer.get(className);
    }

    /**
     * 设置Bean实例
     */
    public static void setBean(String className, Object obj) {
        beanContainer.put(className, obj);
    }
}
