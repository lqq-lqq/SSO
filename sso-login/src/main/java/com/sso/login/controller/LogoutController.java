package com.sso.login.controller;

import com.sso.login.utils.LoginCacheUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value="/logout")  //退出登录
public class LogoutController {
    @GetMapping("")
    public String loginOut(@CookieValue(value = "TOKEN")Cookie cookie, String target, HttpServletResponse response){
        if (cookie != null) {
            cookie.setMaxAge(0);    //清除cookie
            cookie.setPath("/");
            LoginCacheUtils.loginUser.remove(cookie.getValue());   //清除登陆记录
            cookie.setValue(null);
            Cookie newCookie=new Cookie("TOKEN",null); //假如要删除名称为username的Cookie
            newCookie.setMaxAge(0); //立即删除型
            newCookie.setPath("/"); //项目所有目录均有效，这句很关键，否则不敢保证删除
            newCookie.setDomain("lqq.com");  //一定要设置域名，否则该域名下的cookie不会被覆盖，就无法实现清除
            response.addCookie(newCookie); //重新写入，将覆盖之前的
        }
        return "redirect:"+target;
    }
}
