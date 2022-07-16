package com.donggei.controller;

import com.donggei.annotation.SystemLog;
import com.donggei.domain.ResponseResult;

import com.donggei.domain.vo.LoginFormVo;
import com.donggei.enums.AppHttpCodeEnum;
import com.donggei.exception.SystemException;
import com.donggei.service.BlogLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "博客页面登录")
@RestController
@RequestMapping
public class BlogLoginController {
    @Autowired
    private BlogLoginService blogLoginService;
//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @ApiOperation(value = "博客登录")
    @ApiImplicitParam(name = "user",value = "用户登录信息",required = true,dataType = "LoginVo")
    @PostMapping("/login")
    @SystemLog(businessName = "博客登录")
    public ResponseResult login(@RequestBody LoginFormVo user){


        if(!StringUtils.hasText(user.getUserName())) {
            //提示要传用名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        if(!StringUtils.hasText(user.getPassword())) {
            //提示要传密码
            throw new SystemException(AppHttpCodeEnum.REQUIRE_PASSWORD);
        }
//        System.out.println(passwordEncoder.encode("1234"));
//        System.out.println(AppHttpCodeEnum.SUCCESS);
        return blogLoginService.login(user);
    }
    @ApiOperation(value = "退出登录")
    @SystemLog(businessName = "退出登录")
    @PostMapping("/logout")
    public ResponseResult logout(){
        return blogLoginService.logout();
    }

}
