package com.hellsinner.exam.model.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamInfo {
    private List<ExamQuesInfo> quesInfos;

    private CourseclasstaskInfo courseclasstaskInfo;
}
