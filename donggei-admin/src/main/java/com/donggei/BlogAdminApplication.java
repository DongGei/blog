package com.donggei;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @className: BlogApplication
 * @description: TODO 类描述
 * @author: Dong
 * @date: 2022/7/9
 **/
@MapperScan("com.donggei.mapper")
@SpringBootApplication
@EnableScheduling
public class BlogAdminApplication {
    public static void main(String[] args) {
        String s = new String("sdsa");
        SpringApplication.run(BlogAdminApplication.class, args);
    }
}
