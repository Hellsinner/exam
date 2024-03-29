package com.hellsinner.exam.model.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 任务题目
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Taskques {
    private Integer taskquesid;
    /**
     * 任务id
     */
    private Integer taskid;
    /**
     * 问题id
     */
    private String quesid;
    /**
     * 分值
     */
    private Double point;
    /**
     * 补充说明
     */
    private String description;

    public static Taskques adapt(String quesid,Integer tid,Double point){
        Taskques taskques = new Taskques();
        taskques.setTaskid(tid);
        taskques.setQuesid(quesid);
        taskques.setPoint(point);
        return taskques;
    }
}