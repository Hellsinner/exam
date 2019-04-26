package com.hellsinner.exam.model.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 任务题型构成
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Taskconstruct {
    private Integer strategyid;
    /**
     * 任务id
     */
    private Integer taskid;
    /**
     * 题目类型ID
     * @see com.hellsinner.exam.model.enums.QuestionType
     */
    private String typeid;
    /**
     * 试题数量
     */
    private Integer quescount;
    /**
     * 题型总分
     */
    private Integer typepoints;
    /**
     * 策略名称
     */
    private String strategyname;
}