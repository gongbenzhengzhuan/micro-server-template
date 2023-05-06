package com.template.micro.client.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.template.micro.client.entity.User;
import com.template.micro.client.service.IUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private IUserService iUserService;

    @ApiOperation(value = "用户登录")
    @GetMapping(value = "/userLogin")
    public String login(HttpServletRequest request,@RequestParam("username") String username, @RequestParam("password") String password) {
        // 判断是否有这个用户
        Long num = iUserService.count(new QueryWrapper<User>().lambda().eq(User::getLogin,username));
        System.out.println(num);
        HttpSession session = request.getSession();
        if(num>0){
            // 如果存在，将用户和密码加密放到session中
            session.setAttribute("token",username+password);//设置token,参数token是要设置的具体值
        }
        System.out.println("token:"+session.getAttribute("token"));
        return "success";
    }

    @ApiOperation(value = "访问其他接口，权限验证")
    @GetMapping(value = "/home")
    public String home(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String token = (String) session.getAttribute("token");
        System.out.println("token:"+token);
        if(token.equals("adminadmin")){
            System.out.println("用户已经登录，可以正常访问");
        }else{
            System.out.println("用户没有登录，请重新登录");
        }
        return "success";
    }
}
