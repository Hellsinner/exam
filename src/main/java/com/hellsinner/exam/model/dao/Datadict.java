package com.hellsinner.exam.model.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Datadict {
    private Integer dictid;

    private Integer superdictid;

    private Integer dictindex;

    private String value;

    private String name;

    private String description;
}