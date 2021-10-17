package com.sso.demo2;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Demo2App {
    public static void main(String[] args) {
        SpringApplication.run(Demo2App.class,args);
    }
    @Bean
    public RestTemplate restTemplate(){   //不知道干啥
        return new RestTemplate();
    }
}

