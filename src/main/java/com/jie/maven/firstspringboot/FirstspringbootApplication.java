package com.jie.maven.firstspringboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@MapperScan(basePackages = "com.jie.maven.firstspringboot.mapper")
public class FirstspringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(FirstspringbootApplication.class, args);
    }

}
