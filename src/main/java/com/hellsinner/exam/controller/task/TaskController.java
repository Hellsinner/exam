package com.hellsinner.exam.controller.task;

import com.hellsinner.exam.model.annocations.Authorize;
import com.hellsinner.exam.model.dao.Question;
import com.hellsinner.exam.model.dao.Task;
import com.hellsinner.exam.model.dao.Taskques;
import com.hellsinner.exam.model.web.Result;
import com.hellsinner.exam.model.web.TaskAISelect;
import com.hellsinner.exam.model.web.TaskListInfo;
import com.hellsinner.exam.service.task.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/task/create")
    @Authorize(value = 1)
    public Result create(@RequestBody Task task){
        return Result.ok(taskService.create(task));
    }

    @GetMapping("/task/info/{tid}")
    @Authorize(value = 1)
    public Result info(@PathVariable Integer tid){
        TaskListInfo info = taskService.info(tid);
        return Result.ok(info);
    }

    @GetMapping("/task/my")
    @Authorize(value = 1)
    public Result my(){
        return Result.ok(taskService.my());
    }

//    @PostMapping("/task/add/range/{tid}")
//    @Authorize(value = 1)
//    public Result addRange(@PathVariable Integer tid,@RequestBody List<Integer> ids){
//        paperRangeService.add(tid,ids);
//        return Result.ok();
//    }
//
//    @PostMapping("/task/add/construct/{tid}")
//    @Authorize(value = 1)
//    public Result addConstruct(@PathVariable Integer tid, @RequestBody Taskconstruct taskconstruct){
//        taskconstruct.setTaskid(tid);
//        //paperRangeService.add(paperrange);
//        return Result.ok();
//    }

    @PostMapping("/task/add/question/{tid}")
    @Authorize(value = 1)
    public Result taskaddQuestion(@PathVariable Integer tid, @RequestBody List<Taskques> taskques){
        taskService.addQuestion(tid,taskques);
        return Result.ok();
    }

    @PostMapping("/task/AI/add/question/{tid}")
    @Authorize(value = 1)
    public Result taskAiaddQuestion(@PathVariable Integer tid,@RequestBody TaskAISelect taskAISelect){
        List<Question> questions = taskService.aiAddQuestion(tid,taskAISelect);
        return Result.ok(questions);
    }

    @PostMapping("/task/auth/{tid}")
    @Authorize(value = 1)
    public Result authAdd(@PathVariable Integer tid,String email){
        taskService.addAuth(tid,email);
        return Result.ok();
    }

    @GetMapping("/task/select/{cid}")
    @Authorize(value = 1)
    public Result select(@PathVariable Integer cid){
        return Result.ok(taskService.getByCid(cid));
    }
}
