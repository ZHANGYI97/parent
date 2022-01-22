package com.ziyi.mapper;

import com.ziyi.entity.TestTranslate;
import com.ziyi.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TestTranslateMapper {

    int insert(TestTranslate testTranslate);

    int inserts(TestTranslate testTranslate);

}
