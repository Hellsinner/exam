package com.hellsinner.exam.dao;

import com.hellsinner.exam.model.dao.Taskconstruct;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TaskconstructMapper {
    int deleteByPrimaryKey(Integer strategyid);

    int insert(Taskconstruct record);

    int insertSelective(Taskconstruct record);

    Taskconstruct selectByPrimaryKey(Integer strategyid);

    int updateByPrimaryKeySelective(Taskconstruct record);

    int updateByPrimaryKey(Taskconstruct record);

    List<Taskconstruct> selectByTid(@Param("tid") Integer tid);
}