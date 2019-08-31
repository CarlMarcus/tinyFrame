package com.tinyFrame.bean;

import com.tinyFrame.utils.CastUtil;
import com.tinyFrame.utils.CollectionUtil;

import java.util.Map;

/**
 * 请求参数对象
 */
// 需要一个类封装从HttpServletRequest请求对象中 获取的 所有参数，然后传递给 处理方法
public class Parameter {

    private Map<String,Object> formParams;

    public Parameter(Map<String,Object> formParams) {
        this.formParams = formParams;
    }

    /**
     * 判断参数是否为空
     *
     * @return boolean
     */
    public boolean isEmpty() {
        return CollectionUtil.isEmpty(formParams);
    }

    public Map<String, Object> getFormParams() {
        return formParams;
    }

    /**
     * 根据参数名取String型参数值
     *
     * @param name
     * @return
     */
    public String getString(String name) {
        return CastUtil.castString(formParams.get(name));
    }

    public double getDouble(String name) {
        return CastUtil.castDouble(formParams.get(name));
    }

    public long getLong(String name) {
        return CastUtil.castLong(formParams.get(name));
    }

    public int getInt(String name) {
        return CastUtil.castInt(formParams.get(name));
    }

    public Boolean getBoolean(String name) {
        return CastUtil.castBoolean(formParams.get(name));
    }

}