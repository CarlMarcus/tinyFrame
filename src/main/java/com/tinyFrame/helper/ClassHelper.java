package com.tinyFrame.helper;

import com.tinyFrame.annotation.Service;
import com.tinyFrame.annotation.Controller;
import com.tinyFrame.utils.ClassUtil;

import java.util.ArrayList;

/* Controller,Service类是框架需要管理的类，把他们统称为Bean类。此类
 * 可以获取应用所有的Controller,Service类
 */
public class ClassHelper {
    //存放 基础包名下所有的类
    private static final ArrayList<Class<?>> classes;

    static {
        String basePackage = ConfigHelper.getAppBasePackage(); // 获取基础包名
        classes = ClassUtil.getClasses(basePackage); // 加载该基础包名下所有的类
    }
    /**
     * 获取基础包名下所有的类
     *
     * @return
     */
    public static ArrayList<Class<?>> getClasses() {
        return classes;
    }

    /**
     * 获取所有Service类
     *
     * @return
     */
    public static ArrayList<Class<?>> getServiceClasses() {
        ArrayList<Class<?>> sc = new ArrayList<Class<?>>();
        for (Class<?> c : classes) {
            if (c.isAnnotationPresent(Service.class)) {
                sc.add(c);
            }
        }
        return sc;
    }

    /**
     * 获取所有Controller类
     *
     * @return
     */
    public static ArrayList<Class<?>> getControllerClasses() {
        ArrayList<Class<?>> cc = new ArrayList<Class<?>>();
        for (Class<?> c : classes) {
            if (c.isAnnotationPresent(Controller.class)) {
                cc.add(c);
            }
        }
        return cc;
    }

    /**
     * 获取 Bean容器主要管理的 Service,Controller类
     *
     * @return
     */
    public static ArrayList<Class<?>> getBeanClasses() {
        ArrayList<Class<?>> bc = new ArrayList<Class<?>>();
        bc.addAll(getServiceClasses());
        bc.addAll(getControllerClasses());
        return bc;
    }

}
