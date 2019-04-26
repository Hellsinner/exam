package com.hellsinner.exam.controller.question;

import com.hellsinner.exam.component.ExamException;
import com.hellsinner.exam.model.annocations.Authorize;
import com.hellsinner.exam.model.dao.Question;
import com.hellsinner.exam.model.web.QuestionSelect;
import com.hellsinner.exam.model.web.Result;
import com.hellsinner.exam.service.question.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Part;
import java.io.IOException;
import java.util.List;

@RestController
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    private static final String prefix = "/images/";

    @PostMapping("/upload")
    public Result upload(Part file){
        String fileName = file.getSubmittedFileName();
        if (!fileName.endsWith(".jpg") && !fileName.endsWith(".png") && !fileName.endsWith("jpeg")){
            return Result.failed(ExamException.ExamExceptionEnum.NOT_A_PICTURE);
        }
        long currentTimeMillis = System.currentTimeMillis();
        try {
            file.write(currentTimeMillis+fileName);
            return Result.ok(prefix+currentTimeMillis+fileName);
        } catch (IOException e) {
            return Result.failed(ExamException.ExamExceptionEnum.UPLOAD_PICTURE_FAILE);
        }
    }

    @PostMapping("/question/add")
    @Authorize(value = 1)
    public Result create(@RequestBody List<Question> questions){
        questionService.add(questions);
        return Result.ok();
    }

    @GetMapping("/question/select")
    @Authorize(value = 1)
    public Result select(QuestionSelect questionSelect){
        List<Question> questions = questionService.select(questionSelect);
        return Result.ok(questions);
    }

    @PostMapping("/task/question/select")
    @Authorize(value = 1)
    public Result taskquesselect(@RequestBody TaskQuesSelect taskQuesSelect){
        return Result.ok(questionService.taskselect(taskQuesSelect));
    }
}
