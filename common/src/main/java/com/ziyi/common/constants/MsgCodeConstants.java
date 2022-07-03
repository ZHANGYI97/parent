package com.ziyi.common.constants;

/**
 * @author zhy
 * @data 2022/7/3 13:30
 */
public interface MsgCodeConstants {

    // region 通用信息
    /**
     * 通用成功标识
     * replace
     */
    String RETURN_SUCCESS = "100001";

    /**
     * 系统错误（系统未知错误码）
     * replace
     */
    String SYSTEM_ERROR = "100002";

    // endregion

    //region 参数错误

    /**
     * 参数不能为空
     */
    String PARAM_CAN_NOT_BE_EMPTY = "100010";
    /**
     * 参数格式错误
     */
    String PARAM_FORMAT_ERROR = "100011";
    String ERROR_CODE_DATA_TYPE_NO_MATCH = "100012";

    /**
     * 参数校验不通过
     */
    String ERROR_CODE_PARAM_VALIDATION_FAIL = "100013";


    /**
     * 乐观锁错误
     */
    String OPTIMISTIC_LOCK_ERROR = "100004";

    /**
     * 文件上传失败
     * replace
     */
    String FILE_UPLOAD_ERROR = "600010";

    /**
     * 文件下载失败
     * replace
     */
    String FILE_DOWNLOAD_ERROR = "600011";

    // endregion

    // region 缓存相关
    /**
     * 缓存操作错误
     */
    String CACHE_OPERATE_ERROR = "100050";
    // endregion

    // region 分布式锁相关

    /**
     * 锁操作异常
     */
    String LOCK_OPERATE_ERROR = "100070";

    /**
     * 锁存在异常
     */
    String LOCK_EXISTS_ERROR = "100071";

    // endregion

    // region 工具实例缺失

    /**
     * 雪花生成器缺失
     */
    String SNOWFLAKE_GEN_IS_MISSING = "100101";

    /**
     * 序列号生成器缺失
     */
    String SERIAL_NO_GEN_IS_MISSING = "100102";

    /**
     * UUID生成器缺失
     */
    String UUID_GEN_IS_MISSING = "100103";

    // endregion

    // region 请求属性处理

    String CLASS_IS_NOT_A_REQUEST_ATTRIBUTE = "100104";

    String SERVER_REST_EXCEPTION = "100061";
    // endregion

}
