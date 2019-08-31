package com.tinyFrame.bean;

import java.util.HashMap;
import java.util.Map;

public class ModelAndView {

    //返回JSP路径
    private String path;
    //模型数据
    private Map<String,Object> data;

    public ModelAndView(String path) {
        this.path = path;
        data = new HashMap<>();
    }

    public ModelAndView addData(String key, Object obj) {
        data.put(key,obj);
        return this;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}