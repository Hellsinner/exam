package com.hellsinner.exam.model.web;

import com.hellsinner.exam.model.dao.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.beanutils.BeanUtils;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUser extends User {
    @NotBlank(groups = {Login.class,Register.class})
    private String pin;
    @NotBlank(groups = {Login.class,Register.class})
    private String token;

    public static User adapterUser(LoginUser loginUser){
        User user = new User();
        try {
            BeanUtils.copyProperties(user, loginUser);
            return user;
        } catch (Exception e) {
            return null;
        }
    }

    public static LoginUser adapterLoginUser(User user){
        LoginUser loginUser = new LoginUser();
        try {
            BeanUtils.copyProperties(loginUser, user);
            return loginUser;
        } catch (Exception e) {
            return null;
        }
    }
}
