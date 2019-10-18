package com.sun.common.shiro;

import com.sun.common.dao.UserMapper;
import com.sun.common.entity.Auth;
import com.sun.common.entity.Resource;
import com.sun.common.entity.Role;
import com.sun.common.entity.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.AllowAllCredentialsMatcher;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ShiroRealm extends AuthorizingRealm {

    @Autowired private UserMapper userMapper;
    /**
     *用户授权认证
     *调用时机，在使用Subject中的权限角色验证时，如checkPermission等
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String userName = principalCollection.getPrimaryPrincipal().toString();
        Set<String> authList = new HashSet<>();
        Set<String> roleNameList = new HashSet<>();
        User user = userMapper.queryUserByUserName(userName);
        /*authList = userMapper.queryAuthListByUserName(user.getUserId());*/
        //user = userMapper.queryAuthListByUserName(user.getUserId());
        /*Set<Role> roles = user.getRole();
        for (Role role:roles) {
            Set<Auth> auths = role.getAuth();
            for (Auth auth:auths) {
                authList.add(auth.getAuthName());
            }
            roleNameList.add(role.getRoleName());
        }*/
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermissions(authList);//拿到权限拿到角色
        info.addRoles(roleNameList);//
        return info;
    }
    /**
     *该方法身份认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //获取身份
        /*String username = (String) authenticationToken.getCredentials();*/
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String pwd = new String(token.getPassword());
        String cd = token.getCredentials().toString();
        Object gg = token.getPrincipal();
        String userName = token.getUsername();
        //模拟数据库查询
        User user = userMapper.queryUserByUserName(userName);
        if(user != null){
            SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo( user.getUserName(),
                    user.getPwd(), // 密码
                    ByteSource.Util.bytes(user.getSalt()),
                    getName());
            return authenticationInfo;
        }
        return null;
    }
}
