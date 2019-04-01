package com.hellsinner.exam.model.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Questiontype {
    private Integer typeid;

    private String name;

    private String description;

    private Integer judgetag;
}