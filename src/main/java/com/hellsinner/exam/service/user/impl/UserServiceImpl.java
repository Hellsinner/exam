package com.hellsinner.exam.service.user.impl;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.hellsinner.exam.component.ExamException;
import com.hellsinner.exam.component.UserContext;
import com.hellsinner.exam.dao.UserMapper;
import com.hellsinner.exam.model.dao.Courstudent;
import com.hellsinner.exam.model.dao.Org;
import com.hellsinner.exam.model.dao.User;
import com.hellsinner.exam.model.web.LoginUser;
import com.hellsinner.exam.model.web.OrgForm;
import com.hellsinner.exam.model.web.PassWordForm;
import com.hellsinner.exam.model.web.UserInfo;
import com.hellsinner.exam.service.org.OrgService;
import com.hellsinner.exam.service.user.UserService;
import com.hellsinner.exam.utils.JwtUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private static final String BASE64_PREFIX = "data:image/jpeg;base64,";

    @Value("${salt}")
    private String salt;

    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private OrgService orgService;

    @Autowired
    private UserMapper userMapper;

    @Override
    public Map<String, Object> createKaptcha() {
        // 生成文字验证码
        String text = defaultKaptcha.createText();
        // 生成图片验证码
        ByteArrayOutputStream outputStream = null;
        BufferedImage image = defaultKaptcha.createImage(text);
        Base64.Encoder encoder = Base64.getEncoder();

        outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", outputStream);

            String token = UUID.randomUUID().toString();
            System.out.println("token: "+token);
            System.out.println("text: "+text);
            stringRedisTemplate.opsForValue().set(token,text,10,TimeUnit.MINUTES);

            Map<String, Object> map = new HashMap<>();
            map.put("token", token);
            map.put("img", BASE64_PREFIX + encoder.encodeToString(outputStream.toByteArray()));
            return map;
        } catch (IOException e) {
            throw new ExamException(ExamException.ExamExceptionEnum.KAPTCHA_LOAD_FAILED);
        }
    }

    @Override
    public LoginUser login(LoginUser loginUser) {
        String pin = stringRedisTemplate.opsForValue().get(loginUser.getToken());
        //验证码错误
        if (StringUtils.isEmpty(pin) || !pin.equals(loginUser.getPin())){
            throw new ExamException(ExamException.ExamExceptionEnum.KAPTCHA_INPUT_ERROR);
        }
        User user = userMapper.selectByEmail(loginUser.getEmail());
        //用户名不存在
        if (user == null){
            throw new ExamException(ExamException.ExamExceptionEnum.NOT_FOUND_USER);
        }
        //密码不正确
        if (!DigestUtils.md5Hex(salt+loginUser.getPassword()).equals(user.getPassword())){
            throw new ExamException(ExamException.ExamExceptionEnum.PASSWORD_INPUT_ERROR);
        }
        user.setPassword(null);
        loginUser = LoginUser.adapterLoginUser(user);
        loginUser.setToken(JwtUtils.createPayLoad(user.getUserid()));
        return loginUser;
    }

    @Override
    public void register(LoginUser loginUser,OrgForm orgForm) {
        String pin = stringRedisTemplate.opsForValue().get(loginUser.getToken());
        //验证码错误
        if (!pin.equals(loginUser.getPin())){
            throw new ExamException(ExamException.ExamExceptionEnum.KAPTCHA_INPUT_ERROR);
        }
        //邮箱被注册
        User user = userMapper.selectByEmail(loginUser.getEmail());
        if(user != null){
            throw new ExamException(ExamException.ExamExceptionEnum.EMAIL_HAS_REGISTER);
        }
        loginUser.setOrgid(dealOrg(orgForm));
        loginUser.setPassword(DigestUtils.md5Hex(salt+loginUser.getPassword()));
        userMapper.insert(LoginUser.adapterUser(loginUser));
    }

    private Integer dealOrg(OrgForm orgForm){
        if (orgForm.getParentId() != null){
            Org org = orgService.getOrg(orgForm.getParentId());
            if(org == null)
                throw new ExamException(ExamException.ExamExceptionEnum.SERVER_ERROR);
            if (!org.getOrgname().equals(orgForm.getParentName())){
                Org parent = OrgForm.adapterParentOrg(orgForm);
                orgService.addOrg(parent);
                orgForm.setParentId(parent.getOrgid());
            }
            if (orgForm.getChildId() == null){
                Org childOrg = OrgForm.adapterChildOrg(orgForm);
                orgService.addOrg(childOrg);
                return childOrg.getOrgid();
            }else {
                Org child = orgService.getOrg(orgForm.getChildId());
                if(child == null)
                    throw new ExamException(ExamException.ExamExceptionEnum.SERVER_ERROR);
                if (!child.getOrgname().equals(orgForm.getChildName())){
                    Org childOrg = OrgForm.adapterChildOrg(orgForm);
                    orgService.addOrg(childOrg);
                    return childOrg.getOrgid();
                }
                return orgForm.getChildId();
            }
        }else {
            Org parent = OrgForm.adapterParentOrg(orgForm);
            orgService.addOrg(parent);
            orgForm.setParentId(parent.getOrgid());

            Org childOrg = OrgForm.adapterChildOrg(orgForm);
            orgService.addOrg(childOrg);
            return childOrg.getOrgid();
        }
    }

    @Override
    public User getUser(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public void update(User user,OrgForm orgForm) {
        user.setOrgid(dealOrg(orgForm));
        user.setUserid(UserContext.getUid());
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public UserInfo getInfo() {
        User user = this.getUser(UserContext.getUid());

        OrgForm userOrg = orgService.getUserOrg(user.getOrgid());

        try {
            return UserInfo.adapterUserInfo(user, userOrg);
        } catch (Exception e) {
            throw new ExamException(ExamException.ExamExceptionEnum.SERVER_ERROR);
        }
    }

    @Override
    public void updatePassWord(PassWordForm passWordForm) {
        User user = this.getUser(UserContext.getUid());
        //密码输入错误
        if (!user.getPassword().equals(DigestUtils.md5Hex(salt+passWordForm.getOldPass()))){
            throw new ExamException(ExamException.ExamExceptionEnum.PASSWORD_INPUT_ERROR);
        }
        //新旧密码相同
        if (user.getPassword().equals(DigestUtils.md5Hex(salt+passWordForm.getNewPass()))){
            throw new ExamException(ExamException.ExamExceptionEnum.OLDPASS_SAME_NEWPASS);
        }
        user.setPassword(DigestUtils.md5Hex(salt+passWordForm.getNewPass()));
        userMapper.updateByPrimaryKey(user);
    }

    @Override
    public List<UserInfo> batchGetUserInfo(List<Courstudent> classStudents) {
        List<Integer> ids = classStudents
                .stream()
                .map(courstudent -> courstudent.getUserid())
                .collect(Collectors.toList());

        List<User> users = userMapper.selectByIds(ids);

        List<Integer> orgids = users.stream()
                .map(User::getOrgid)
                .collect(Collectors.toList());

        List<OrgForm> orgForms = orgService.batchgetOrg(orgids);
        List<UserInfo> userInfos = new ArrayList<>();
        for (int i=0;i<users.size();i++){
            try {
                UserInfo userInfo = UserInfo.adapterUserInfo(users.get(i), orgForms.get(i));
                userInfo.setComment(classStudents.get(i).getComment());
                userInfos.add(userInfo);
            } catch (Exception e) {
                throw new ExamException(ExamException.ExamExceptionEnum.SERVER_ERROR);
            }
        }
        return userInfos;
    }
}
