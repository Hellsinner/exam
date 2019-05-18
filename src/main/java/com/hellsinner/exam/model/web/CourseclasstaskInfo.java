package com.hellsinner.exam.model.web;

import com.hellsinner.exam.model.dao.Courseclasstask;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseclasstaskInfo extends Courseclasstask {
    private Boolean isnoticed;

    private Boolean isexam;

    private String courclassname;

    private String courname;

    private Boolean isallowview;
}
