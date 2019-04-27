package com.hellsinner.exam.dao;

import com.hellsinner.exam.model.dao.Questiontype;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestiontypeMapper {
    int deleteByPrimaryKey(Integer typeid);

    int insert(Questiontype record);

    int insertSelective(Questiontype record);

    Questiontype selectByPrimaryKey(Integer typeid);

    int updateByPrimaryKeySelective(Questiontype record);

    int updateByPrimaryKey(Questiontype record);
}