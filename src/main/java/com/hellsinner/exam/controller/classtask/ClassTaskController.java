package com.hellsinner.exam.controller.classtask;

import com.hellsinner.exam.model.annocations.Authorize;
import com.hellsinner.exam.model.dao.Courseclasstask;
import com.hellsinner.exam.model.web.Result;
import org.springframework.web.bind.annotation.*;

@RestController
public class ClassTaskController {
    @PostMapping("/classtask/create")
    @Authorize(value = 1)
    public Result create(@RequestBody Courseclasstask courseclasstask){
        return Result.ok();
    }

    @GetMapping("/classtask/{ctid}")
    public Result get(@PathVariable Integer ctid){
        return Result.ok();
    }

    @PostMapping("/classtask/exam/{ctid}")
    public Result exam(@PathVariable Integer ctid){
        return Result.ok();
    }
}
