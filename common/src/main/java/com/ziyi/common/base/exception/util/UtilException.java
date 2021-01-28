package com.ziyi.common.base.exception.util;


import com.ziyi.common.utils.MessageUtils;
import org.springframework.util.StringUtils;

/**
 * 工具类异常模板
 *
 * @author zhy
 */
public class UtilException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    /**
     * 所属模块
     */
    private String module;

    /**
     * 错误码
     */
    private String code;

    /**
     * 错误码对应的参数
     */
    private Object[] args;

    /**
     * 错误消息
     */
    private String defaultMessage;

    public UtilException(String module, String code, Object[] args, String defaultMessage)
    {
        this.module = module;
        this.code = code;
        this.args = args;
        this.defaultMessage = defaultMessage;
    }

    public UtilException(String module, String code, Object[] args)
    {
        this(module, code, args, null);
    }

    public UtilException(String module, String defaultMessage)
    {
        this(module, null, null, defaultMessage);
    }

    public UtilException(String code, Object[] args)
    {
        this(null, code, args, null);
    }

    public UtilException(String defaultMessage)
    {
        this(null, null, null, defaultMessage);
    }

    @Override
    public String getMessage()
    {
        String message = null;
        if (!StringUtils.isEmpty(code))
        {
            message = MessageUtils.message(code, args);
        }
        if (message == null)
        {
            message = defaultMessage;
        }
        return message;
    }

    public String getModule()
    {
        return module;
    }

    public String getCode()
    {
        return code;
    }

    public Object[] getArgs()
    {
        return args;
    }

    public String getDefaultMessage()
    {
        return defaultMessage;
    }
}

