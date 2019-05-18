package com.hellsinner.exam.model.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

/**
 * 题目
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(collection = "questions")
public class Question implements Serializable {
    private static final long serialVersionUID = -3121911923027807845L;
    @Id
    private String id;
    /**
     * 题目类型
     */
    private String type;
    /**
     * 题目类型编号
     */
    private int typenum;
    /**
     * 用户ID
     */
    private Integer userid;
    /**
     * 课程ID
     */
    private Integer courseId;
    /**
     * 知识点ID
     */
    private Integer unitId;

    private Qdata qdata;

    private String answer;

    private Long submitTotal;

    private String acRate;

    private Boolean isAc;

    private Integer pid;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Qdata{
        private String content;
        private course course;
        private Dataa data;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class course{
        private String courseName;
        private String unitName;
        private Integer courseId;
        private Integer unitId;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Dataa{
        private String analysis;
        private Option option;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Option{
        private String type;
        private String answer;
        private Integer count;
        private String id;
        private List<Odata> odata;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Odata{
        private Integer key;
        private String action;
        private String option;
        private List<String> image;
    }
}