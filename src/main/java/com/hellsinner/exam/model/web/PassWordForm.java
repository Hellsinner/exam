package com.hellsinner.exam.model.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PassWordForm {
    @NotBlank
    private String oldPass;
    @NotBlank
    private String newPass;
}
