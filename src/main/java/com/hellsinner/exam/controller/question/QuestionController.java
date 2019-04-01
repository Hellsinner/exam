package com.hellsinner.exam.controller.question;

import com.hellsinner.exam.component.ExamException;
import com.hellsinner.exam.model.annocations.Authorize;
import com.hellsinner.exam.model.web.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Part;
import java.io.IOException;

@RestController
public class QuestionController {

    @PostMapping("/upload")
    public Result upload(Part file){
        String fileName = file.getSubmittedFileName();
        try {
            file.write(fileName);
            return Result.ok(fileName);
        } catch (IOException e) {
            return Result.failed(ExamException.ExamExceptionEnum.UPLOAD_PICTURE_FAILE);
        }
    }

    @PostMapping("/question/create")
    @Authorize(value = 1)
    public Result create(){
        return Result.ok();
    }
}
