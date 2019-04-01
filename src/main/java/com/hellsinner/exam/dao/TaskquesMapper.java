package com.hellsinner.exam.dao;

import com.hellsinner.exam.model.dao.Taskques;

public interface TaskquesMapper {
    int deleteByPrimaryKey(Integer taskquesid);

    int insert(Taskques record);

    int insertSelective(Taskques record);

    Taskques selectByPrimaryKey(Integer taskquesid);

    int updateByPrimaryKeySelective(Taskques record);

    int updateByPrimaryKey(Taskques record);
}