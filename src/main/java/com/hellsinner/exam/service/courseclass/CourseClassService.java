package com.hellsinner.exam.service.courseclass;

import com.hellsinner.exam.model.dao.Courseclass;
import com.hellsinner.exam.model.web.CourseClassInfo;
import com.hellsinner.exam.model.web.UserInfo;

import java.util.List;

public interface CourseClassService {
    Integer create(Courseclass courseclass);

    List<CourseClassInfo> myCourseClass();

    CourseClassInfo getCourseClass(Integer id);

    List<UserInfo> getCourseClassStudents(Integer id);

    void join(Integer id);
}
