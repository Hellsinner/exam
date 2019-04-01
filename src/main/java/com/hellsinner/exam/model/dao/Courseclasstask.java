package com.hellsinner.exam.model.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 课程班任务
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Courseclasstask {
    private Integer classtaskid;
    /**
     * 任务id
     */
    private Integer taskid;
    /**
     * 课程班id
     */
    private Integer courclassid;
    /**
     * 课程班任务名称
     */
    private String classtaskname;
    /**
     * 课程班任务开始时间
     */
    private Date starttime;
    /**
     * 课程班任务结束时间
     */
    private Date endtime;
    /**
     * 课程班状态
     */
    private Integer status;
}