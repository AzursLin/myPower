package com.hctek.dao;

import com.hctek.model.UserDorm;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserDormMapper {
    int insert(UserDorm record);

    int insertSelective(UserDorm record);

    List<UserDorm> getAll(UserDorm userDorm);

    int updateById(UserDorm record);

    @Delete("DELETE FROM user_dorm WHERE id = #{id}")
    int deleteById(int id);
}