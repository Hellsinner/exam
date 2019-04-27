package com.hellsinner.exam.model.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskAISelect {
    private List<Integer> unitids;

    private List<QuesSelect> construct;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QuesSelect{
        private String type;
        private Integer count;
    }
}
