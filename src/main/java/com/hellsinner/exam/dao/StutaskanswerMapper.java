package com.hellsinner.exam.dao;

import com.hellsinner.exam.model.dao.Stutaskanswer;

public interface StutaskanswerMapper {
    int deleteByPrimaryKey(Integer taskanswerid);

    int insert(Stutaskanswer record);

    int insertSelective(Stutaskanswer record);

    Stutaskanswer selectByPrimaryKey(Integer taskanswerid);

    int updateByPrimaryKeySelective(Stutaskanswer record);

    int updateByPrimaryKey(Stutaskanswer record);
}