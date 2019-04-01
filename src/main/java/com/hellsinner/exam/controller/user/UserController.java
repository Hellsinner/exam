package com.hellsinner.exam.controller.user;

import com.hellsinner.exam.component.ExamException;
import com.hellsinner.exam.model.annocations.Authorize;
import com.hellsinner.exam.model.dao.User;
import com.hellsinner.exam.model.web.*;
import com.hellsinner.exam.service.user.UserService;
import com.hellsinner.exam.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/token/{id}")
    public String token(@PathVariable Integer id){
        return JwtUtils.createPayLoad(id);
    }

    @GetMapping(value = "/kaptcha")
    public Result kaptcha(){
        Map<String, Object> kaptcha = userService.createKaptcha();
        System.out.println(kaptcha);
        return Result.ok(kaptcha);
    }

    @PostMapping("/user/login")
    public Result login(@Validated(User.Login.class) LoginUser loginUser, BindingResult result){
        if (result.hasErrors()){
            return Result.failed(ExamException.ExamExceptionEnum.LOGIN_PARAMS_Insufficient);
        }
        LoginUser user = userService.login(loginUser);
        return Result.ok(user);
    }

    @PostMapping("/user/register")
    public Result register(@Validated(User.Register.class) LoginUser loginUser,
                           @Valid OrgForm orgForm, BindingResult result){
        if (result.hasErrors()){
            return Result.failed(ExamException.ExamExceptionEnum.REGISTER_PARAMS_Insufficient);
        }
        userService.register(loginUser,orgForm);
        return Result.ok();
    }

    @GetMapping("/user/info")
    @Authorize
    public Result info() {
        UserInfo userInfo = userService.getInfo();
        return Result.ok(userInfo);
    }

    @PostMapping("/user/update/info")
    @Authorize
    public Result updateInfo(@Valid User user,@Valid OrgForm orgForm){
        userService.update(user,orgForm);
        return Result.ok();
    }

    @PostMapping("/user/update/password")
    @Authorize
    public Result updatePassword(@Valid PassWordForm passWordForm,BindingResult result){
        if(result.hasErrors()){
            return Result.failed(ExamException.ExamExceptionEnum.PASSWORD_IS_BLANK);
        }
        userService.updatePassWord(passWordForm);
        return Result.ok();
    }
}