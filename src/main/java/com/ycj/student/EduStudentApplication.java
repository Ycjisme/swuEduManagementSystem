package com.ycj.student;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author yangchengju
 */
@SpringBootApplication
@EnableWebMvc
@MapperScan("com.ycj.student.mapper")
public class EduStudentApplication {
    /**
     * 可以通过EduStudentApplication.applicationContext.getBean()获取bean
     * */
    public static ConfigurableApplicationContext applicationContext;

    public static void main(String[] args) {
        applicationContext = SpringApplication.run(EduStudentApplication.class, args);
    }

}
