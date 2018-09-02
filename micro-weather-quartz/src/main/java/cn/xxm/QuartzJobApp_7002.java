package cn.xxm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
@EnableEurekaClient     //本服务启动后会自动注册进eureka服务中
@EnableDiscoveryClient  //服务发现
public class QuartzJobApp_7002 {

    public static void main(String[] args) {
        SpringApplication.run(QuartzJobApp_7002.class, args);
    }
}
