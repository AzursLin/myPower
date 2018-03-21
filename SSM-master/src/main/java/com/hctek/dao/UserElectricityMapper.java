package com.hctek.dao;

import com.hctek.model.UserElectricity;
import org.apache.ibatis.annotations.Delete;

import java.util.List;

public interface UserElectricityMapper {
    int deleteByPrimaryKey(String userId);

    int insert(UserElectricity record);

    int insertSelective(UserElectricity record);

    UserElectricity selectByPrimaryKey(String userId);

    int updateByPrimaryKeySelective(UserElectricity record);

    int updateByPrimaryKey(UserElectricity record);

    List<UserElectricity> getAll(UserElectricity userElectricity);

    @Delete("DELETE FROM user_electricity WHERE id = #{id}")
    int deleteById(int id);
}