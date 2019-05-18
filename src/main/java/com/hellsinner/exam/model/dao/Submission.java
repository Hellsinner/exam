package com.hellsinner.exam.model.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "submissions")
public class Submission {
    @Id
    private String id;

    private String quesid;

    private String language;

    private Integer result;

    private Integer user_id;

    private StaticInfo statistic_info;

    private String code;

    private Info info;

    private String username;

    private String submitTime;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StaticInfo{
        private Integer score;

        private Integer memory_cost;

        private Integer time_cost;

        private String err_info;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Info{
        private String err;

        private List<IData> data;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class IData{
        private Integer error;

        private Integer score;

        private Integer memory;

        private Integer result;
    }

}
