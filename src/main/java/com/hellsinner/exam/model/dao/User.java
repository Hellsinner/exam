package com.hellsinner.exam.model.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 用户
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer userid;

    /**
     * 机构名称
     */
    @NotBlank(groups = Register.class)
    private String orgname;

    /**
     * 用户类型 0:学生 1:教师
     */
    @NotNull(groups = Register.class)
    private Integer usertype;

    /**
     * 登录账号
     */
    private String loginid;
    /**
     * 密码
     */
    @NotBlank(groups = {Login.class,Register.class})
    private String password;
    /**
     * 学生(学号)/教师(工号)
     */
    private String personid;

    /**
     * 姓名
     */
    @NotBlank(groups = Register.class)
    private String name;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 邮箱
     */
    @NotBlank(groups = {Login.class,Register.class})
    private String email;
    /**
     * 手机号
     */
    private String telnum;

    public interface Login{}
    public interface Register{}
}