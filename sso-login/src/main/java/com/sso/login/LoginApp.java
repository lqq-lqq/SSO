package com.sso.login;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class LoginApp {     //启动类
    public static void main(String[] args) {
        SpringApplication.run(LoginApp.class,args);
    }
}
