package com.itheima.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @param
 * @author JellyfishChips
 * @version 1.0
 * @className Result
 * @description  封装返回结果集
 * @return
 * @date 2019/6/22 20:05
 */
public class Result implements Serializable {

    private boolean flag;//执行结果，true为执行成功 false为执行失败
    private String message;//返回提示信息，主要用于页面提示信息
    private Object data;//返回数据

    public Result() {
    }

    public Result(boolean flag, String message) {
        super();
        this.flag = flag;
        this.message = message;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Result(boolean flag, String message, Object data) {
        this.data = data;
        this.flag = flag;
        this.message = message;
    }
}
