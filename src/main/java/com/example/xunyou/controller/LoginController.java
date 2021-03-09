package com.example.xunyou.controller;


import com.example.xunyou.aop.WebLog;
import com.example.xunyou.bean.User;
import com.example.xunyou.common.Result;
import com.example.xunyou.service.UserService;
import com.example.xunyou.service.impl.UserServiceImpl;
import com.example.xunyou.tool.ProxyInvocationHandle;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class LoginController {

    public final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;


    @ResponseBody
    @RequestMapping("/login")
    public Result login(User user) {
        if (StringUtils.isEmpty(user.getUsername()) || StringUtils.isEmpty(user.getPassword())) {
            return Result.fail();
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
            //subject.checkPermissions("测试权限:*");
        } catch (UnknownAccountException e) {
            logger.error("用户名不存在！", e);
            return Result.fail(300,"账号信息错误");
        } catch (AuthenticationException e) {
            logger.error("账号或密码错误！", e);
            return Result.fail(300,"账号信息错误");
        } catch (AuthorizationException e) {
            logger.error("没有权限！", e);
            return Result.fail(600,"权限缺失");
        }
        return Result.succeed("index.html");
    }


    @RequestMapping("/403")
    public String _403() {
        return "403.html";
    }

    @RequiresRoles("管理员")
    @RequestMapping("/admin")
    public String admin() {
        return "admin success! 管理员";
    }

    @ResponseBody
    @RequiresPermissions("测试权限:update")
    @RequestMapping("/test")
    //@WebLog(description = "/test")
    public String index() {
        return "index success! 测试权限";
    }

    @RequiresRoles("会员")
    @RequestMapping("/huiyuan")
    public String add() {
        return "add success! 会员";
    }

    //被踢出后跳转的页面
    @ResponseBody
    @RequestMapping(value = "/land")
    public String kickOut() {
        return "kickout";
    }

    @RequestMapping("/testProxy")
    //@WebLog(description = "/test")
    public String testProxy() {

        UserServiceImpl userService = new UserServiceImpl();
        ProxyInvocationHandle px=new ProxyInvocationHandle();
        px.setTarget(userService);
        UserService proxy = (UserService) px.getProxy();
        proxy.test("test-----");
        return "testProxy";
    }




}
