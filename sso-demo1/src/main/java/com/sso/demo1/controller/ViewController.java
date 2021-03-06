package com.sso.demo1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping(value = "/view")
public class ViewController {

    @Autowired
    private RestTemplate restTemplate;
    private final String LOGIN_INFO_ADDRESS="http://login.lqq.com:8000/info?token="; //根据自身本地映射更改

    @GetMapping(value = "/index")
    public String toIndex(@CookieValue(required = false, value = "TOKEN") Cookie cookie, HttpSession session) {
        if (cookie != null) {
            String token = cookie.getValue();
            if (!StringUtils.isEmpty(token)) { //向login寻找用户信息的url请求
                Map result = restTemplate.getForObject(LOGIN_INFO_ADDRESS + token, Map.class);
                session.setAttribute("loginUser",result);
                if(result==null){
                    return "redirect:"+"http://login.lqq.com:8000/view/login?target=http://demo1.lqq.com:8002/view/index";
                }
            }
        }
        return "demo1";
    }
}
