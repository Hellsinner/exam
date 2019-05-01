package com.hellsinner.exam.service.classtask;

import com.hellsinner.exam.model.dao.Courseclasstask;
import com.hellsinner.exam.model.dao.Quesanswer;
import com.hellsinner.exam.model.web.CourseclasstaskInfo;
import com.hellsinner.exam.model.web.ExamInfo;

import java.util.List;

public interface ClassTaskService {
    void add(Courseclasstask courseclasstask);

    void notice(Integer ctid);

    List<CourseclasstaskInfo> list(Integer classid);

    List<CourseclasstaskInfo> myjoin(Integer classid);

    List<ExamInfo> start(Integer ctid);

    void submit(Integer ctid, List<Quesanswer> quesanswers);
}
