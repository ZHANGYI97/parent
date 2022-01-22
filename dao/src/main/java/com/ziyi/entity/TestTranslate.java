package com.ziyi.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TestTranslate {

    private Integer id;

    private String name;

    private Integer age;

    private String address;

}
