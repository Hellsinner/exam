package com.hellsinner.exam.controller.courseclass;

import com.hellsinner.exam.component.ExamException;
import com.hellsinner.exam.model.annocations.Authorize;
import com.hellsinner.exam.model.dao.Courseclass;
import com.hellsinner.exam.model.web.Result;
import com.hellsinner.exam.service.courseclass.CourseClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class CourseClassController {

    @Autowired
    private CourseClassService courseClassService;

    @PostMapping("/courseClass/create")
    @Authorize(value = 1)
    public Result addCourseClass(@RequestBody @Valid Courseclass courseclass, BindingResult result){
        if (result.hasErrors()){
            return Result.failed(ExamException.ExamExceptionEnum.CREATE_CLASS_PARAMS_Insufficient);
        }
        return Result.ok(courseClassService.create(courseclass));
    }

    @GetMapping("/courseClass/my")
    @Authorize
    public Result myCourseClass(){
        return Result.ok(courseClassService.myCourseClass());
    }

    @PostMapping("/courseClass/join/{id}")
    @Authorize
    public Result joinCourseClass(@PathVariable Integer id){
        courseClassService.join(id);
        return Result.ok();
    }

    @GetMapping("/courseClass/info/{id}")
    @Authorize
    public Result info(@PathVariable Integer id){
        return Result.ok(courseClassService.getCourseClass(id));
    }

    @GetMapping("/courseClass/students/{id}")
    @Authorize
    public Result students(@PathVariable Integer id){
        return Result.ok(courseClassService.getCourseClassStudents(id));
    }

}
