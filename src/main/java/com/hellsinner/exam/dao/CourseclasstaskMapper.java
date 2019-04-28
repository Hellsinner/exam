package com.hellsinner.exam.dao;

import com.hellsinner.exam.model.dao.Courseclasstask;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseclasstaskMapper {
    int deleteByPrimaryKey(Integer classtaskid);

    int insert(Courseclasstask record);

    int insertSelective(Courseclasstask record);

    Courseclasstask selectByPrimaryKey(Integer classtaskid);

    List<Courseclasstask> selectByClassid(@Param("classid") Integer classid);

    int updateByPrimaryKeySelective(Courseclasstask record);

    int updateByPrimaryKey(Courseclasstask record);
}