package com.ziyi.config.web.handler;

import cn.hutool.core.util.ArrayUtil;
import com.ziyi.common.constants.Constants;
import com.ziyi.common.constants.MsgCodeConstants;
import com.ziyi.common.model.ResultData;
import com.ziyi.common.utils.ArraysUtils;
import com.ziyi.common.utils.SpringMeaasgeContent;
import lombok.experimental.UtilityClass;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.Optional;
import java.util.stream.Collectors;

/**
 * util
 * @author zhy
 * @date 2022/7/3
 */
@UtilityClass
public class HandlerUtil {

    public ResultData<Void> getExceptionResultData(String errorCode, String message) {
        ResultData<Void> result = new ResultData<>();
        result.setSucceed(Boolean.FALSE);
        result.setErrorCode(errorCode);
        result.setErrorMsg(message);
        return result;
    }

    public ResultData<Void> getBindExceptionResultData(BindException e) {
        ResultData<Void> result = new ResultData<>();
        result.setSucceed(Boolean.FALSE);
        result.setErrorCode(MsgCodeConstants.ERROR_CODE_PARAM_VALIDATION_FAIL);
        String errorMsg = getErrorMessage(e.getBindingResult());
        if (ObjectUtils.isEmpty(errorMsg)) {
            errorMsg = e.getMessage();
        }
        errorMsg = SpringMeaasgeContent.getMessage(MsgCodeConstants.ERROR_CODE_PARAM_VALIDATION_FAIL, errorMsg, "");
        result.setErrorMsg(errorMsg);
        return result;
    }

    public String getErrorMessage(BindingResult bindingResult) {
        return bindingResult.getAllErrors().stream()
                .map(err -> {
                    if (ArrayUtil.isNotEmpty(err.getArguments())) {
                        Object[] arguments = err.getArguments();
                        for (int i = 0; i < arguments.length; i++) {
                            // 字段错误时 合并错误码
                            // Spring 抛出的错误没有把字段值作为变量传进去 手动加
                            setMessageValueArgs(bindingResult, err, arguments, i, arguments[i]);
                        }
                    }
                    return Optional.ofNullable(SpringMeaasgeContent.getMessage(err.getDefaultMessage(), err.getArguments()))
                            .filter(StringUtils::hasText).orElse(err.getDefaultMessage());
                }).distinct().collect(Collectors.joining(Constants.COMMA));
    }

    private void setMessageValueArgs(BindingResult bindingResult, ObjectError err, Object[] args, int i, Object arg) {
        if (arg instanceof DefaultMessageSourceResolvable && err instanceof FieldError) {
            DefaultMessageSourceResolvable resolvable = (DefaultMessageSourceResolvable) arg;
            if (ArraysUtils.isNullOrEmpty(resolvable.getArguments()) && !ArraysUtils.isNullOrEmpty(resolvable.getCodes())
                    // FieldError时 defaultMessage 为字段名
                    && StringUtils.hasText(resolvable.getDefaultMessage())) {
                Optional.ofNullable(resolvable.getDefaultMessage()).filter(StringUtils::hasText).ifPresent(field -> {
                    Object val = Optional.ofNullable(bindingResult.getRawFieldValue(field)).orElse("");
                    String[] fieldErrCodes = resolvable.getCodes();
                    String[] bindErrCodes = err.getCodes();
                    if (!ArraysUtils.isNullOrEmpty(fieldErrCodes) && !ArraysUtils.isNullOrEmpty(bindErrCodes)
                            && fieldErrCodes.length >= 2 && bindErrCodes.length >= 2) {
                        String[] codes = {bindErrCodes[0], fieldErrCodes[0],
                                bindErrCodes[1], fieldErrCodes[1]};
                        args[i] = new DefaultMessageSourceResolvable(codes,
                                new Object[]{val}, resolvable.getDefaultMessage());
                    }
                });
            }
        }
    }
}
