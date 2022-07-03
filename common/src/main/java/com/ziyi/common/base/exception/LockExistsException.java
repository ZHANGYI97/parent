package com.ziyi.common.base.exception;

import com.ziyi.common.constants.MsgCodeConstants;

/**
 * @author zhy
 * @date 2022/7/3
 */
public class LockExistsException extends ZiBusinessException {
    /**
     * 锁存在异常
     *
     * @param args 参数
     */
    public LockExistsException(Object... args) {
        super(MsgCodeConstants.LOCK_EXISTS_ERROR, args);
    }
}
