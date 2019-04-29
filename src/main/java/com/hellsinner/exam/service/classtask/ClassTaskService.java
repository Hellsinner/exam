package com.hellsinner.exam.service.classtask;

import com.hellsinner.exam.model.dao.Courseclasstask;
import com.hellsinner.exam.model.web.CourseclasstaskInfo;

import java.util.List;

public interface ClassTaskService {
    void add(Courseclasstask courseclasstask);

    void notice(Integer ctid);

    List<CourseclasstaskInfo> list(Integer classid);

    List<CourseclasstaskInfo> myjoin(Integer classid);
}
