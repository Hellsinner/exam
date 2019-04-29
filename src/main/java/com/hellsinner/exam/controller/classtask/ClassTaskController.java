package com.hellsinner.exam.controller.classtask;

import com.hellsinner.exam.model.annocations.Authorize;
import com.hellsinner.exam.model.dao.Courseclasstask;
import com.hellsinner.exam.model.web.CourseclasstaskInfo;
import com.hellsinner.exam.model.web.Result;
import com.hellsinner.exam.service.classtask.ClassTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @Authorize(value = 1)
    public Result list(@PathVariable Integer classid){
        List<CourseclasstaskInfo> list = classTaskService.list(classid);
        return Result.ok(list);
    }

    @GetMapping("/classtask/myjoin/{classid}")
    @Authorize(value = 0)
    public Result myjoin(@PathVariable Integer classid){
        List<CourseclasstaskInfo> list = classTaskService.myjoin(classid);
        return Result.ok(list);
    }
}
