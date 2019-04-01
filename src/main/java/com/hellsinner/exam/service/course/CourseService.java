package com.hellsinner.exam.service.course;

import com.hellsinner.exam.model.dao.Course;

import java.util.List;

public interface CourseService {
    List<Course> list();

    void add(Course course);
}
