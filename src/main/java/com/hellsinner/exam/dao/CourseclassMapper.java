package com.hellsinner.exam.dao;

import com.hellsinner.exam.model.dao.Courseclass;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CourseclassMapper {
    int deleteByPrimaryKey(Integer courclassid);

    int insert(Courseclass record);

    int insertSelective(Courseclass record);

    Courseclass selectByPrimaryKey(Integer courclassid);

    int updateByPrimaryKeySelective(Courseclass record);

    int updateByPrimaryKey(Courseclass record);

    List<Courseclass> selectByUid(@Param("uid") Integer uid);

    void incrClassSize(@Param("id") Integer id);
}