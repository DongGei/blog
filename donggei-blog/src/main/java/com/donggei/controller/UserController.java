package com.donggei.controller;

import com.donggei.annotation.SystemLog;
import com.donggei.domain.ResponseResult;
import com.donggei.domain.vo.UserInfoVo;
import com.donggei.domain.vo.UserRegisterVo;
import com.donggei.service.UserService;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Api(tags = "用户管理")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "获取用户信息")
    @SystemLog(businessName = "获取用户信息")
    @GetMapping("/userInfo")
    public ResponseResult userInfo(){
        return userService.userInfo();
    }


    @PutMapping("/userInfo")
    @SystemLog(businessName = "更新用户信息")
    @ApiOperation(value = "保存用户信息修改")
    @ApiImplicitParam(name = "userInfoVo",value = "用户登录信息",required = true,dataType = "UserInfoVo")
    public ResponseResult updateUserInfo(@RequestBody UserInfoVo userInfoVo){
        return userService.updateUserInfo(userInfoVo);
    }


    @PostMapping("/register")
    @SystemLog(businessName = "新用户进行注册")
    @ApiOperation(value = "新用户进行注册")
    @ApiImplicitParam(name = "userRegisterVo",value = "用户登录信息",required = true,dataType = "UserRegisterVo")
    public ResponseResult register(@RequestBody UserRegisterVo userRegisterVo){
        return userService.register(userRegisterVo);
    }


}
