package com.example.xunyou.controller;


import com.example.xunyou.aop.WebLog;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LoginController {

    public final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;


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
            subject.checkPermissions("测试权限:*");
        } catch (UnknownAccountException e) {
            logger.error("用户名不存在！", e);
            return "用户名不存在！";
        } catch (AuthenticationException e) {
            logger.error("账号或密码错误！", e);
            return "账号或密码错误！";
        } catch (AuthorizationException e) {
            logger.error("没有权限！", e);
            return "没有权限";
        }
        return "login success";
    }


    @RequiresRoles("管理员")
    @RequestMapping("/admin")
    public String admin() {
        return "admin success! 管理员";
    }

    @RequiresPermissions("测试权限")
    @RequestMapping("/test")
    @WebLog(description = "mapping-test-日志测试")
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

}
