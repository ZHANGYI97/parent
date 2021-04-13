package com.ziyi.common;

/**
 * auther:jurzis
 * date: 2021/2/24 10:50
 */
public class Result {

    private int code;

    private String msg;

    private Object data;

    public Result(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
