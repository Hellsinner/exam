package com.hellsinner.exam.dao;

import com.hellsinner.exam.model.dao.Course;

import java.util.List;

public interface CourseMapper {
    int deleteByPrimaryKey(Integer courid);

    int insert(Course record);

    int insertSelective(Course record);

    Course selectByPrimaryKey(Integer courid);

    int updateByPrimaryKeySelective(Course record);

    int updateByPrimaryKey(Course record);

    List<Course> selectList();
}