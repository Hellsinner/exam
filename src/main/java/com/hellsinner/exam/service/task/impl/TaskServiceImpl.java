package com.hellsinner.exam.service.task.impl;

import com.google.common.collect.Lists;
import com.hellsinner.exam.component.ExamException;
import com.hellsinner.exam.component.UserContext;
import com.hellsinner.exam.dao.*;
import com.hellsinner.exam.model.dao.*;
import com.hellsinner.exam.model.web.*;
import com.hellsinner.exam.service.task.TaskService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private TaskquesMapper taskquesMapper;

    @Autowired
    private PaperrangeMapper paperrangeMapper;

    @Autowired
    private TaskconstructMapper taskconstructMapper;

    @Autowired
    private KnowledgeunitMapper knowledgeunitMapper;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TaskauthMapper taskauthMapper;

    @Value("${question.type.max.count}")
    private Integer quesTypeMaxCount;

    @Override
    public Task create(Task task) {
        task.setUserid(UserContext.getUid());
        taskMapper.insert(task);
        return task;
    }

    @Override
    public List<TaskInfo> my() {
        Integer uid = UserContext.getUid();
        List<TaskInfo> taskInfos = taskMapper.selectByUid(uid);
        List<Integer> tids = taskauthMapper.selectByUid(uid);
        if (CollectionUtils.isEmpty(tids)){
            return taskInfos;
        }
        taskInfos.addAll(taskMapper.selectListInfo(tids));
        return taskInfos;
    }

    @Override
    public TaskListInfo info(Integer tid) {
        TaskInfo taskInfo = taskMapper.selectInfo(tid);

        Integer uid = UserContext.getUid();

        if (!taskInfo.getUserid().equals(uid) && taskInfo.getStatus().equals(0) && taskauthMapper.selectByTUid(tid,uid) == null){
            throw new ExamException(ExamException.ExamExceptionEnum.NOT_HAVE_TASK);
        }

        List<Knowledgeunit> knowledgeunits = knowledgeunitMapper.selectByCid(taskInfo.getCourid());

        Map<String, Taskques> taskquesMap = taskquesMapper.selectByTid(tid);
        TaskListInfo info = new TaskListInfo();
        info.setTaskInfo(taskInfo);
        info.setKnowledgeunits(knowledgeunits);
        if (MapUtils.isEmpty(taskquesMap)){
            info.setPaperranges(Lists.newArrayList());
            info.setTaskconstructs(Lists.newArrayList());
            info.setQuestionInfos(Lists.newArrayList());
        }

        List<PaperrangeInfo> paperranges = paperrangeMapper.selectByTid(tid);

        List<Taskconstruct> taskconstructs = taskconstructMapper.selectByTid(tid);

        Query query = new Query();
        query.addCriteria(Criteria.where("_id").in(
                taskquesMap.keySet()
                        .stream().collect(Collectors.toList())));

        info.setPaperranges(paperranges);
        info.setTaskconstructs(taskconstructs);

        List<Question> questions = mongoTemplate.find(query, Question.class);
        List<QuestionInfo> questionInfos = Lists.newArrayList();
        for (Question question : questions){
            QuestionInfo questionInfo = QuestionInfo.adapt(question);
            questionInfo.setGrade(taskquesMap.get(question.getId()).getPoint());
            questionInfos.add(questionInfo);
        }
        info.setQuestionInfos(questionInfos);
        return info;
    }

    @Override
    public void addQuestion(Integer tid, List<Taskques> taskques) {
        Task task = taskMapper.selectByPrimaryKey(tid);
        if (!task.getUserid().equals(UserContext.getUid())){
            throw new ExamException(ExamException.ExamExceptionEnum.AUTH_NOT_ENOUGH);
        }
        if (CollectionUtils.isEmpty(taskques)){
            throw new ExamException(ExamException.ExamExceptionEnum.NOT_HAVE_QUESTION);
        }

        taskques.stream().forEach(ques -> ques.setTaskid(tid));
        taskquesMapper.insertMany(taskques);
    }

    @Override
    public List<Question> aiAddQuestion(Integer tid, TaskAISelect taskAISelect) {
        Task task = taskMapper.selectByPrimaryKey(tid);
        if (!task.getUserid().equals(UserContext.getUid())){
            throw new ExamException(ExamException.ExamExceptionEnum.AUTH_NOT_ENOUGH);
        }

        if (CollectionUtils.isEmpty(taskAISelect.getUnitids()) || CollectionUtils.isEmpty(taskAISelect.getConstruct())){
            throw new ExamException(ExamException.ExamExceptionEnum.CONDITION_NOT_ENOUGH);
        }
        MatchOperation matchunitids = Aggregation.match(Criteria.where("unitId").in(taskAISelect.getUnitids()));
        List<Question> questions = Lists.newArrayList();
        for (TaskAISelect.QuesSelect quesSelect : taskAISelect.getConstruct()){
            Integer count = quesSelect.getCount();
            if (count == null || count == 0){
                continue;
            }
            if (count >= quesTypeMaxCount){
                throw new ExamException(ExamException.ExamExceptionEnum.AI_QUESTION_TYPE_COUNT_GT_MAX);
            }
            Aggregation agg = Aggregation.newAggregation(
                    matchunitids,
                    Aggregation.match(Criteria.where("type").is(quesSelect.getType())),
                    Aggregation.sample(quesSelect.getCount())
            );
            AggregationResults<Question> questionAggregationResults =
                    mongoTemplate.aggregate(agg, "questions", Question.class);
            List<Question> mappedResults = questionAggregationResults.getMappedResults();
            if (mappedResults.size()<quesSelect.getCount()){
                throw new ExamException(ExamException.ExamExceptionEnum.AI_QUESTION_NOT_ENOUTH);
            }
            questions.addAll(mappedResults);
        }
        return questions;
    }

    @Override
    public void addAuth(Integer tid, String email) {
        Task task = taskMapper.selectByPrimaryKey(tid);
        if (!task.getTaskid().equals(UserContext.getUid())){
            throw new ExamException(ExamException.ExamExceptionEnum.AUTH_NOT_ENOUGH);
        }
        User user = userMapper.selectByEmail(email);
        if (user==null){
            throw new ExamException(ExamException.ExamExceptionEnum.NOT_FOUND_USER);
        }
        Taskauth byTUid = taskauthMapper.selectByTUid(tid, user.getUserid());
        if (byTUid != null){
            throw new ExamException(ExamException.ExamExceptionEnum.AGAIN_TASK_AUTH);
        }
        Taskauth taskauth = new Taskauth();
        taskauth.setTaskid(tid);
        taskauth.setUserid(user.getUserid());
        taskauthMapper.insert(taskauth);
    }
}
