package com.ziyi.common.base.exception;

import com.ziyi.common.base.AbstractZiException;
import com.ziyi.common.constants.MsgCodeConstants;
import com.ziyi.common.utils.JsonUtils;
import com.ziyi.common.utils.SpringMeaasgeContent;
import org.springframework.util.ObjectUtils;

/**
 * 运行时异常封装
 * @author zhy
 * @data 2022/7/3 13:22
 */
public class ZiRuntimeException extends AbstractZiException {

    public ZiRuntimeException(String errorCode, Object... args) {
        String errorMsg = SpringMeaasgeContent.getMessage(errorCode, args);
        this.setErrorCode(errorCode);
        this.setErrorMsg(ObjectUtils.isEmpty(errorMsg) ? JsonUtils.toJSONString(args) : errorMsg);
        this.setErrorDetail(this.getErrorMsg());
    }

    public ZiRuntimeException(String errorCode, Throwable cause, Object... args) {
        super(cause);
        String errorMsg = SpringMeaasgeContent.getMessage(errorCode, args);
        this.setErrorCode(errorCode);
        this.setErrorMsg(ObjectUtils.isEmpty(errorMsg) ? JsonUtils.toJSONString(args) : errorMsg);
        this.setErrorDetail(this.getErrorMsg());
    }

    /**
     * using in throw unknown exception
     * 封装未知异常
     *
     * @param cause 异常对象
     */
    public ZiRuntimeException(Throwable cause) {
        super(cause);
        this.setErrorCode(MsgCodeConstants.SYSTEM_ERROR);
        this.setErrorMsg(cause.getMessage());
        this.setErrorDetail(this.getErrorMsg());
    }
}
