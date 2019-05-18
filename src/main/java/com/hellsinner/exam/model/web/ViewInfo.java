package com.hellsinner.exam.model.web;

import com.hellsinner.exam.model.dao.Stutaskanswer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewInfo {
    private CourseclasstaskInfo courseclasstaskInfo;

    private Stutaskanswer stutaskanswer;

    private List<QuestionView> questionInfos;
}
