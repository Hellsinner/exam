package com.hellsinner.exam.service.question;

import com.hellsinner.exam.controller.question.TaskQuesSelect;
import com.hellsinner.exam.model.dao.Question;
import com.hellsinner.exam.model.web.QuestionResult;
import com.hellsinner.exam.model.web.QuestionSelect;

import java.util.List;

public interface QuestionService {
    void add(List<Question> questions);

    List<Question> select(QuestionSelect questionSelect);

    QuestionResult taskselect(TaskQuesSelect taskQuesSelect);
}
