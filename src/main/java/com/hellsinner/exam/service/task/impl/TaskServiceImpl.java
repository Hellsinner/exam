package com.hellsinner.exam.service.task.impl;

import com.hellsinner.exam.component.UserContext;
import com.hellsinner.exam.dao.TaskMapper;
import com.hellsinner.exam.model.dao.Task;
import com.hellsinner.exam.service.task.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskMapper taskMapper;

    @Override
    public Task create(Task task) {
        task.setUserid(UserContext.getUid());
        taskMapper.insert(task);
        return task;
    }
}
