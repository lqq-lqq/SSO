package com.sso.login.controller;

import com.sso.login.com.sso.login.utils.LoginCacheUtils;
import com.sso.login.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

//页面跳转逻辑
@Controller
@RequestMapping(value = "/view")
public class ViewController {
    //登陆界面
    @GetMapping(value = "/login")
    public String toLogin(@RequestParam(required = false,defaultValue = "") String target,
                          HttpSession session, @CookieValue(required = false,value = "TOKEN") Cookie cookie){
        if(StringUtils.isEmpty(target)){     //如果到达登陆界面url没有带target参数，则登陆完成后默认回到首页
            target="http://www.lqq.com:8001";  //默认返回主页面
        }
        if(cookie!=null){
            String value=cookie.getValue();
            User user= LoginCacheUtils.loginUser.get(value);
            if(user!=null){    //已经登陆的状态，不需要跳转到登陆界面
                return "redirect:"+target;
            }
        }
        //重定向地址（此处需要做target地址是否合法的校验）
        session.setAttribute("target",target);
        return "index";
    }
}
