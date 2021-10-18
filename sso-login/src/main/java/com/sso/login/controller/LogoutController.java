package com.sso.login.controller;

import com.sso.login.com.sso.login.utils.LoginCacheUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;

@Controller
@RequestMapping(value="/logout")  //退出登录
public class LogoutController {
    @GetMapping("")
    public String loginOut(@CookieValue(value = "TOKEN")Cookie cookie, String target){
        cookie.setMaxAge(0);    //清除cookie
        LoginCacheUtils.loginUser.remove(cookie.getValue());   //清除登陆记录
        return "redirect:"+target;
    }
}
