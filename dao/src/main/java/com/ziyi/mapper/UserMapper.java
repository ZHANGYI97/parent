package com.ziyi.mapper;

import com.ziyi.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    User select(@Param("id") int id);

}
