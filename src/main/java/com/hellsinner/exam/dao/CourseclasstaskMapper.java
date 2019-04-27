package com.hellsinner.exam.dao;

import com.hellsinner.exam.model.dao.Courseclasstask;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseclasstaskMapper {
    int deleteByPrimaryKey(Integer classtaskid);

    int insert(Courseclasstask record);

    int insertSelective(Courseclasstask record);

    Courseclasstask selectByPrimaryKey(Integer classtaskid);

    int updateByPrimaryKeySelective(Courseclasstask record);

    int updateByPrimaryKey(Courseclasstask record);
}