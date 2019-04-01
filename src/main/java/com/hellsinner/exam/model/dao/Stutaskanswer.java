package com.hellsinner.exam.model.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 学生任务答案
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stutaskanswer {
    private Integer taskanswerid;
    /**
     * 用户ID
     */
    private Integer userid;
    /**
     * 课程班任务ID
     */
    private Integer classtaskid;
    /**
     * 提交时间
     */
    private Date submittime;
    /**
     * 任务答案状态
     */
    private Integer status;
    /**
     * 任务总成绩
     */
    private Double totalscore;
}