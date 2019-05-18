package com.hellsinner.exam.service.program;

import com.hellsinner.exam.model.dao.Question;
import com.hellsinner.exam.model.dao.Submission;
import com.hellsinner.exam.model.web.QuestionResult;
import com.hellsinner.exam.model.web.Rank;

import java.util.List;

public interface ProgramService {
    QuestionResult programList(Integer page, Integer size);

    List<Question> info(String quesid);

    void submit(String quesid, Submission submission);

    Object submissions(String quesid, Integer type, Integer page, Integer size);

    Submission submission(String id);

    List<Rank> todayRank();

    List<Rank> allRank();
}
