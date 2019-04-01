package com.hellsinner.exam.model.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 机构
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Org {
    private Integer orgid;
    /**
     * 上级组织机构_组织机构ID
     */
    private Integer orgOrgid;
    /**
     * 组织机构名称
     */
    private String orgname;
    /**
     *机构简称
     */
    private String orgshortname;
}