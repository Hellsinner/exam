package com.hellsinner.exam.model.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 课程实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    private Integer courid;
    /**
     * 课程名称
     */
    private String courname;
    /**
     * 课程描述
     */
    private String courdescription;
    /**
     * 课程状态
     */
    private Integer status;
}