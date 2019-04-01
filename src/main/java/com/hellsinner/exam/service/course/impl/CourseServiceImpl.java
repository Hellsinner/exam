package com.hellsinner.exam.service.course.impl;

import com.hellsinner.exam.dao.CourseMapper;
import com.hellsinner.exam.model.dao.Course;
import com.hellsinner.exam.service.course.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Override
    public List<Course> list() {
        return courseMapper.selectList();
    }

    @Override
    public void add(Course course) {
        courseMapper.insert(course);
    }
}
