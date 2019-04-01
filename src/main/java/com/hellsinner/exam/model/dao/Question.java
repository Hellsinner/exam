package com.hellsinner.exam.model.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * 题目
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "questions")
public class Question implements Serializable {
    private static final long serialVersionUID = -3121911923027807845L;

    private Integer quesid;
    /**
     * 题目类型编号
     * @see com.hellsinner.exam.model.enums.QuestionType
     */
    private Integer typeid;
    /**
     * 用户ID
     */
    private Integer userid;
    /**
     * 知识点ID
     */
    private Integer unitid;
    /**
     * 题目名称
     */
    private String name;
    /**
     * 选用次数
     */
    private Integer seletedtimes;
    /**
     * 内存上限(KB)
     */
    private Integer memlimit;
    /**
     * 时间上限(Sec)
     */
    private Integer timelimit;
    /**
     * 题目状态
     */
    private Integer status;
    /**
     * 题目内容
     */
    private String content;
    /**
     * 输入示例
     */
    private String inputexam;
    /**
     * 输出示例
     */
    private String outexam;
    /**
     * 题目答案
     */
    private String answer;
}