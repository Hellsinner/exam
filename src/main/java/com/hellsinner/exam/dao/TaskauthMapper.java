package com.hellsinner.exam.dao;

import com.hellsinner.exam.model.dao.Taskauth;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskauthMapper {
    int deleteByPrimaryKey(Integer taskauthid);

    int insert(Taskauth record);

    int insertSelective(Taskauth record);

    Taskauth selectByPrimaryKey(Integer taskauthid);

    int updateByPrimaryKeySelective(Taskauth record);

    int updateByPrimaryKey(Taskauth record);

    Taskauth selectByTUid(@Param("tid") Integer tid,@Param("uid") Integer userid);

    List<Integer> selectByUid(@Param("uid") Integer uid);
}