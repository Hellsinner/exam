package com.hellsinner.exam.model.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 考试范围
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Paperrange {
    private Integer paperrangeid;
    /**
     * 任务ID
     */
    private Integer taskid;
    /**
     * 知识点ID
     */
    private Integer unitid;
    /**
     * 分值
     */
    private Integer points;
}