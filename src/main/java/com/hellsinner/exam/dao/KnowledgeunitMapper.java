package com.hellsinner.exam.dao;

import com.hellsinner.exam.model.dao.Knowledgeunit;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface KnowledgeunitMapper {
    int deleteByPrimaryKey(Integer unitid);

    int insert(Knowledgeunit record);

    int insertSelective(Knowledgeunit record);

    Knowledgeunit selectByPrimaryKey(Integer unitid);

    int updateByPrimaryKeySelective(Knowledgeunit record);

    int updateByPrimaryKey(Knowledgeunit record);

    List<Knowledgeunit> selectByCid(@Param("cid") Integer cid);
}