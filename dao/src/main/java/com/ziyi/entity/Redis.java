package com.ziyi.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Redis {

    private String key;

    private String value;

    private long time;
}
