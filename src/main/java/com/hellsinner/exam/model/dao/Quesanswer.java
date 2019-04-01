package com.hellsinner.exam.model.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Quesanswer {
    private Integer answerid;
    /**
     * 任务答案ID
     */
    private Integer taskanswerid;
    /**
     * 任务题目ID
     */
    private Integer taskquesid;
    /**
     * 得分
     */
    private Double score;
    /**
     * 用时(Sec)
     */
    private Integer usedtime;
    /**
     * 学生答案
     */
    private String answer;
}