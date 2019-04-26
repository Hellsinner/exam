package com.hellsinner.exam.model.web;

import com.hellsinner.exam.model.dao.Question;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionResult {
    private long count;

    private List<Question> questions;
}
