package com.sso.login.controller;

import com.sso.login.domain.User;
import com.sso.login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
//@RequestMapping(value = "/sign-up")
@RequestMapping(value = "/sign-up",method = {RequestMethod.POST,RequestMethod.GET})
public class SignUpController {
    @Autowired
    private UserRepository userRepository;
    @PostMapping("")
    public String toSign(HttpServletRequest request,HttpSession session){
//        String username = request.getParameter("username");
//        String password = request.getParameter("password");
//        System.out.println("aaaaaaaaaa  "+username);
//        System.out.println(password);
        //获取用户和密码
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        //根据昵称查询用户是否存在
        User user = userRepository.findByUsername(username);
        //如果用户名存在
        if(user!=null){
            session.setAttribute("msg1","用户名重复");
            return "sign-up";
        }
        //如果用户名不存在
        User newUser = new User(username,password);
        //注册
        userRepository.save(newUser);
        //将信息设置session作用域
        request.setAttribute("user",newUser);
        return("success");
//        return "redirect:"+"http://login.lqq.com:8000/view/login";
    }
}
