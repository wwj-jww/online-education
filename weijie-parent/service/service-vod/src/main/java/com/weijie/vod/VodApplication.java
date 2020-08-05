package com.weijie.vod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
//公共组件都在这个包下 使用其他模块的东西
@ComponentScan(basePackages = {"com.weijie"})
//注册中心注册
@EnableDiscoveryClient
//不配置数据库
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class VodApplication {
    public static void main(String[] args) {
        SpringApplication.run(VodApplication.class, args);
    }
}
