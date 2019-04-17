package com.hellsinner.exam.service.courseclass;

import com.hellsinner.exam.model.dao.Courseclass;
import com.hellsinner.exam.model.dao.User;
import com.hellsinner.exam.model.web.CourseClassInfo;

import java.util.List;

public interface CourseClassService {
    Integer create(Courseclass courseclass);

    List<CourseClassInfo> myCourseClass();

    CourseClassInfo getCourseClass(Integer id);

    List<User> getCourseClassStudents(Integer id);

    void join(Integer id);
}
