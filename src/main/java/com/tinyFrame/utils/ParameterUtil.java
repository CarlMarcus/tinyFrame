package com.tinyFrame.utils;

import com.tinyFrame.bean.Parameter;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 解析请求参数,form表单数据
 */
// 解析请求参数，存为HashMap，封装到 Parameter类中，传递给Controller的方法处理。
public class ParameterUtil {

    public static Parameter createParam(HttpServletRequest request) throws IOException {
        return new Parameter(parseParameterNames(request));
    }

    private static Map<String,Object> parseParameterNames(HttpServletRequest request) {
        Map<String,Object> formParams = new HashMap<>();
        //获取 发送请求页面中form表单 所具有的属性名称
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String fieldName = paramNames.nextElement();
            String[] fieldValues = request.getParameterValues(fieldName); // 通过属性名获取其值
            if (ArrayUtil.isNotEmpty(fieldValues)) {
                Object fieldValue;
                if (fieldValues.length == 1) {
                    fieldValue = fieldValues[0];
                } else {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < fieldValues.length; ++i) {
                        sb.append(fieldValues[i]);
                        if (i != fieldValues.length - 1) {
                            sb.append(StringUtil.separator);
                        }
                    }
                    fieldValue = sb.toString();
                }
                formParams.put(fieldName,fieldValue);
            }
        }
        return formParams;
    }

}