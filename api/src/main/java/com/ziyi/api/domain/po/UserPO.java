package com.ziyi.api.domain.po;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author zhy
 * @data 2022/6/28 21:47
 */
@Data
@Document
public class UserPO {

    private String id;

    private String name;

    private String age;

    private String address;

    private String phone;

}
