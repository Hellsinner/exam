package com.hellsinner.exam.controller.classtask;

import com.hellsinner.exam.model.annocations.Authorize;
import com.hellsinner.exam.model.dao.Courseclasstask;
import com.hellsinner.exam.model.web.Result;
import com.hellsinner.exam.service.classtask.ClassTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ClassTaskController {

    @Autowired
    private ClassTaskService classTaskService;

    @PostMapping("/classtask/create")
    @Authorize(value = 1)
    public Result create(@RequestBody Courseclasstask courseclasstask){
        classTaskService.add(courseclasstask);
        return Result.ok();
    }

    @PostMapping("/classtask/notice/{ctid}")
    @Authorize(value = 1)
    public Result notice(@PathVariable Integer ctid){
        classTaskService.notice(ctid);
        return Result.ok();
    }

    @GetMapping("/classtask/list/{classid}")
    @Authorize(value = 0)
    public Result list(@PathVariable Integer classid){
        classTaskService.list(classid);
        return Result.ok();
    }
}
