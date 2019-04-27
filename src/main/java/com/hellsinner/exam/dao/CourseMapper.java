package com.hellsinner.exam.dao;

import com.hellsinner.exam.model.dao.Course;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CourseMapper {
    int deleteByPrimaryKey(Integer courid);

    int insert(Course record);

    int insertSelective(Course record);

    Course selectByPrimaryKey(Integer courid);

    int updateByPrimaryKeySelective(Course record);

    int updateByPrimaryKey(Course record);

    List<Course> selectList();
}