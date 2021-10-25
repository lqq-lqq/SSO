package com.sso.login.controller;

import com.sso.login.utils.LoginCacheUtils;
import com.sso.login.utils.JWTUtils;
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
import java.util.Map;
import java.util.HashMap;

@Controller
@RequestMapping(value = "",method = {RequestMethod.POST,RequestMethod.GET})
public class LoginController {
    @Autowired
    private UserRepository userRepository;
    JWTUtils JWTtoken = new JWTUtils();
    @PostMapping("/login")
    public String doLogin(HttpServletRequest request,HttpSession session, HttpServletResponse response){   //接受表单参数和重定向地址
        String target=(String) session.getAttribute("target");  //拿到重定向的页面
        //从数据库中通过用户名和密码查找用户
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user = userRepository.findByUsername(username);
        if(user==null){
            session.setAttribute("msg","用户名不存在，请注册");
            return "index";
        }
        else{
            if(password.equals(user.getPassword())){
                
                /**保存用户信息**/
                //将用户信息存为map为生成token作准备
                Map<String, String> UserInfo = new HashMap<>();
                UserInfo.put("username", user.getUsername());
                UserInfo.put("password", user.getPassword());
                /**
                 * 生成token
                 */
                String token = JWTtoken.getToken(UserInfo);
                System.out.println(token);//打印token,验证token生成成功。

                /**
                 * 生成cookie
                 */
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
            //验证token的有效性
            if(JWTtoken.verify(token)){
                //有效则将user信息返回给响应体
                User user=LoginCacheUtils.loginUser.get(token);
                return ResponseEntity.ok(user);
            }
            else{
                //无效则将user信息置为无效，让其他界面重新登录
                LoginCacheUtils.loginUser.remove(token);
                //如何删token
                return new ResponseEntity<>(null,HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
}
