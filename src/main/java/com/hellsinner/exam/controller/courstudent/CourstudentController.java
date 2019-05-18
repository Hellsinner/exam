package com.hellsinner.exam.controller.courstudent;

import com.hellsinner.exam.model.annocations.Authorize;
import com.hellsinner.exam.model.web.Result;
import com.hellsinner.exam.service.courstudent.CourstudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exam")
public class CourstudentController {
    @Autowired
    private CourstudentService courstudentService;

    @GetMapping("/courseStudent/detele/{cid}/{uid}")
    @Authorize(value = 1)
    public Result delStudent(@PathVariable Integer cid,@PathVariable Integer uid){
        courstudentService.delStudent(cid,uid);
        return Result.ok();
    }

    @PostMapping("/courseStudent/comment/{cid}/{uid}")
    @Authorize(value = 1)
    public Result comment(@PathVariable Integer cid, @PathVariable Integer uid,
                          @RequestParam String comment){
        courstudentService.addComment(uid,cid,comment);
        return Result.ok();
    }
}
