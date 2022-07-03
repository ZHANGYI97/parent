package com.ziyi.common.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static lombok.AccessLevel.PROTECTED;

/**
 * standard custom exception
 * every exception have a errorCode and a errorMsg
 * <p>
 * 标准自定义对象
 * 异常包含 错误码 和 错误信息
 *
 * @author zhy
 * @date 2022/7/3  12:25
 */
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractZiException extends RuntimeException {

    /**
     * the code used to identify the error
     * 错误码
     * Set 方法作为保护方法
     */
    @Getter
    @Setter(PROTECTED)
    private String errorCode;

    /**
     * the message used to describe the error
     * the errormessage must be clearly , friendly
     * 错误信息，错误信息需要考虑提示对象，必须清晰友好
     * Set 方法作为保护方法
     */
    @Getter
    @Setter(PROTECTED)
    private String errorMsg;

    /**
     * error detail msg ,using debug
     * 错误详情,用于错误排查，不用于提示
     */
    @Getter
    @Setter(PROTECTED)
    private String errorDetail;

    /**
     * set throwable if exist
     *
     * @param throwable throwable
     */
    public AbstractZiException(Throwable throwable) {
        super(throwable);
    }

    @Override
    public String getMessage() {
        return this.errorMsg;
    }
}
