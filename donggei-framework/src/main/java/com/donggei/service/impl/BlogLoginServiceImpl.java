package com.donggei.service.impl;

import com.donggei.domain.ResponseResult;
import com.donggei.domain.entity.LoginUser;
import com.donggei.domain.entity.User;
import com.donggei.domain.vo.BlogUserLoginVo;
import com.donggei.domain.vo.UserInfoVo;
import com.donggei.service.BlogLoginService;
import com.donggei.utils.BeanCopyUtils;
import com.donggei.utils.JwtUtil;
import com.donggei.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @className: BlogLoginServiceImpl
 * @description: TODO 类描述
 * @author: Dong
 * @date: 2022/7/11
 **/
@Service
public class BlogLoginServiceImpl implements BlogLoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;
    @Override
    public ResponseResult login(User user) {
        //UsernamePasswordAuthenticationToken 他实现了Authentication接口
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //判断是否认证通过
        if (Objects.isNull(authenticate)){
            throw new RuntimeException("密码错误");
        }
        //获取usreid 生成token
        LoginUser loginUser = (LoginUser)authenticate.getPrincipal();
        Long id = loginUser.getUser().getId();
        String jwt = JwtUtil.createJWT(String.valueOf(id));
        //用户信息存入redis
        redisCache.setCacheObject("bloglogin:"+id,loginUser);
        //token 和 用户info封装返回
            //转换成Vo
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        BlogUserLoginVo vo = new BlogUserLoginVo(jwt,userInfoVo);
        return ResponseResult.okResult(vo);
    }
}
