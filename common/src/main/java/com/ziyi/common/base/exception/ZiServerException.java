package com.ziyi.common.base.exception;

import com.ziyi.common.base.AbstractZiException;

/**
 * 远程服务 异常
 * 使用范围：
 * 调用远程服务时，服务直接返回错误码和错误信息
 *
 * @author zhy
 * @date 2022/7/3
 */
public class ZiServerException extends AbstractZiException {

    public ZiServerException(String errorCode, String message) {
        super(errorCode, message, message);
    }
}
