package com.hellsinner.exam;

import org.joda.time.DateTime;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


@SpringBootApplication
@MapperScan("com.hellsinner.exam.dao")
@RestController
public class ExamApplication {

    public static void main(String[] args) {
        //System.out.println(DigestUtils.md5Hex("qwertyuiopasdfghjklzxcvbnm"+"1234567"));
        DateTime dateTime = DateTime.now().withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0);
        System.out.println(dateTime);
        SpringApplication.run(ExamApplication.class, args);
//        try {
//            generator();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    public static void generator() throws Exception{
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        //项目根路径不要有中文,我的有中文,所以使用绝对路径
        File configFile = new File("D:\\IdeaProjects\\exam\\src\\main\\resources\\gener.xml");
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
    }
}
