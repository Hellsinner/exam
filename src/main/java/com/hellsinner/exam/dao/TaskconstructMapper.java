package com.hellsinner.exam.dao;

import com.hellsinner.exam.model.dao.Taskconstruct;

public interface TaskconstructMapper {
    int deleteByPrimaryKey(Integer strategyid);

    int insert(Taskconstruct record);

    int insertSelective(Taskconstruct record);

    Taskconstruct selectByPrimaryKey(Integer strategyid);

    int updateByPrimaryKeySelective(Taskconstruct record);

    int updateByPrimaryKey(Taskconstruct record);
}