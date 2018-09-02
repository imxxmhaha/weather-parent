package cn.xxm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApp9002 {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApp9002.class,args);
    }
}
