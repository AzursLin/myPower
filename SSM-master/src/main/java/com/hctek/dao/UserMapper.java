package com.hctek.dao;

import com.hctek.model.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(String userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<User> getAll(User record);

    @Select("select * from user where user_name = #{userName}")
    @ResultMap("BaseResultMap")
    User getOneByName(String userName);

    @Delete("DELETE FROM user WHERE user_id = #{userId}")
    int deleteByuserId(String userId);
}