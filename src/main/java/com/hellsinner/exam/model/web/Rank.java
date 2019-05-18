package com.hellsinner.exam.model.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rank implements Serializable {
    private static final long serialVersionUID = 4134408867800896375L;

    private String _id;
    private Integer count;
}
