package com.tinyFrame.helper;

import com.tinyFrame.annotation.RequestMapping;
import com.tinyFrame.bean.Handler;
import com.tinyFrame.bean.Request;
import com.tinyFrame.utils.ArrayUtil;
import com.tinyFrame.utils.CollectionUtil;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ControllerHelper {

    //请求request与处理请求handler映射关系
    private static final Map<Request, Handler> RequestMap = new HashMap<>();

    static {
        ArrayList<Class<?>> controllerClasses = ClassHelper.getControllerClasses();
        if (CollectionUtil.isNotEmpty(controllerClasses)) {
            initRequestMap(controllerClasses);
        }
    }

    private static void initRequestMap(ArrayList<Class<?>> controllerClasses) {
        /*
        找到controller类，定位到其中被RequestMapping注解的方法后获得具体的RequestMapping类，取得其中的请求方法和请求路径
        包装出一个Request实例和Handler实例，这就是这个controller类的method对应的request
         */
        for (Class<?> controllerClass : controllerClasses) {
            Method[] methods = controllerClass.getDeclaredMethods();
            if (ArrayUtil.isNotEmpty(methods)) {
                for (Method method : methods) {
                    //带有RequestMapping注解的方法
                    if (method.isAnnotationPresent(RequestMapping.class)) {
                        RequestMapping rm = method.getAnnotation(RequestMapping.class);// 获取requestMapping注解类
                        //请求路径与请求方法
                        Request request = new Request(rm.method(), rm.path());
                        //对应请求路径与请求方法的Controller和method
                        Handler handler = new Handler(controllerClass, method);
                        RequestMap.put(request, handler);
                    }
                }
            }
        }
    }
    /**
     * 获取handler
     *
     * @param requestMethod
     * @param requestPath
     * @return
     */
    public static Handler getHandler(String requestMethod, String requestPath) {
        Request request = new Request(requestMethod, requestPath);
        return RequestMap.get(request);
    }
}