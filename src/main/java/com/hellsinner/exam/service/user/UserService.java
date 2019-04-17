package com.hellsinner.exam.service.user;

import com.hellsinner.exam.model.dao.Courstudent;
import com.hellsinner.exam.model.dao.User;
import com.hellsinner.exam.model.web.LoginUser;
import com.hellsinner.exam.model.web.PassWordForm;

import java.util.List;
import java.util.Map;

public interface UserService {
    Map<String, Object> createKaptcha();

    LoginUser login(LoginUser loginUser);

    void register(LoginUser loginUser);

    User getUser(Integer id);

    void update(User user);

    User getInfo();

    void updatePassWord(PassWordForm passWordForm);

    List<User> batchGetUserInfo(List<Courstudent> ids);
}
