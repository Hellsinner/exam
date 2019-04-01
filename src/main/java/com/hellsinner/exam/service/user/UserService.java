package com.hellsinner.exam.service.user;

import com.hellsinner.exam.model.dao.Courstudent;
import com.hellsinner.exam.model.dao.User;
import com.hellsinner.exam.model.web.LoginUser;
import com.hellsinner.exam.model.web.OrgForm;
import com.hellsinner.exam.model.web.PassWordForm;
import com.hellsinner.exam.model.web.UserInfo;

import java.util.List;
import java.util.Map;

public interface UserService {
    Map<String, Object> createKaptcha();

    LoginUser login(LoginUser loginUser);

    void register(LoginUser loginUser, OrgForm orgForm);

    User getUser(Integer id);

    void update(User user,OrgForm orgForm);

    UserInfo getInfo();

    void updatePassWord(PassWordForm passWordForm);

    List<UserInfo> batchGetUserInfo(List<Courstudent> ids);
}
