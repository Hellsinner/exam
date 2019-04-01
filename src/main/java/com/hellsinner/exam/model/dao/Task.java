package com.hellsinner.exam.model.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 任务
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    private Integer taskid;
    /**
     * 任务名称
     */
    private String taskname;
    /**
     * 用户ID
     */
    private Integer userid;
    /**
     * 课程ID
     */
    private Integer courid;
    /**
     * 任务分数
     */
    private Integer taskscore;
    /**
     * 任务说明
     */
    private String taskdescription;
    /**
     * 任务状态
     */
    private Integer status;
}