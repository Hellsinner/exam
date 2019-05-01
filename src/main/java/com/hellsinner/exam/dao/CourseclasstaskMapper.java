package com.hellsinner.exam.dao;

import com.hellsinner.exam.model.dao.Courseclasstask;
import com.hellsinner.exam.model.web.CourseclasstaskInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseclasstaskMapper {
    int deleteByPrimaryKey(Integer classtaskid);

    int insert(Courseclasstask record);

    int insertSelective(Courseclasstask record);

    Courseclasstask selectByPrimaryKey(Integer classtaskid);

    List<CourseclasstaskInfo> selectByClassid(@Param("classid") Integer classid);

    List<CourseclasstaskInfo> selectStuClassid(@Param("classid") Integer classid);

    int updateByPrimaryKeySelective(Courseclasstask record);

    int updateByPrimaryKey(Courseclasstask record);
}