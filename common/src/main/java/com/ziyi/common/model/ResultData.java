package com.ziyi.common.model;

import com.ziyi.common.base.exception.ZiRuntimeException;
import com.ziyi.common.constants.MsgCodeConstants;
import com.ziyi.common.utils.SpringMeaasgeContent;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 * @author zhy
 * @data 2022/7/3 13:22
 */
@Data
@NoArgsConstructor
@ApiModel(description = "标准返回结果")
@SuppressWarnings("unused")
public class ResultData<T> implements Serializable {
    private static final long serialVersionUID = 8450400755456555766L;

    /**
     * 结果: 成功或失败(true,false)
     */
    @ApiModelProperty(value = "成功标志", allowableValues = "true,false")
    private boolean succeed = true;

    /**
     * 错误码
     */
    @ApiModelProperty(value = "错误码")
    private String errorCode = "";

    /**
     * 错误信息
     */
    @ApiModelProperty("错误信息")
    private String errorMsg = "";

    /**
     * 业务数据实体
     */
    @ApiModelProperty("业务数据实体")
    private T data = null;

    /**
     * 返回前端生成的防止重复提交唯一值
     */
    @ApiModelProperty("前端生成的防止重复提交唯一值")
    private String traceId;

    private ResultData(boolean succeed, String errorCode, String errorMsg, T data) {
        this.succeed = succeed;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.data = data;
        this.traceId = MDC.get("traceId");
    }

    /**
     * 构造成功返回对象
     *
     * @param data 返回数据
     * @param <T>  数据类型
     * @return resultData
     */
    public static <T> ResultData<T> success(T data) {
        return new ResultData<>(true, MsgCodeConstants.RETURN_SUCCESS, "", data);
    }

    /**
     * 构造失败返回对象
     *
     * @param errorCode 错误码
     * @param errorMsg  错误消息
     * @param <T>       数据类型
     * @return resultData
     */
    public static <T> ResultData<T> failed(String errorCode, String errorMsg) {
        return new ResultData<>(false, errorCode, errorMsg, null);
    }

    public static <T> ResultData<T> failure(String errorCode, String errorMsg) {
        return new ResultData<>(false, errorCode, errorMsg, null);
    }

    public ResultData(boolean succeed, T data) {
        this(succeed, null, null, data);
    }

    public ResultData(boolean succeed, String errorCode, String errorMsg) {
        this(succeed, errorCode, errorMsg, null);
    }

    public ResultData(String errorCode, boolean succeed, Object... args) {
        this(succeed, errorCode, null, null, args);
    }

    public ResultData(boolean succeed, String errorCode, String errorMsg, T data, Object... args) {
        this.succeed = succeed;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.data = data;

        if (!succeed && StringUtils.hasText(errorCode) && !StringUtils.hasText(errorMsg)) {
            this.errorMsg = SpringMeaasgeContent.getMessage(errorCode, args);
        }

        if (!StringUtils.hasText(this.errorCode)) {
            this.errorCode = "";
        }
        if (!StringUtils.hasText(this.errorMsg)) {
            this.errorMsg = "";
        }
        this.traceId = MDC.get("traceId");
    }

    public static <T> ResultData<T> succeed() {
        return succeed(null);
    }

    public static <T> ResultData<T> succeed(T data) {
        return new ResultData<>(true, null, null, data);
    }

    /**
     * 通用成功标识
     */
    public static <T> ResultData<T> codeSucceed() {
        return codeSucceed(null);
    }

    /**
     * 通用成功标识
     */
    public static <T> ResultData<T> codeSucceed(T data) {
        return new ResultData<>(true, MsgCodeConstants.RETURN_SUCCESS, null, data);
    }

    public static <T> ResultData<T> failure(String errorCode, Object... args) {
        return new ResultData<>(false, errorCode, null, null, args);
    }

    public static <T> ResultData<T> failure(ZiRuntimeException e) {
        return failure(e.getErrorCode(), e.getErrorMsg());
    }

    public static <T> ResultData<T> failureForMsg(String errorCode, String errorMsg) {
        return new ResultData<>(false, errorCode, errorMsg, null);
    }

    public static <T> ResultData<T> failureForMsg(ZiRuntimeException e) {
        return failureForMsg(e.getErrorCode(), e.getMessage());
    }

}
