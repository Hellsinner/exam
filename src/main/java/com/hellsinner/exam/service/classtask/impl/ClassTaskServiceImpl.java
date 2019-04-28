package com.hellsinner.exam.service.classtask.impl;

import com.hellsinner.exam.component.ExamException;
import com.hellsinner.exam.component.UserContext;
import com.hellsinner.exam.dao.CourseclassMapper;
import com.hellsinner.exam.dao.CourseclasstaskMapper;
import com.hellsinner.exam.dao.CourstudentMapper;
import com.hellsinner.exam.dao.TaskquesMapper;
import com.hellsinner.exam.model.dao.Courseclass;
import com.hellsinner.exam.model.dao.Courseclasstask;
import com.hellsinner.exam.model.dao.Courstudent;
import com.hellsinner.exam.service.classtask.ClassTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassTaskServiceImpl implements ClassTaskService {

    @Autowired
    private CourseclasstaskMapper courseclasstaskMapper;

    @Autowired
    private CourseclassMapper courseclassMapper;

    @Autowired
    private TaskquesMapper taskquesMapper;

    @Autowired
    private CourstudentMapper courstudentMapper;

    @Override
    public void add(Courseclasstask courseclasstask) {
        Integer courclassid = courseclasstask.getCourclassid();
        Courseclass courseclass = courseclassMapper.selectByPrimaryKey(courclassid);
        if (!courseclass.getUserid().equals(UserContext.getUid())){
            throw new ExamException(ExamException.ExamExceptionEnum.AUTH_CLASS_TASK_NOT_ENOUGH);
        }
        if (courseclasstask.getEndtime().before(courseclasstask.getStarttime())){
            throw new ExamException(ExamException.ExamExceptionEnum.CLASS_TASK_TIME_ERROR);
        }
        courseclasstask.setStatus(0);
        courseclasstaskMapper.insert(courseclasstask);
    }

    @Override
    public void notice(Integer ctid) {
        Courseclasstask courseclasstask = courseclasstaskMapper.selectByPrimaryKey(ctid);
        Integer courclassid = courseclasstask.getCourclassid();
        Courseclass courseclass = courseclassMapper.selectByPrimaryKey(courclassid);
        if (!courseclass.getUserid().equals(UserContext.getUid())){
            throw new ExamException(ExamException.ExamExceptionEnum.AUTH_CLASS_TASK_NOT_ENOUGH);
        }
        Integer count = taskquesMapper.selectCountByTid(courseclasstask.getTaskid());
        if (count == 0){
            throw new ExamException(ExamException.ExamExceptionEnum.CLASS_TASK_NOT_HAVE_QUESTION);
        }
        courseclasstask.setStatus(1);
        courseclasstaskMapper.updateByPrimaryKey(courseclasstask);
    }

    @Override
    public void list(Integer classid) {
        Courstudent courstudent = courstudentMapper.selectByUidAndCid(classid, UserContext.getUid());
        if (courstudent == null){
            throw new ExamException(ExamException.ExamExceptionEnum.AUTH_CLASS_TASK_NOT_ENOUGH);
        }
        List<Courseclasstask> courseclasstasks = courseclasstaskMapper.selectByClassid(classid);

        for (Courseclasstask courseclasstask : courseclasstasks){

        }
    }
}
