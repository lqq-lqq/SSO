package com.sso.login.controller;

import com.sso.login.com.sso.login.utils.LoginCacheUtils;
import com.sso.login.domain.User;
import com.sso.login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

@Controller
@RequestMapping(value = "/login",method = {RequestMethod.POST,RequestMethod.GET})
public class LoginController {
    @Autowired
    private UserRepository userRepository;
    @PostMapping("")
    public String doLogin(HttpServletRequest request,HttpSession session, HttpServletResponse response){   //接受表单参数和重定向地址
        String target=(String) session.getAttribute("target");  //拿到重定向的页面
        //模拟从数据库中通过用户名和密码查找用户
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user = userRepository.findByUsername(username);
        if(user==null){
            session.setAttribute("msg","用户名不存在，请注册");
            return "index";
        }
        else{
            if(password.equals(user.getPassword())){
                String token= UUID.randomUUID().toString();
                Cookie cookie=new Cookie("TOKEN",token);
                cookie.setDomain("lqq.com"); //设置后缀域名
                response.addCookie(cookie);
                LoginCacheUtils.loginUser.put(token,user);
                request.setAttribute("user",user);
            }
            else{
                session.setAttribute("msg","用户名或密码错误");
                return "index";
            }
        }
        return "redirect:"+target;
    }
    @GetMapping("/info")    //请求得到用户信息
    @ResponseBody
    public ResponseEntity<User> getUserInfo(String token){
        if(!StringUtils.isEmpty(token)){
            User user=LoginCacheUtils.loginUser.get(token);
            return ResponseEntity.ok(user);
        }
        else{
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

    }
}
