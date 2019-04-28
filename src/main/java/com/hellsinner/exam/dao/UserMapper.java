package com.hellsinner.exam.dao;

import com.hellsinner.exam.model.dao.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserMapper {
    int deleteByPrimaryKey(Integer userid);

    User selectByEmail(@Param("email") String email);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userid);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<User> selectByIds(@Param("ids") List<Integer> ids);

    List<User> selectAuthByIds(@Param("ids") List<Integer> ids);
}