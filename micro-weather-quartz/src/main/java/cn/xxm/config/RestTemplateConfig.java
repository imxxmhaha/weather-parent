package cn.xxm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author xxm
 * @create 2018-07-29 23:23
 */
@Configuration
public class RestTemplateConfig {

    @Bean
//    @LoadBalanced //Spring Cloud Ribbon 是基于Netflix Ribbon实现的一套客户端  负载均衡的工具
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
