package com.sun.control.web.controller;

import com.sun.common.core.Result;
import com.sun.common.dao.RoleMapper;
import com.sun.common.dao.UserMapper;
import com.sun.common.entity.Auth;
import com.sun.common.entity.Resource;
import com.sun.common.entity.Role;
import com.sun.common.entity.User;
import com.sun.common.utils.ResultUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Security;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class LoginController {

    @Autowired private UserMapper userMapper;
    @Autowired private RoleMapper roleMapper;
    @RequestMapping("/login")
    public String login(){
        return "/login";
    }

    @RequestMapping("/submitLoginForm")
    @ResponseBody
    public Result submitLoginForm(String userName, String password){
        List<Role> roles = new ArrayList<>();
        List<Auth> auths = new ArrayList<>();
        List<Resource> resource = new ArrayList<>();
        try {
            UsernamePasswordToken usernamePasswordToken=new UsernamePasswordToken(userName,password);
            Subject currentUser = SecurityUtils.getSubject();//获取当前用户信息
            currentUser.login(usernamePasswordToken);//登陆验证

            User user = roleMapper.queryUserByUserName(userName);
            roles = user.getRole();
            for (Role role:roles) {
                auths = role.getAuth();
                for (Auth auth:auths) {
                    resource = auth.getResource();
                }
            }
            //获取session
            Session session = SecurityUtils.getSubject().getSession();
            session.setAttribute("userInfo", user);
        } catch (Exception e) {
            e.printStackTrace();
            ResultUtil.error("用户或密码错误！！！");
        }
        return ResultUtil.success(resource);
    }

    @RequestMapping("/menuList")
    @ResponseBody
    public Result menuList(){
        List<Role> roles = new ArrayList<>();
        List<Auth> auths = new ArrayList<>();
        List<Resource> resource = new ArrayList<>();
        //获取已登录的用户信息
        Session session = SecurityUtils.getSubject().getSession();
        User user1 = (User)session.getAttribute("userInfo");
        try {
            User user = roleMapper.queryUserByUserName(user1.getUserName());
            roles = user.getRole();
            for (Role role:roles) {
                auths = role.getAuth();
                for (Auth auth:auths) {
                    resource = auth.getResource();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            ResultUtil.error("用户或密码错误！！！");
        }
        return ResultUtil.success(resource);
    }
}
