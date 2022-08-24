package com.atguigu.ucenterservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"com.atguigu"})
@SpringBootApplication
@MapperScan("com.atguigu.ucenterservice.mapper")
public class ServiceUcApplication {
  public static void main(String[] args) {
    SpringApplication.run(ServiceUcApplication.class, args);
  }
}
