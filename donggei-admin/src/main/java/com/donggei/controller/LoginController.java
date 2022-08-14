package com.donggei.controller;

import com.donggei.annotation.SystemLog;
import com.donggei.domain.ResponseResult;
import com.donggei.domain.entity.LoginUser;
import com.donggei.domain.entity.User;
import com.donggei.domain.vo.AdminUserInfoVo;
import com.donggei.domain.vo.LoginFormVo;
import com.donggei.domain.vo.UserInfoVo;
import com.donggei.enums.AppHttpCodeEnum;
import com.donggei.exception.SystemException;


import com.donggei.service.LoginService;
import com.donggei.service.MenuService;
import com.donggei.service.RoleService;
import com.donggei.utils.BeanCopyUtils;
import com.donggei.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Api(tags = "博客页面登录")
@RestController
@RequestMapping
public class LoginController {
    @Autowired
    private LoginService LoginService;

    @Autowired
    private MenuService menuService;
    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "博客登录")
    @ApiImplicitParam(name = "user",value = "用户登录信息",required = true,dataType = "LoginVo")
    @PostMapping("/user/login")
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
        return LoginService.login(user);
    }

    @GetMapping("/getInfo")
    public ResponseResult<AdminUserInfoVo> getInfo(){
        //获取当前登入的用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        User user = loginUser.getUser();
        //根据用户ID查询权限信息
       List<String> perms= menuService.selectPermsByUserId(user.getId());
        //根据用户ID查询角色信息
     //   List<String> roleKeyList=roleService.selectRoleKeyByUserId(user.getId());
        List<String> roleKeyList=null;

        //获取用户信息
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);

        //封装数据返回
        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(perms,roleKeyList,userInfoVo);

        return ResponseResult.okResult(adminUserInfoVo);


    }

}
