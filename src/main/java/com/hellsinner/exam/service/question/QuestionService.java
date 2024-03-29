package com.hellsinner.exam.service.question;

import com.hellsinner.exam.model.dao.Taskques;
import com.hellsinner.exam.model.web.TaskQuesSelect;
import com.hellsinner.exam.model.dao.Question;
import com.hellsinner.exam.model.web.QuestionResult;
import com.hellsinner.exam.model.web.QuestionSelect;

import java.util.List;

public interface QuestionService {
    void add(List<Question> questions);

    List<Question> select(QuestionSelect questionSelect);

    QuestionResult taskselect(TaskQuesSelect taskQuesSelect);

    List<Question> questions(List<Taskques> ids);

    List<Question> lists(List<String> ids);

    List<Question> excludeAnswer(List<String> ids,int type);
}
