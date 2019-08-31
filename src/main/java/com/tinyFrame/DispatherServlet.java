package com.tinyFrame;

import com.tinyFrame.bean.Data;
import com.tinyFrame.bean.Handler;
import com.tinyFrame.bean.ModelAndView;
import com.tinyFrame.bean.Parameter;
import com.tinyFrame.utils.BeanFactory;
import com.tinyFrame.helper.ConfigHelper;
import com.tinyFrame.helper.ControllerHelper;
import com.tinyFrame.helper.HelperLoader;
import com.tinyFrame.helper.BeanContainer;
import com.tinyFrame.utils.JsonUtil;
import com.tinyFrame.utils.ParameterUtil;
import com.tinyFrame.utils.StringUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 请求转发器
 */
@WebServlet(urlPatterns = "/",loadOnStartup = 0)
public class DispatherServlet extends HttpServlet {
// 请求转发器是框架的核心，用来处理所有的请求
    @Override
    public void init(ServletConfig config) throws ServletException {
        HelperLoader.init();// 初始化框架&应用
        ServletContext sc = config.getServletContext();
        // ServletContext是一个全局的储存信息的空间，服务器开始就存在，服务器关闭才释放
        //注册JSP的Servlet
        ServletRegistration jspServlet = sc.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");
        //注册处理静态资源的Servlet
        ServletRegistration defaultServlet = sc.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");
    }

    // service()方法响应所有的请求
    @Override
    protected void service(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException {
        //获取请求方法 (get,post,put delete)
        String requestMethod = req.getMethod().toLowerCase();
        //请求路径url
        String url = req.getRequestURI();
        String contextPath = req.getContextPath();
        String requestPath = null;
        if (contextPath != null && contextPath.length() > 0) {
            requestPath = url.substring(contextPath.length());
        }
        //根据请求路径和请求方法 调用ControllerHelper.getHandler 获取处理这个请求的handler
        Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);
        // System.out.println(requestMethod + "  " + requestPath);
        if (handler != null) {
            Class<?> controllerClass = handler.getControllerClass(); // 通过handler获取Controller类
            Object controllerBean =    BeanContainer.getBean(controllerClass.getName()); //获取Controller类的实例对象
            //解析该请求的参数
            Parameter parameter = ParameterUtil.createParam(req);
            Object result;//该请求的返回对象
            Method method = handler.getMethod();//处理该请求的方法
            if (parameter.isEmpty()) {
                result = BeanFactory.invokeMethod(controllerBean, method); // 反射调用实例方法
            } else {
                result = BeanFactory.invokeMethod(controllerBean, method, parameter);
            } // 根据返回的结果是ModelAndView还是Data来处理返回问题
            if (result instanceof ModelAndView) {
                handleViewResult((ModelAndView) result, req, resp);
            } else {
                handleDataResult((Data) result, resp);
            }
        }
    }

    //返回为JSP页面
    private static void handleViewResult(ModelAndView view, HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        String path = view.getPath();
        if (StringUtil.isNotEmpty(path)) {
            if (path.startsWith("/")) {
                resp.sendRedirect(req.getContextPath() + path);
            } else {
                Map<String, Object> data = view.getData();
                for (Map.Entry<String, Object> entry : data.entrySet()) {
                    req.setAttribute(entry.getKey(), entry.getValue());
                }
                //forward将页面响应转发到ConfigHelper.getAppJspPath() + path
                req.getRequestDispatcher(ConfigHelper.getAppJspPath() + path).forward(req, resp);
            }
        }
    }

    //返回结果为Data，将返回结果POJO转换为JSON格式，写入HttpServletRespone对象中，输出到客户端浏览器
    private static void handleDataResult(Data data, HttpServletResponse resp)
            throws IOException {
        Object model = data.getData();
        if (model != null) {
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            PrintWriter writer = resp.getWriter();
            String json = JsonUtil.toJSON(model);
            writer.write(json);
            writer.flush();
            writer.close();
        }
    }

}