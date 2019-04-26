package com.hellsinner.exam.model.web;

import com.hellsinner.exam.component.ExamException;
import com.hellsinner.exam.model.dao.Question;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.beanutils.BeanUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionInfo extends Question {

    public static QuestionInfo adapt(Question question){
        QuestionInfo questionInfo = new QuestionInfo();
        try {
            BeanUtils.copyProperties(questionInfo,question);
        }catch (Exception e){
            throw new ExamException(ExamException.ExamExceptionEnum.SERVER_ERROR);
        }
        return questionInfo;
    }
    private Double grade;
}
