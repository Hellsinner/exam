package com.hellsinner.exam.model.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 课程班
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Courseclass {
    private Integer courclassid;
    /**
     * 教师id
     */
    private Integer userid;
    /**
     * 课程id
     */
    private Integer courid;
    /**
     * 课程班编号
     */
    private String courclassnum;
    /**
     * 课程班名称
     */
    @NotBlank
    private String courclassname;
    /**
     * 课程班状态
     */
    @NotNull
    private Integer status;
    /**
     * 课程班人数
     */
    private Integer courclasssize;
    /**
     * 课程班描述
     */
    private String courclassdescription;
}