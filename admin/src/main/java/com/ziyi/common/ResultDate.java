package com.ziyi.common;

/**
 * auther:jurzis
 * date: 2021/2/24 10:50
 */
public class ResultDate<T> {

    private int code;

    private String msg;

    private Object data;

    public ResultDate(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static ResultDate success(int code, String msg, Object data) {
        return new ResultDate(code, msg, data);
    }
}
