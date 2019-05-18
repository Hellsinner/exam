package com.hellsinner.exam.service.classtask;

import com.hellsinner.exam.model.dao.Courseclasstask;
import com.hellsinner.exam.model.dao.Quesanswer;
import com.hellsinner.exam.model.dao.Taskques;
import com.hellsinner.exam.model.web.CourseclasstaskInfo;
import com.hellsinner.exam.model.web.ExamInfo;
import com.hellsinner.exam.model.web.ExamineInfo;
import com.hellsinner.exam.model.web.ViewInfo;

import java.util.List;

public interface ClassTaskService {
    void add(Courseclasstask courseclasstask);

    void notice(Integer ctid);

    List<CourseclasstaskInfo> list();

    List<CourseclasstaskInfo> myjoin();

    ExamInfo start(Integer ctid,int type);

    void submit(Integer ctid, List<Quesanswer> quesanswers);

    ViewInfo view(Integer ctid,Integer uid);

    List<ExamineInfo> examine(Integer ctid);

    void examinesubmit(Integer ctid, Integer uid, List<Taskques> taskques);

    ViewInfo examineview(Integer ctid, Integer uid);

    List<CourseclasstaskInfo> notifyMe();

    List<CourseclasstaskInfo> list1();

    List<CourseclasstaskInfo> myjoin1();
}
