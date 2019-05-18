package com.hellsinner.exam.model.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionView {
    private QuestionInfo questionInfo;

    private Double point;
}
