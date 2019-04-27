package com.hellsinner.exam.dao;

import com.hellsinner.exam.model.dao.Quesanswer;
import org.springframework.stereotype.Repository;

@Repository
public interface QuesanswerMapper {
    int deleteByPrimaryKey(Integer answerid);

    int insert(Quesanswer record);

    int insertSelective(Quesanswer record);

    Quesanswer selectByPrimaryKey(Integer answerid);

    int updateByPrimaryKeySelective(Quesanswer record);

    int updateByPrimaryKeyWithBLOBs(Quesanswer record);

    int updateByPrimaryKey(Quesanswer record);
}