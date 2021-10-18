package com.sso.login.controller;

import com.sso.login.com.sso.login.utils.LoginCacheUtils;
import com.sso.login.domain.User;
import com.sun.deploy.net.HttpResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Controller
@RequestMapping(value = "/sign-up",method = {RequestMethod.POST,RequestMethod.GET})
public class SignUpController {

}
