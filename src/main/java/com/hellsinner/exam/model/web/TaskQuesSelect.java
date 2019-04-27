package com.hellsinner.exam.model.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskQuesSelect {
    private Integer page;

    private Integer pageSize;

    private List<Integer> unitids;

    private String type;

    private boolean ismyexms;
}
