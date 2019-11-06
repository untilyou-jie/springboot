package com.jie.maven.firstspringboot.mapper;

import com.jie.maven.firstspringboot.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {
    @Insert("insert into USER(name,account_id,token,gmt_create,gmt_modified,avatar_url) values(#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    void insert(User user);
    @Select("select * from user where token=#{token}")
    //不是对象的时候要加@param
    User findByToken(@Param("token") String token);
    @Select("select * from user where id=#{creator}")
    User findById(@Param("creator") Integer creator);
}
