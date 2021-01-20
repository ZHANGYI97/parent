package com.ziyi.base;

public class BaseException extends Exception {

    protected String code;

    protected String reason;

    public BaseException(String code, String reason, Throwable cause) {
        super(code + "-" + reason, cause);
        this.code = code;
        this.reason = reason;
    }

    public BaseException(String code, String reason) {
        super(code + "-" + reason);
        this.code = code;
        this.reason = reason;
    }

}
