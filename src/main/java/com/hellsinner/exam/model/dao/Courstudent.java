package com.hellsinner.exam.model.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 课程学生
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Courstudent {
    private Integer selcourid;
    /**
     * 学生id
     */
    private Integer userid;
    /**
     * 课程班id
     */
    private Integer courclassid;
    /**
     * 备注
     */
    private String comment;
}