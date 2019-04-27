package com.hellsinner.exam.dao;

import com.hellsinner.exam.model.dao.Datadict;
import org.springframework.stereotype.Repository;

@Repository
public interface DatadictMapper {
    int deleteByPrimaryKey(Integer dictid);

    int insert(Datadict record);

    int insertSelective(Datadict record);

    Datadict selectByPrimaryKey(Integer dictid);

    int updateByPrimaryKeySelective(Datadict record);

    int updateByPrimaryKey(Datadict record);
}