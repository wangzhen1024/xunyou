package com.example.xunyou.controller;


import com.example.xunyou.bean.User;
import com.example.xunyou.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class LoginController {



    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping("/test")
    public String Test(){
        User user= userService.getById(1);
        System.out.println(user.toString());
        return "test";
    }

    @ResponseBody
    @RequestMapping("/getById")
    public String getById(int id){
        User user= userService.getById(id);
        return user.toString();
    }



    @ResponseBody
    @RequestMapping("/login")
    public String login(User user) {
        if (StringUtils.isEmpty(user.getUsername()) || StringUtils.isEmpty(user.getPassword())) {
            return "请输入用户名和密码！";
        }
        //用户认证信息
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
                user.getUsername(),
                user.getPassword()
        );
        try {
            //进行验证，这里可以捕获异常，然后返回对应信息
            subject.login(usernamePasswordToken);
            //subject.checkRole("管理员");
            subject.checkPermissions("测试权限");
        } catch (UnknownAccountException e) {
            log.error("用户名不存在！", e);
            return "用户名不存在！";
        } catch (AuthenticationException e) {
            log.error("账号或密码错误！", e);
            return "账号或密码错误！";
        } catch (AuthorizationException e) {
            log.error("没有权限！", e);
            return "没有权限";
        }
        return "login success";
    }

    @RequiresRoles("admin")
    @RequestMapping("/admin")
    public String admin() {
        return "admin success!";
    }

    @RequiresPermissions("query")
    @RequestMapping("/index")
    public String index() {
        return "index success!";
    }

    @RequiresPermissions("add")
    @RequestMapping("/add")
    public String add() {
        return "add success!";
    }



}
