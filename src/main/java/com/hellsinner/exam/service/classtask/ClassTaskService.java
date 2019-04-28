package com.hellsinner.exam.service.classtask;

import com.hellsinner.exam.model.dao.Courseclasstask;

public interface ClassTaskService {
    void add(Courseclasstask courseclasstask);

    void notice(Integer ctid);

    void list(Integer classid);
}
