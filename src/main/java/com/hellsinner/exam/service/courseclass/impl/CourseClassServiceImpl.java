package com.hellsinner.exam.service.courseclass.impl;

import com.hellsinner.exam.component.ExamException;
import com.hellsinner.exam.component.UserContext;
import com.hellsinner.exam.dao.CourseclassMapper;
import com.hellsinner.exam.model.dao.Courseclass;
import com.hellsinner.exam.model.dao.Courstudent;
import com.hellsinner.exam.model.dao.User;
import com.hellsinner.exam.model.web.CourseClassInfo;
import com.hellsinner.exam.model.web.UserInfo;
import com.hellsinner.exam.service.courseclass.CourseClassService;
import com.hellsinner.exam.service.courstudent.CourstudentService;
import com.hellsinner.exam.service.user.UserService;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseClassServiceImpl implements CourseClassService {

    @Autowired
    private CourstudentService courstudentService;

    @Autowired
    private CourseclassMapper courseclassMapper;

    @Autowired
    private UserService userService;

    @Override
    public Integer create(Courseclass courseclass) {
        courseclass.setUserid(UserContext.getUid());
        courseclass.setCourclasssize(1);
        courseclassMapper.insert(courseclass);

        Courstudent courstudent = new Courstudent();
        courstudent.setUserid(courseclass.getUserid());
        courstudent.setCourclassid(courseclass.getCourclassid());
        courstudentService.insert(courstudent);
        return courseclass.getCourclassid();
    }

    @Override
    public List<CourseClassInfo> myCourseClass() {
        Integer uid = UserContext.getUid();

        List<Courseclass> courseclasses = courseclassMapper.selectByUid(uid);

        List<CourseClassInfo> courseClassInfos = courseclasses
                .stream()
                .map(courseclass -> tranform(courseclass, uid))
                .collect(Collectors.toList());

        return courseClassInfos;
    }

    private CourseClassInfo tranform(Courseclass courseclass,Integer uid){
        CourseClassInfo courseClassInfo = new CourseClassInfo();
        try {
            BeanUtils.copyProperties(courseClassInfo,courseclass);
            User user = userService.getUser(courseclass.getUserid());
            if (uid.equals(courseclass.getUserid())){
                courseClassInfo.setCreator("我自己");
            }else {
                courseClassInfo.setCreator(user.getName());
            }
            return courseClassInfo;
        } catch (Exception e) {
            throw new ExamException(ExamException.ExamExceptionEnum.SERVER_ERROR);
        }
    }

    @Override
    public CourseClassInfo getCourseClass(Integer id) {
        Courseclass courseclass = courseclassMapper.selectByPrimaryKey(id);

        return this.tranform(courseclass, UserContext.getUid());
    }

    @Override
    public List<UserInfo> getCourseClassStudents(Integer id) {
        List<Courstudent> classStudents = courstudentService.getClassStudents(id);

        List<UserInfo> userInfos = userService.batchGetUserInfo(classStudents);

        return userInfos;
    }

    @Override
    public void join(Integer id) {
        Courstudent classStudent = courstudentService.getClassStudent(id,UserContext.getUid());
        if (classStudent != null){
            throw new ExamException(ExamException.ExamExceptionEnum.AGAIN_JOIN_CLASS);
        }

        User user = userService.getUser(UserContext.getUid());
        Courstudent courstudent = new Courstudent();
        courstudent.setUserid(user.getUserid());
        courstudent.setCourclassid(id);

        courseclassMapper.incrClassSize(id);
        courstudentService.insert(courstudent);
    }
}
