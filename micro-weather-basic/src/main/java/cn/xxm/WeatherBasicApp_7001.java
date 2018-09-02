package cn.xxm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@SpringBootApplication
@MapperScan("cn.xxm.dao")
@EnableEurekaClient //本服务启动后会自动注册进eureka服务中
@EnableDiscoveryClient  //服务发现
public class WeatherBasicApp_7001 {

    public static void main(String[] args) {
        SpringApplication.run(WeatherBasicApp_7001.class, args);
    }
}
