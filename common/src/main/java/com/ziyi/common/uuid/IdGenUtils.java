package com.ziyi.common.uuid;

import com.ziyi.common.base.exception.ZiRuntimeException;
import com.ziyi.common.constants.MsgCodeConstants;

import java.util.Objects;

/**
 * ID生成工具类集合
 *
 * @author zhy
 * @date 2022/7/3
 */
public final class IdGenUtils {

    private IdGenUtils() {
    }

    private static SerialNoGen serialNoGen;
    private static SnowflakeGen snowflakeGen;
    private static UuidGen uuidGen;

    public static void setSerialNoGen(SerialNoGen serialNoGen) {
        IdGenUtils.serialNoGen = serialNoGen;
    }

    public static void setSnowflakeGen(SnowflakeGen snowflakeGen) {
        IdGenUtils.snowflakeGen = snowflakeGen;
    }

    public static void setUuidGen(UuidGen uuidGen) {
        IdGenUtils.uuidGen = uuidGen;
    }

    /**
     * 雪花生成工具
     *
     * @return 雪花生成工具
     */
    public static SnowflakeGen snowflakeGen() {
        if (Objects.isNull(IdGenUtils.snowflakeGen)) {
            throw new ZiRuntimeException(MsgCodeConstants.SNOWFLAKE_GEN_IS_MISSING);
        }
        return IdGenUtils.snowflakeGen;
    }

    /**
     * 序列号生成工具
     *
     * @return 序列号生成工具
     */
    public static SerialNoGen serialNoGen() {
        if (Objects.isNull(IdGenUtils.serialNoGen)) {
            throw new ZiRuntimeException(MsgCodeConstants.SERIAL_NO_GEN_IS_MISSING);
        }
        return IdGenUtils.serialNoGen;
    }

    /**
     * uuid 默认去掉横线
     *
     * @return uuid
     */
    public static UuidGen uuidGen() {
        if (Objects.isNull(IdGenUtils.uuidGen)) {
            throw new ZiRuntimeException(MsgCodeConstants.UUID_GEN_IS_MISSING);
        }
        return IdGenUtils.uuidGen;
    }
}
