package com.donggei.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.donggei.domain.ResponseResult;
import com.donggei.domain.entity.User;
import com.donggei.domain.vo.LoginFormVo;


/**
 * 用户表(User)表服务接口
 *userService
 * @author makejava
 * @since 2022-05-13 21:31:25
 */
public interface BlogLoginService extends IService<User> {


    ResponseResult login(LoginFormVo user);

    ResponseResult logout();
}
