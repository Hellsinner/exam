package com.hellsinner.exam.model.web;

import com.hellsinner.exam.model.dao.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.beanutils.BeanUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
    private String comment;

    private Integer userid;

    private Integer usertype;

    private Integer personid;

    private String name;

    private String nickname;

    private String email;

    private String telnum;

    private Integer parentId;

    private String parentName;

    private Integer childId;

    private String childName;

    public static UserInfo adapterUserInfo(User user,OrgForm orgForm) throws Exception{
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(userInfo,user);
        BeanUtils.copyProperties(userInfo,orgForm);
        return userInfo;
    }
}
