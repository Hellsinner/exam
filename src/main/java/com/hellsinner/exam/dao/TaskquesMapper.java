package com.hellsinner.exam.dao;

import com.hellsinner.exam.model.dao.Taskques;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TaskquesMapper {
    int deleteByPrimaryKey(Integer taskquesid);

    int insert(Taskques record);

    int insertSelective(Taskques record);

    Taskques selectByPrimaryKey(Integer taskquesid);

    int updateByPrimaryKeySelective(Taskques record);

    int updateByPrimaryKey(Taskques record);

    @MapKey("quesid")
    Map<String,Taskques> selectByTid(@Param("tid") Integer tid);

    void insertMany(@Param("quess") List<Taskques> taskques);
}