package com.ziyi.mapper;

import com.ziyi.entity.Users;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UsersMapper {

    Users selectOne();

    Users select(@Param("name") String name, @Param("salt") String salt);

}
