package com.ziyi.common.uuid;

import cn.hutool.core.util.IdUtil;

/**
 * uuid生成器
 *
 * @author zhy
 */
public class UuidGen {
    /**
     * 生成唯一标示uuid 没有中划线
     *
     * @return uuid
     */
    public String uuid() {
        return IdUtil.fastSimpleUUID();
    }

    /**
     * 生成唯一标示uuid 带中划线
     *
     * @return uuid
     */
    public String uuidWithLine() {
        return IdUtil.fastUUID();
    }


}
