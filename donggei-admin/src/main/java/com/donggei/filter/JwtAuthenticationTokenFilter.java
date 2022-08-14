package com.donggei.filter;

import com.alibaba.fastjson.JSON;
import com.donggei.domain.ResponseResult;
import com.donggei.domain.entity.LoginUser;
import com.donggei.enums.AppHttpCodeEnum;
import com.donggei.utils.JwtUtil;
import com.donggei.utils.RedisCache;
import com.donggei.utils.WebUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @className: JwtAuthenticationTokenFilter
 * @description: TODO 类描述
 * @author: Dong
 * @date: 2022/7/30
 **/
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    RedisCache redisCache;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //获取请求头中的token
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)){
            //没有token 可能该接口不需要token 直接放行
            //如果后面访问的资源需要认证 因为没在SecurityContextHolder中放东西，也不会成功访问的
            filterChain.doFilter(request,response);
            return;
        }
        //解析获取usreId
        Claims claims = null;
        try {
            claims = JwtUtil.parseJWT(token);
        } catch (Exception e) {
            e.printStackTrace();
            //token超时，或者 token非法（前端发送篡改过了）
            //不能抛异常 统一的异常处理 是针对controller层的
            ResponseResult responseResult = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(responseResult));
            return;
        }
        String userId = claims.getSubject(); //获取token里真正的那个值
        //从redis中获取用户信息
        LoginUser loginUser =redisCache.getCacheObject("login:" + userId);
        if (Objects.isNull(loginUser)){
            //没拿到 ,说明redis过期
            ResponseResult responseResult = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response,JSON.toJSONString(responseResult));
            return;
        }
        //redis中有存入SecurityContextHolder
        UsernamePasswordAuthenticationToken authenticationToken =new UsernamePasswordAuthenticationToken(loginUser,null,null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request,response);
    }
}
