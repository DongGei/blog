package com.donggei.service.Impl;

import com.donggei.domain.ResponseResult;
import com.donggei.domain.entity.LoginUser;
import com.donggei.domain.vo.BlogUserLoginVo;
import com.donggei.domain.vo.LoginFormVo;
import com.donggei.domain.vo.UserInfoVo;
import com.donggei.service.LoginService;
import com.donggei.utils.BeanCopyUtils;
import com.donggei.utils.JwtUtil;
import com.donggei.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

/**
 * @className: LoginServiceImpl
 * @description: TODO 类描述
 * @author: Dong
 * @date: 2022/7/31
 **/
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;
    @Override
    public ResponseResult login(LoginFormVo user) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if(Objects.isNull(authenticate)) {
            throw new RuntimeException("用户名或密码错误！");
        }
        //获取userid 生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        //把用户信息存入redis
        redisCache.setCacheObject("login:"+userId,loginUser);

        HashMap<String, String> map = new HashMap<>();
        map.put("token",jwt);
        return ResponseResult.okResult(map);
    }



    @Override
    public ResponseResult logout() {
        //获取token 解析获取userid
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        //获取userid
        Long userId = loginUser.getUser().getId();
        //删除redis中的用户信息
        redisCache.deleteObject("login:"+userId);
        return ResponseResult.okResult(200,"退出成功");
    }
}
