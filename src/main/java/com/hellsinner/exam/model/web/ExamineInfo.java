package com.hellsinner.exam.model.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamineInfo {
    private Integer classtaskid;

    private String classtaskname;

    private String courclassname;

    private Integer userid;

    private Double totalscore;

    private String name;

    private Integer status;
}
