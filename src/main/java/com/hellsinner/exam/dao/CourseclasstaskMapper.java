package com.hellsinner.exam.dao;

import com.hellsinner.exam.model.dao.Courseclasstask;
import com.hellsinner.exam.model.web.CourseclasstaskInfo;
import com.hellsinner.exam.model.web.ExamineInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseclasstaskMapper {
    int insert(Courseclasstask record);

    Courseclasstask selectByPrimaryKey(Integer classtaskid);

    List<CourseclasstaskInfo> selectByClassid(@Param("classids") List<Integer> classids);

    CourseclasstaskInfo selectByClassTaskid(@Param("classtaskid") Integer classtaskid);

    List<CourseclasstaskInfo> selectStuClassid(@Param("classids") List<Integer> classid);

    int updateByPrimaryKey(Courseclasstask record);

    List<ExamineInfo> examine(@Param("courclassid") Integer courclassid);
}