package com.ziyi.common.base.exception;

import lombok.Getter;

/**
 * customized business exception
 * use scope :
 * 1. use to throw some scenes what logically is allowed to exist
 * 用于出现业务逻辑异常时场景，如业务输入参数不满足规则
 *
 * @author zhy
 * @date 2022/7/3
 */
@Getter
public class ZiBusinessException extends ZiRuntimeException {

    public ZiBusinessException(String errorCode, Object... args) {
        super(errorCode, args);
    }

    public ZiBusinessException(String errorCode, Throwable cause, Object... args) {
        super(errorCode, cause, args);
    }
}
