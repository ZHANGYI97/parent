package com.ziyi.common.base.exception.file;

import com.ziyi.common.base.exception.util.UtilException;

/**
 * 文件信息异常类
 * 
 * @author zhy
 */
public class FileException extends UtilException
{
    private static final long serialVersionUID = 1L;

    public FileException(String code, Object[] args)
    {
        super("file", code, args, null);
    }

}
