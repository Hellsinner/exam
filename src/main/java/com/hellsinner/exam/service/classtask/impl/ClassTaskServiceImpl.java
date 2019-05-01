package com.hellsinner.exam.service.classtask.impl;

import com.google.common.collect.Lists;
import com.hellsinner.exam.component.ExamException;
import com.hellsinner.exam.component.UserContext;
import com.hellsinner.exam.dao.*;
import com.hellsinner.exam.model.dao.*;
import com.hellsinner.exam.model.web.CourseclasstaskInfo;
import com.hellsinner.exam.model.web.ExamInfo;
import com.hellsinner.exam.service.classtask.ClassTaskService;
import com.hellsinner.exam.service.question.QuestionService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Autowired
    private StutaskanswerMapper stutaskanswerMapper;

    @Autowired
    private QuestionService questionService;

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
        if (courseclasstask.getStatus() == 1 || new Date().after(courseclasstask.getStarttime())) {
            throw new ExamException(ExamException.ExamExceptionEnum.OPER_HAS_MISTAKE);
        }
        courseclasstask.setStatus(1);
        courseclasstaskMapper.updateByPrimaryKey(courseclasstask);
    }

    @Override
    public List<CourseclasstaskInfo> list(Integer classid) {
        Courseclass courseclass = courseclassMapper.selectByPrimaryKey(classid);
        if (!courseclass.getUserid().equals(UserContext.getUid())){
            throw new ExamException(ExamException.ExamExceptionEnum.AUTH_CLASS_TASK_NOT_ENOUGH);
        }
        List<CourseclasstaskInfo> courseclasstasks = courseclasstaskMapper.selectByClassid(classid);

        for (CourseclasstaskInfo courseclasstaskInfo : courseclasstasks){
            Date now = new Date();
            if (courseclasstaskInfo.getStatus() == 0
                    && now.before(courseclasstaskInfo.getStarttime()) ){
                courseclasstaskInfo.setIsnoticed(false);
            }else {
                courseclasstaskInfo.setIsnoticed(true);
            }
        }
        return courseclasstasks;
    }

    @Override
    public List<CourseclasstaskInfo> myjoin(Integer classid) {
        Integer uid = UserContext.getUid();
        Courstudent courstudent = courstudentMapper.selectByUidAndCid(classid, uid);
        if (courstudent == null){
            throw new ExamException(ExamException.ExamExceptionEnum.OPER_HAS_MISTAKE);
        }
        List<CourseclasstaskInfo> courseclasstasks = courseclasstaskMapper.selectStuClassid(classid);
        if (CollectionUtils.isEmpty(courseclasstasks)){
            return Lists.newArrayList();
        }
        for (CourseclasstaskInfo courseclasstaskInfo : courseclasstasks){
            Stutaskanswer stutaskanswer = stutaskanswerMapper
                    .selectByTUid(courseclasstaskInfo.getClasstaskid(), uid);
            Date now = new Date();
            if (stutaskanswer == null && now.after(courseclasstaskInfo.getStarttime())
                    && now.before(courseclasstaskInfo.getEndtime())){
                courseclasstaskInfo.setIsexam(true);
            }else {
                courseclasstaskInfo.setIsexam(false);
            }
        }
        return courseclasstasks;
    }

    @Override
    public List<ExamInfo> start(Integer ctid) {
        Courseclasstask courseclasstask = courseclasstaskMapper.selectByPrimaryKey(ctid);
        Date now = new Date();
        if (courseclasstask == null || courseclasstask.getStatus()==0){
            throw new ExamException(ExamException.ExamExceptionEnum.OPER_HAS_MISTAKE);
        }
        Courstudent courstudent = courstudentMapper
                .selectByUidAndCid(courseclasstask.getCourclassid(), UserContext.getUid());
        if (courstudent == null){
            throw new ExamException(ExamException.ExamExceptionEnum.OPER_HAS_MISTAKE);
        }
        Stutaskanswer stutaskanswer = stutaskanswerMapper.selectByTUid(ctid, UserContext.getUid());
        if (stutaskanswer != null ||
                now.before(courseclasstask.getStarttime()) ||
                now.after(courseclasstask.getEndtime())){
            throw new ExamException(ExamException.ExamExceptionEnum.OPER_HAS_MISTAKE);
        }
        Map<String, ExamInfo> stringTaskquesMap = taskquesMapper.selectByTid(courseclasstask.getTaskid());
        List<String> qids = stringTaskquesMap.keySet().stream().collect(Collectors.toList());
        List<Question> questions = questionService.excludeAnswer(qids);
        List<ExamInfo> list = Lists.newArrayList();
        for (Question question : questions){
            ExamInfo examInfo = stringTaskquesMap.get(question.getId());
            examInfo.setQuestion(question);
            list.add(examInfo);
        }
        return list;
    }

    @Override
    public void submit(Integer ctid, List<Quesanswer> quesanswers) {

    }
}
