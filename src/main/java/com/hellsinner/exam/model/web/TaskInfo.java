package com.hellsinner.exam.model.web;

import com.hellsinner.exam.model.dao.Task;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskInfo extends Task {

    private String coursename;
}
