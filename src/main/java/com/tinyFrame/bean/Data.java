package com.tinyFrame.bean;

/**
 * 返回数据
 */
public class Data<T> {


    private T data;

    public Data(T datas) {
        this.data = datas;
    }

    public T getData() {
        return data;
    }
}