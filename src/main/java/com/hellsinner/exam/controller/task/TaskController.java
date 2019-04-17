package com.hellsinner.exam.controller.task;

import com.hellsinner.exam.model.annocations.Authorize;
import com.hellsinner.exam.model.dao.Paperrange;
import com.hellsinner.exam.model.dao.Task;
import com.hellsinner.exam.model.web.Result;
import com.hellsinner.exam.service.task.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/task/create")
    @Authorize(value = 1)
    public Result create(Task task){
        return Result.ok(taskService.create(task));
    }

    @PostMapping("/task/add/range/{tid}")
    @Authorize(value = 1)
    public Result addRange(@PathVariable Long tid, Paperrange paperrange){
        return Result.ok();
    }

    @PostMapping("/task/add/question/{tid}")
    @Authorize(value = 1)
    public Result addQuestion(){
        return Result.ok();
    }

}
