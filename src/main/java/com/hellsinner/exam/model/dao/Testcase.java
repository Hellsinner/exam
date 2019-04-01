package com.hellsinner.exam.model.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 测试用例
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Testcase {
    private Integer testcaseid;
    /**
     * 问题id
     */
    private Integer quesid;
    /**
     * 用例序号
     */
    private Integer caseindex;
    /**
     * 输入文件路径
     */
    private String caseinputfilepath;
    /**
     * 输出文件路径
     */
    private String caseoutputfilepath;
    /**
     * 终端输入
     */
    private String casetermiinput1;
    /**
     * 终端输出
     */
    private String casetermioutput;
}