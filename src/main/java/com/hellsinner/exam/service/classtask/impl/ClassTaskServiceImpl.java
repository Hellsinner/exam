package com.hellsinner.exam.service.classtask.impl;

import com.google.common.collect.Lists;
import com.hellsinner.exam.component.ExamException;
import com.hellsinner.exam.component.UserContext;
import com.hellsinner.exam.dao.*;
import com.hellsinner.exam.model.dao.*;
import com.hellsinner.exam.model.enums.QuestionType;
import com.hellsinner.exam.model.web.*;
import com.hellsinner.exam.service.classtask.ClassTaskService;
import com.hellsinner.exam.service.courseclass.CourseClassService;
import com.hellsinner.exam.service.question.QuestionService;
import com.hellsinner.exam.utils.JsonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ClassTaskServiceImpl implements ClassTaskService {

    @Autowired
    private CourseclasstaskMapper courseclasstaskMapper;

    @Autowired
    private TaskMapper taskMapper;

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

    @Autowired
    private QuesanswerMapper quesanswerMapper;

    @Autowired
    private CourseClassService courseClassService;

    private static final double ZERO = 0d;

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
    public List<CourseclasstaskInfo> list() {
        List<Courseclass> courseclasses = courseClassService.myCreatorClass();
        if (CollectionUtils.isEmpty(courseclasses)){
            return Lists.newArrayList();
        }
        List<Integer> classids = courseclasses.stream()
                .map(Courseclass::getCourclassid)
                .collect(Collectors.toList());
        List<CourseclasstaskInfo> courseclasstasks = courseclasstaskMapper.selectByClassid(classids);

        List<CourseclasstaskInfo> result = Lists.newArrayList();
        for (CourseclasstaskInfo courseclasstaskInfo : courseclasstasks){
            Task task = taskMapper.selectByPrimaryKey(courseclasstaskInfo.getTaskid());
            if (task.getType() == 1){
                continue;
            }
            Date now = new Date();
            if (courseclasstaskInfo.getStatus() == 1 || now.after(courseclasstaskInfo.getStarttime())){
                courseclasstaskInfo.setIsnoticed(true);
            }else {
                courseclasstaskInfo.setIsnoticed(false);
            }
            if (now.after(courseclasstaskInfo.getEndtime())){
                courseclasstaskInfo.setIsallowview(true);
            }else {
                courseclasstaskInfo.setIsallowview(false);
            }
            result.add(courseclasstaskInfo);
        }
        return result;
    }

    @Override
    public List<CourseclasstaskInfo> myjoin() {
        List<CourseClassInfo> courseClassInfos = courseClassService.myCourseClass();
        if (CollectionUtils.isEmpty(courseClassInfos)){
            return Lists.newArrayList();
        }
        List<Integer> classids = courseClassInfos.stream()
                .map(CourseClassInfo::getCourclassid)
                .collect(Collectors.toList());
        List<CourseclasstaskInfo> courseclasstasks = courseclasstaskMapper.selectStuClassid(classids);
        if (CollectionUtils.isEmpty(courseclasstasks)){
            return Lists.newArrayList();
        }
        Integer uid = UserContext.getUid();
        List<CourseclasstaskInfo> result = Lists.newArrayList();
        for (CourseclasstaskInfo courseclasstaskInfo : courseclasstasks){
            Task task = taskMapper.selectByPrimaryKey(courseclasstaskInfo.getTaskid());
            if (task.getType() == 1){
                continue;
            }
            Stutaskanswer stutaskanswer = stutaskanswerMapper
                    .selectByTUid(courseclasstaskInfo.getClasstaskid(), uid);
            Date now = new Date();
            if (stutaskanswer == null && now.after(courseclasstaskInfo.getStarttime())
                    && now.before(courseclasstaskInfo.getEndtime())){
                courseclasstaskInfo.setIsexam(true);
            }else {
                courseclasstaskInfo.setIsexam(false);
            }
            if (now.after(courseclasstaskInfo.getEndtime())){
                courseclasstaskInfo.setIsallowview(true);
            }else {
                courseclasstaskInfo.setIsallowview(false);
            }
            result.add(courseclasstaskInfo);
        }
        return result;
    }

    @Override
    public ExamInfo start(Integer ctid,int type) {
        Courseclasstask courseclasstask = checkExam(ctid,type);
        Map<String, ExamQuesInfo> stringTaskquesMap =
                taskquesMapper.selectByTid(courseclasstask.getTaskid());
        List<String> qids = stringTaskquesMap.keySet()
                .stream().collect(Collectors.toList());
        List<Question> questions = questionService.excludeAnswer(qids,type);
        List<ExamQuesInfo> list = Lists.newArrayList();
        for (Question question : questions){
            ExamQuesInfo examInfo = stringTaskquesMap.get(question.getId());
            examInfo.setQuestion(question);
            list.add(examInfo);
        }
        ExamInfo examInfo = new ExamInfo();
        CourseclasstaskInfo courseclasstaskInfo = courseclasstaskMapper.selectByClassTaskid(ctid);
        examInfo.setQuesInfos(list);
        examInfo.setCourseclasstaskInfo(courseclasstaskInfo);
        return examInfo;
    }

    private Courseclasstask checkExam(Integer ctid,int type){
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
        if (type == 0){
            checkExam(courseclasstask,stutaskanswer,now);
        }else {
            checkView(courseclasstask,stutaskanswer,now);
        }

        return courseclasstask;
    }

    private void checkExam(Courseclasstask courseclasstask,Stutaskanswer stutaskanswer,Date now){
        if (stutaskanswer != null ||
                now.before(courseclasstask.getStarttime()) ||
                now.after(courseclasstask.getEndtime())){
            throw new ExamException(ExamException.ExamExceptionEnum.OPER_HAS_MISTAKE);
        }
    }

    private void checkView(Courseclasstask courseclasstask,Stutaskanswer stutaskanswer,Date now){
        if (now.before(courseclasstask.getEndtime())){
            throw new ExamException(ExamException.ExamExceptionEnum.OPER_HAS_MISTAKE);
        }
    }

    @Override
    @Transactional
    public void submit(Integer ctid, List<Quesanswer> quesanswers) {
        checkExam(ctid,0);
        if (CollectionUtils.isEmpty(quesanswers)){
            throw new ExamException(ExamException.ExamExceptionEnum.OPER_HAS_MISTAKE);
        }
        int status = 1;
        List<Integer> ids = quesanswers.stream()
                .map(Quesanswer::getTaskquesid).collect(Collectors.toList());
        Map<String, Taskques> stringTaskquesMap = taskquesMapper.selectByids(ids);
        List<String> qids = stringTaskquesMap.values().stream().map(Taskques::getQuesid).collect(Collectors.toList());
        List<Question> questions = questionService.lists(qids);
        Map<String, Question> questionMap = questions.stream().collect(
                Collectors.toMap(Question::getId, question -> question));
        double total = ZERO;
        for (Quesanswer quesanswer : quesanswers){
            Taskques taskques = stringTaskquesMap.get(quesanswer.getTaskquesid());
            Question question = questionMap.get(taskques.getQuesid());
            QuestionType type = QuestionType.getQuesType(question.getType());
            if (type == null){
                throw new ExamException(ExamException.ExamExceptionEnum.OPER_HAS_MISTAKE);
            }
            switch (type){
                case Individual_choice:
                case Judge:
                    if (StringUtils.isNotEmpty(quesanswer.getAnswer()) && quesanswer.getAnswer().equals(question.getAnswer())){
                        quesanswer.setScore(taskques.getPoint());
                    }else{
                        quesanswer.setScore(ZERO);
                    }
                    break;
                case Multiple_choice:
                    if (StringUtils.isNotEmpty(quesanswer.getAnswer())){
                        quesanswer.setScore(checkMultAnswer(quesanswer,question,taskques));
                    }else{
                        quesanswer.setScore(ZERO);
                    }
                    break;
                case Programm:
                    break;
                default:
                    status = 0;
            }
            total += quesanswer.getScore();
        }
        Stutaskanswer stutaskanswer = new Stutaskanswer();
        stutaskanswer.setUserid(UserContext.getUid());
        stutaskanswer.setClasstaskid(ctid);
        stutaskanswer.setTotalscore(total);
        stutaskanswer.setStatus(status);
        stutaskanswerMapper.insert(stutaskanswer);
        quesanswerMapper.insertMany(quesanswers,stutaskanswer.getTaskanswerid());
    }

    private double checkMultAnswer(Quesanswer quesanswer, Question question, Taskques taskques) {
        List<String> stuAn = JsonUtils.jsonToList(quesanswer.getAnswer());
        List<String> quAn = JsonUtils.jsonToList(question.getAnswer());

        if (stuAn.size() == 0){
            return ZERO;
        }else if (stuAn.size() < quAn.size()){
            int flag = 1;
            for (String s : stuAn){
                if (quAn.indexOf(s) == -1){
                    flag = 0;
                }
            }
            return flag == 1 ? taskques.getPoint()/2 : ZERO;
        }else if (stuAn.size() == quAn.size()){
            int flag = 1;
            for (String s : stuAn){
                if (quAn.indexOf(s) == -1){
                    flag = 0;
                }
            }
            return flag == 1 ? taskques.getPoint() : ZERO;
        }else {
            return ZERO;
        }
    }

    @Override
    public ViewInfo view(Integer ctid, Integer uid) {
        ViewInfo viewInfo = new ViewInfo();
        ExamInfo start = this.start(ctid, 1);
        Stutaskanswer stutaskanswer = stutaskanswerMapper.selectByTUid(ctid, uid);
        CourseclasstaskInfo courseclasstaskInfo = start.getCourseclasstaskInfo();
        List<ExamQuesInfo> quesInfos = start.getQuesInfos();
        List<Integer> ids = quesInfos.stream()
                .map(Taskques::getTaskquesid).collect(Collectors.toList());
        Map<Integer, Quesanswer> quesanswerMap = quesanswerMapper.selectByids(ids);
        List<QuestionView> questionInfos = Lists.newArrayList();
        for (ExamQuesInfo examQuesInfo : quesInfos){
            QuestionView questionView = new QuestionView();
            QuestionInfo questionInfo = QuestionInfo.adapt(examQuesInfo.getQuestion());
            Quesanswer quesanswer = quesanswerMap.get(examQuesInfo.getTaskquesid());
            questionInfo.setSubmitAnswer(quesanswer == null ? null : quesanswer.getAnswer());
            questionView.setQuestionInfo(questionInfo);
            questionView.setPoint(examQuesInfo.getPoint());
            questionInfos.add(questionView);
        }
        viewInfo.setStutaskanswer(stutaskanswer);
        viewInfo.setCourseclasstaskInfo(courseclasstaskInfo);
        viewInfo.setQuestionInfos(questionInfos);
        return viewInfo;
    }

    @Override
    public List<ExamineInfo> examine(Integer ctid) {
        CourseclasstaskInfo courseclasstaskInfo = courseclasstaskMapper.selectByClassTaskid(ctid);
        List<ExamineInfo> examine = courseclasstaskMapper.examine(courseclasstaskInfo.getCourclassid());

        if (CollectionUtils.isEmpty(examine)) {
            return examine;
        }
        examine.stream()
                .forEach(examineInfo -> {
                    examineInfo.setClasstaskid(ctid);
                    examineInfo.setClasstaskname(courseclasstaskInfo.getClasstaskname());
                });


        return examine;
    }

    @Override
    @Transactional
    public void examinesubmit(Integer ctid, Integer uid, List<Taskques> taskquess) {
        if (CollectionUtils.isEmpty(taskquess)){
            throw new ExamException(ExamException.ExamExceptionEnum.OPER_HAS_MISTAKE);
        }
        Courseclasstask courseclasstask = courseclasstaskMapper.selectByPrimaryKey(ctid);
        Map<String, ExamQuesInfo> stringExamQuesInfoMap = taskquesMapper.selectByTid(courseclasstask.getTaskid());
        List<Integer> collect = stringExamQuesInfoMap.values().stream()
                .map(Taskques::getTaskquesid)
                .collect(Collectors.toList());

        Map<Integer, Quesanswer> integerQuesanswerMap = quesanswerMapper.selectByids(collect);
        List<Quesanswer> quesanswers = taskquess.stream()
                .map(taskques -> {
                    ExamQuesInfo examQuesInfo = stringExamQuesInfoMap.get(taskques.getQuesid());
                    Quesanswer quesanswer = integerQuesanswerMap.get(examQuesInfo.getTaskquesid());
                    quesanswer.setScore(taskques.getPoint());
                    return quesanswer;
                }).collect(Collectors.toList());
        for (Quesanswer quesanswer :quesanswers){
            quesanswerMapper.updateScore(quesanswer);
        }
        //quesanswerMapper.updateMany(quesanswers);
        double sum = quesanswers.stream()
                .mapToDouble(Quesanswer::getScore).sum();
        Stutaskanswer stutaskanswer = stutaskanswerMapper.selectByTUid(ctid, uid);
        stutaskanswer.setTotalscore(stutaskanswer.getTotalscore()+sum);
        stutaskanswer.setStatus(1);
        stutaskanswerMapper.updateByPrimaryKey(stutaskanswer);
        return;
    }

    @Override
    public ViewInfo examineview(Integer ctid, Integer uid) {
        ViewInfo view = this.view(ctid, uid);
        List<QuestionView> questionInfos = view.getQuestionInfos();
        List<QuestionView> collect = questionInfos.stream()
                .filter(this::filterNoNeedExamineQuestion)
                .collect(Collectors.toList());
        view.setQuestionInfos(collect);
        return view;
    }

    @Override
    public List<CourseclasstaskInfo> notifyMe() {
        List<CourseClassInfo> courseClassInfos = courseClassService.myCourseClass();
        if (CollectionUtils.isEmpty(courseClassInfos)){
            return Lists.newArrayList();
        }
        List<Integer> classids = courseClassInfos.stream()
                .map(CourseClassInfo::getCourclassid)
                .collect(Collectors.toList());
        List<CourseclasstaskInfo> courseclasstasks = courseclasstaskMapper.selectStuClassid(classids);
        if (CollectionUtils.isEmpty(courseclasstasks)){
            return Lists.newArrayList();
        }
        Integer uid = UserContext.getUid();
        List<CourseclasstaskInfo> courseclasstaskInfos = Lists.newArrayList();
        for (CourseclasstaskInfo courseclasstaskInfo : courseclasstasks){
            Stutaskanswer stutaskanswer = stutaskanswerMapper
                    .selectByTUid(courseclasstaskInfo.getClasstaskid(), uid);
            Date now = new Date();
            if (stutaskanswer == null && now.before(courseclasstaskInfo.getEndtime())){
                courseclasstaskInfos.add(courseclasstaskInfo);
            }
        }
        return courseclasstaskInfos;
    }

    @Override
    public List<CourseclasstaskInfo> list1() {
        List<Courseclass> courseclasses = courseClassService.myCreatorClass();
        if (CollectionUtils.isEmpty(courseclasses)){
            return Lists.newArrayList();
        }
        List<Integer> classids = courseclasses.stream()
                .map(Courseclass::getCourclassid)
                .collect(Collectors.toList());
        List<CourseclasstaskInfo> courseclasstasks = courseclasstaskMapper.selectByClassid(classids);

        List<CourseclasstaskInfo> result = Lists.newArrayList();
        for (CourseclasstaskInfo courseclasstaskInfo : courseclasstasks){
            Task task = taskMapper.selectByPrimaryKey(courseclasstaskInfo.getTaskid());
            if (task.getType() == 0){
                continue;
            }
            Date now = new Date();
            if (courseclasstaskInfo.getStatus() == 1 || now.after(courseclasstaskInfo.getStarttime())){
                courseclasstaskInfo.setIsnoticed(true);
            }else {
                courseclasstaskInfo.setIsnoticed(false);
            }
            if (now.after(courseclasstaskInfo.getEndtime())){
                courseclasstaskInfo.setIsallowview(true);
            }else {
                courseclasstaskInfo.setIsallowview(false);
            }
            result.add(courseclasstaskInfo);
        }
        return result;
    }

    @Override
    public List<CourseclasstaskInfo> myjoin1() {
        List<CourseClassInfo> courseClassInfos = courseClassService.myCourseClass();
        if (CollectionUtils.isEmpty(courseClassInfos)){
            return Lists.newArrayList();
        }
        List<Integer> classids = courseClassInfos.stream()
                .map(CourseClassInfo::getCourclassid)
                .collect(Collectors.toList());
        List<CourseclasstaskInfo> courseclasstasks = courseclasstaskMapper.selectStuClassid(classids);
        if (CollectionUtils.isEmpty(courseclasstasks)){
            return Lists.newArrayList();
        }
        Integer uid = UserContext.getUid();
        List<CourseclasstaskInfo> result = Lists.newArrayList();
        for (CourseclasstaskInfo courseclasstaskInfo : courseclasstasks){
            Task task = taskMapper.selectByPrimaryKey(courseclasstaskInfo.getTaskid());
            if (task.getType() == 0){
                continue;
            }
            Stutaskanswer stutaskanswer = stutaskanswerMapper
                    .selectByTUid(courseclasstaskInfo.getClasstaskid(), uid);
            Date now = new Date();
            if (stutaskanswer == null && now.after(courseclasstaskInfo.getStarttime())
                    && now.before(courseclasstaskInfo.getEndtime())){
                courseclasstaskInfo.setIsexam(true);
            }else {
                courseclasstaskInfo.setIsexam(false);
            }
            if (now.after(courseclasstaskInfo.getEndtime())){
                courseclasstaskInfo.setIsallowview(true);
            }else {
                courseclasstaskInfo.setIsallowview(false);
            }
            result.add(courseclasstaskInfo);
        }
        return result;
    }

    private boolean filterNoNeedExamineQuestion(QuestionView questionView){
        QuestionType quesType = QuestionType.getQuesType(questionView.getQuestionInfo().getType());
        switch (quesType) {
            case Judge:
            case Programm:
            case Multiple_choice:
            case Individual_choice:
                return false;
            default:
                return true;
        }
    }
}
