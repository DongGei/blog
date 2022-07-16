package com.donggei.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.donggei.domain.ResponseResult;
import com.donggei.domain.entity.User;
import com.donggei.domain.vo.UserInfoVo;
import com.donggei.domain.vo.UserRegisterVo;


public interface UserService  extends IService<User> {
    ResponseResult userInfo();

    ResponseResult updateUserInfo(UserInfoVo userInfoVo);

    ResponseResult register(UserRegisterVo userRegisterVo);
}
