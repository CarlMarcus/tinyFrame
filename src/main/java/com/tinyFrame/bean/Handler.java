package com.tinyFrame.bean;

import java.lang.reflect.Method;

/**
 * 处理Request请求对应Controller & method
 */
public class Handler {

    private Class<?> controllerClass;

    private Method method;

    public Handler(Class<?> controllerClass, Method method) {
        this.controllerClass = controllerClass;
        this.method = method;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public Method getMethod() {
        return method;
    }
}
