package com.hellsinner.exam.controller.course;

import com.hellsinner.exam.model.annocations.Authorize;
import com.hellsinner.exam.model.dao.Course;
import com.hellsinner.exam.model.web.Result;
import com.hellsinner.exam.service.course.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping("/course/list")
    @Authorize(value = 1)
    public Result list(){
        return Result.ok(courseService.list());
    }

    @PostMapping("/course/add")
    @Authorize(value = 1)
    public Result add(Course course){
        courseService.add(course);
        return Result.ok();
    }

}
