package com.hellsinner.exam.dao;

import com.hellsinner.exam.model.dao.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Integer userid);

    User selectByEmail(@Param("email") String email);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userid);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<User> selectByIds(@Param("ids") List<Integer> ids);
}