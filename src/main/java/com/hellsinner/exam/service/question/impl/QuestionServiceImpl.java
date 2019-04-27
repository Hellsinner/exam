package com.hellsinner.exam.service.question.impl;

import com.hellsinner.exam.component.ExamException;
import com.hellsinner.exam.component.UserContext;
import com.hellsinner.exam.model.web.TaskQuesSelect;
import com.hellsinner.exam.model.dao.Question;
import com.hellsinner.exam.model.web.QuestionResult;
import com.hellsinner.exam.model.web.QuestionSelect;
import com.hellsinner.exam.service.question.QuestionService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void add(List<Question> questions) {
        if (CollectionUtils.isEmpty(questions)){
            throw new ExamException(ExamException.ExamExceptionEnum.NOT_HAVE_QUESTION);
        }
        Integer uid = UserContext.getUid();
        questions
                .stream()
                .forEach(question -> question.setUserid(uid));
        mongoTemplate.insert(questions,Question.class);
    }

    @Override
    public List<Question> select(QuestionSelect questionSelect) {
        Query query = new Query();
        if (questionSelect.getUnitid() != null){
            query.addCriteria(Criteria.where("unitid").is(questionSelect.getUnitid()));
        }
        if (questionSelect.isIsmyexms()){
            query.addCriteria(Criteria.where("userid").is(UserContext.getUid()));
        }
        if (StringUtils.isNotEmpty(questionSelect.getType())){
            query.addCriteria(Criteria.where("type").in(questionSelect.getType()));
        }
        return mongoTemplate.find(query,Question.class);
    }

    @Override
    public QuestionResult taskselect(TaskQuesSelect taskQuesSelect) {
        Query query = new Query();
        if (CollectionUtils.isNotEmpty(taskQuesSelect.getUnitids())){
            query.addCriteria(Criteria.where("unitid").in(taskQuesSelect.getUnitids()));
        }

        if (taskQuesSelect.isIsmyexms()){
            query.addCriteria(Criteria.where("userid").is(UserContext.getUid()));
        }
        if (StringUtils.isNotEmpty(taskQuesSelect.getType())){
            query.addCriteria(Criteria.where("type").in(taskQuesSelect.getType()));
        }
        long count = mongoTemplate.count(query, Question.class);
        query.skip((taskQuesSelect.getPage()-1)*taskQuesSelect.getPageSize()).limit(taskQuesSelect.getPageSize());
        List<Question> questions = mongoTemplate.find(query, Question.class);
        QuestionResult result = new QuestionResult();
        result.setCount(count);
        result.setQuestions(questions);
        return result;
    }
}
