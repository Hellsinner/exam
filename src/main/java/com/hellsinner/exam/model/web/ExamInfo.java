package com.hellsinner.exam.model.web;

import com.hellsinner.exam.model.dao.Question;
import com.hellsinner.exam.model.dao.Taskques;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamInfo extends Taskques {
    private Question question;
}
