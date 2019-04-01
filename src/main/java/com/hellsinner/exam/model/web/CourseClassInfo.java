package com.hellsinner.exam.model.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseClassInfo {
    private Integer courclassid;

    private String creator;

    private String courclassname;

    private Integer status;

    private Integer courclasssize;

    private String courclassdescription;
}
