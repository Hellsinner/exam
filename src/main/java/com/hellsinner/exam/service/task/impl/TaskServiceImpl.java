package com.hellsinner.exam.service.task.impl;

import com.google.common.collect.Lists;
import com.hellsinner.exam.component.ExamException;
import com.hellsinner.exam.component.UserContext;
import com.hellsinner.exam.dao.*;
import com.hellsinner.exam.model.dao.*;
import com.hellsinner.exam.model.web.PaperrangeInfo;
import com.hellsinner.exam.model.web.QuestionInfo;
import com.hellsinner.exam.model.web.TaskInfo;
import com.hellsinner.exam.model.web.TaskListInfo;
import com.hellsinner.exam.service.task.TaskService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Override
    public Task create(Task task) {
        task.setUserid(UserContext.getUid());
        taskMapper.insert(task);
        return task;
    }

    @Override
    public void linkQuestion(List<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            throw new ExamException(ExamException.ExamExceptionEnum.NOT_HAVE_QUESTION);
        }
        //taskquesMapper.insert()
    }

    @Override
    public List<TaskInfo> my() {
        return taskMapper.selectByUid(UserContext.getUid());
    }

    @Override
    public TaskListInfo info(Integer tid) {
        TaskInfo taskInfo = taskMapper.selectInfo(tid);

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
        if (CollectionUtils.isEmpty(taskques)){
            throw new ExamException(ExamException.ExamExceptionEnum.NOT_HAVE_QUESTION);
        }
        taskques.stream().forEach(ques -> ques.setTaskid(tid));
        taskquesMapper.insertMany(taskques);
//        List<String> ids = taskques.stream()
//                .map(Taskques::getQuesid)
//                .collect(Collectors.toList());
    }
}
