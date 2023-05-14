package com.template.micro.client.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.template.micro.client.entity.User;
import com.template.micro.client.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utils.vo.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@Api(tags = {"[管理端-登录], tags = [标签管理], description = [标签管理]"})
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private IUserService iUserService;

    @ApiOperation(value = "用户登录")
    @PostMapping(value = "/userLogin")
    public Result<String> login(HttpServletRequest request, @RequestBody User user) {
        // 判断是否有这个用户
        Long num = iUserService.count(new QueryWrapper<User>().lambda().eq(User::getLogin,user.getLogin()));
        System.out.println(num);
        HttpSession session = request.getSession();
        if(num>0){
            // 如果存在，将用户和密码加密放到session中
            session.setAttribute("token",user.getLogin()+user.getPassword());//设置token,参数token是要设置的具体值
            System.out.println("token:"+session.getAttribute("token"));
            return new Result<String>().success("success");
        }else{
            return new Result<String>().code(204).failure("invalid user");
        }
    }

    @ApiOperation(value = "访问其他接口，权限验证")
    @GetMapping(value = "/home")
    public String home(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String token = (String) session.getAttribute("token");
        System.out.println("token:"+token);
        if(("adminadmin").equals(token)){
            System.out.println("用户已经登录，可以正常访问");
        }else{
            response.setStatus(204);
            System.out.println("用户没有登录，请重新登录");
        }
        System.out.println("status:"+response.getStatus());
        return "success";
    }
}
