package com.micro.manage;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import swagger.annotation.EnableBaseSwagger2;

@SpringBootApplication(exclude = {DruidDataSourceAutoConfigure.class, DataSourceAutoConfiguration.class})
//@EnableSwagger2
@EnableBaseSwagger2
@MapperScan("com.micro.manage.mapper")
@EnableFeignClients(basePackages = {"api"})
public class DataSystemManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataSystemManageApplication.class,args);
    }

}
