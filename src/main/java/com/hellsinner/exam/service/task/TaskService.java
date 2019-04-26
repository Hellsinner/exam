package com.hellsinner.exam.service.task;

import com.hellsinner.exam.model.dao.Task;
import com.hellsinner.exam.model.dao.Taskques;
import com.hellsinner.exam.model.web.TaskInfo;
import com.hellsinner.exam.model.web.TaskListInfo;

import java.util.List;

public interface TaskService {
    Task create(Task task);

    void linkQuestion(List<String> ids);

    List<TaskInfo> my();

    TaskListInfo info(Integer tid);

    void addQuestion(Integer tid, List<Taskques> taskques);
}
