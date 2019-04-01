package com.hellsinner.exam.model.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 知识点
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Knowledgeunit {
    private Integer unitid;
    /**
     * 上级知识点
     */
    private Integer superunitid;
    /**
     * 课程ID
     */
    private Integer courid;
    /**
     * 名称
     */
    private String unitname;
    /**
     * 说明
     */
    private String description;
}