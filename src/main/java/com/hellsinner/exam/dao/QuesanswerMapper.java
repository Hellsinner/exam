package com.hellsinner.exam.dao;

import com.hellsinner.exam.model.dao.Quesanswer;

public interface QuesanswerMapper {
    int deleteByPrimaryKey(Integer answerid);

    int insert(Quesanswer record);

    int insertSelective(Quesanswer record);

    Quesanswer selectByPrimaryKey(Integer answerid);

    int updateByPrimaryKeySelective(Quesanswer record);

    int updateByPrimaryKeyWithBLOBs(Quesanswer record);

    int updateByPrimaryKey(Quesanswer record);
}