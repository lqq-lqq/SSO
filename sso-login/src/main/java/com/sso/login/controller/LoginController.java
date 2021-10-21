package com.sso.login.controller;

import com.sso.login.com.sso.login.utils.LoginCacheUtils;
import com.sso.login.com.sso.login.utils.JWTUtils;
import com.sso.login.domain.User;
//import com.sun.deploy.net.HttpResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Controller
@RequestMapping(value = "/login",method = {RequestMethod.POST,RequestMethod.GET})
public class LoginController {
    private static Set<User> dbUsers;
    static {
        dbUsers=new HashSet<>();
        dbUsers.add(new User("zhangsan","123456"));
        dbUsers.add(new User("lisi","123456"));

    }
    @PostMapping("")
    public String doLogin(User user, HttpSession session, HttpServletResponse response){   //接受表单参数和重定向地址
        String target=(String) session.getAttribute("target");  //拿到重定向的页面
        //模拟从数据库中通过用户名和密码查找用户
        Optional<User> person=dbUsers.stream().filter(dbUser->dbUser.getUsername().equals(user.getUsername()) &&
                dbUser.getPassword().equals(user.getPassword())).findFirst();
        if(person.isPresent()){
            //保存用户信息
            //将用户信息存为map为生成token作准备
            Map<String, String> UserInfo=new HashMap<>();
            UserInfo.put("username",user.getUsername());
            UserInfo.put("password",user.getPassword());
            //新建token工具类实体
            JWTUtils JWTtoken=new JWTUtils();
            //生成token
            String token=JWTtoken.getToken(UserInfo);
            System.out.println(token);//打印token,验证token生成成功。

            //String token= UUID.randomUUID().toString();//随机字符串生成token
            Cookie cookie=new Cookie("TOKEN",token);
            cookie.setDomain("lqq.com"); //设置后缀域名
            response.addCookie(cookie);
            LoginCacheUtils.loginUser.put(token,person.get());
        } else {  //登陆失败
            session.setAttribute("msg","用户名或密码错误");
            return "index";
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
