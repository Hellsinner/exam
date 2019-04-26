package com.hellsinner.exam.model.web;

import com.hellsinner.exam.model.dao.Knowledgeunit;
import com.hellsinner.exam.model.dao.Taskconstruct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskListInfo {
    private TaskInfo taskInfo;

    private List<PaperrangeInfo> paperranges;

    private List<Taskconstruct> taskconstructs;

    private List<QuestionInfo> questionInfos;

    private List<Knowledgeunit> knowledgeunits;
}
