package com.hellsinner.exam.dao;

import com.hellsinner.exam.model.dao.Knowledgeunit;

public interface KnowledgeunitMapper {
    int deleteByPrimaryKey(Integer unitid);

    int insert(Knowledgeunit record);

    int insertSelective(Knowledgeunit record);

    Knowledgeunit selectByPrimaryKey(Integer unitid);

    int updateByPrimaryKeySelective(Knowledgeunit record);

    int updateByPrimaryKey(Knowledgeunit record);
}