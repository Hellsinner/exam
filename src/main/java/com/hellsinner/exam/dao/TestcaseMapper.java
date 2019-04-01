package com.hellsinner.exam.dao;

import com.hellsinner.exam.model.dao.Testcase;

public interface TestcaseMapper {
    int deleteByPrimaryKey(Integer testcaseid);

    int insert(Testcase record);

    int insertSelective(Testcase record);

    Testcase selectByPrimaryKey(Integer testcaseid);

    int updateByPrimaryKeySelective(Testcase record);

    int updateByPrimaryKey(Testcase record);
}