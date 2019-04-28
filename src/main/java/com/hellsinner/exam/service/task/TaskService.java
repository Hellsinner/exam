package com.hellsinner.exam.service.task;

import com.hellsinner.exam.model.dao.Question;
import com.hellsinner.exam.model.dao.Task;
import com.hellsinner.exam.model.dao.Taskques;
import com.hellsinner.exam.model.web.TaskAISelect;
import com.hellsinner.exam.model.web.TaskInfo;
import com.hellsinner.exam.model.web.TaskListInfo;

import java.util.List;

public interface TaskService {
    Task create(Task task);

    List<TaskInfo> my();

    TaskListInfo info(Integer tid);

    void addQuestion(Integer tid, List<Taskques> taskques);

    List<Question> aiAddQuestion(Integer tid, TaskAISelect taskAISelect);

    void addAuth(Integer tid, String email);

    List<Task> getByCid(Integer cid);
}
