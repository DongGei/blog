package com.donggei;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @className: BlogApplication
 * @description: TODO 类描述
 * @author: Dong
 * @date: 2022/7/9
 **/
@MapperScan("com.donggei.mapper")
@SpringBootApplication
public class BlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class,args);
    }

}
