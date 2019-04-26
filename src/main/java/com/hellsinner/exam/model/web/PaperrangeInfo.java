package com.hellsinner.exam.model.web;

import com.hellsinner.exam.model.dao.Paperrange;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaperrangeInfo extends Paperrange {
    private String unitname;
}
