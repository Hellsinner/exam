package com.hellsinner.exam.controller.classtask;

import com.hellsinner.exam.model.annocations.Authorize;
import com.hellsinner.exam.model.web.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClassTaskController {

    @PostMapping("/classtask/create")
    @Authorize(value = 1)
    public Result create(){
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
