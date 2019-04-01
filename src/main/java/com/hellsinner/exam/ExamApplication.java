package com.hellsinner.exam;

import com.hellsinner.exam.component.UserContext;
import com.hellsinner.exam.dao.UserMapper;
import com.hellsinner.exam.model.annocations.Authorize;
import com.hellsinner.exam.model.dao.User;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@MapperScan("com.hellsinner.exam.dao")
@RestController
public class ExamApplication {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/index0/{id}")
    public User index(@PathVariable int id){
        return userMapper.selectByPrimaryKey(id);
    }

    @GetMapping("/index1")
    @Authorize
    public User index1(){
        return userMapper.selectByPrimaryKey(UserContext.getUid());
    }

    @GetMapping("/index2/{id}")
    @Authorize(value = 0)
    public User index2(@PathVariable int id){
        return userMapper.selectByPrimaryKey(id);
    }

    @GetMapping("/index3/{id}")
    @Authorize(value = 1)
    public User index3(@PathVariable int id){
        return userMapper.selectByPrimaryKey(id);
    }

    public static void main(String[] args) {
        //System.out.println(DigestUtils.md5Hex("qwertyuiopasdfghjklzxcvbnm"+"1234567"));
        SpringApplication.run(ExamApplication.class, args);
    }
}
