package com.donggei.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.donggei.domain.entity.LoginUser;
import com.donggei.domain.entity.User;

import com.donggei.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名查询用户信息
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(User::getUserName,username);
        User user = userMapper.selectOne(queryWrapper);
        //判断是否查到用户  如果没查到抛出异常
        if(Objects.isNull(user)){
            throw new RuntimeException("用户不存在");
        }
        LoginUser loginUser = new LoginUser();
        //返回用户信息 登录还没成功
        // TODO 查询权限信息封装
//        loginUser.setUser(user);
        //该方法返回后SpringSecurity会自动通过PasswordEncoder对比UserDetails（LoginUser）中的密码和Authentication的秘密校验,
        //密码正确的话会把UserDetails（LoginUser）的权限信息设置到Authentication对象中
        return new LoginUser(user);
    }
}
