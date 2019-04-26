package com.hellsinner.exam.model.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionSelect {
    private Integer courseid;

    private Integer unitid;

    private String type;

    private boolean ismyexms;
}
