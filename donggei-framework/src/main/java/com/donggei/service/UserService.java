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

    ResponseResult selectUserPage(User user, Integer pageNum, Integer pageSize);

    boolean checkUserNameUnique(String userName);

    boolean checkPhoneUnique(User user);

    boolean checkEmailUnique(User user);

    ResponseResult addUser(User user);

    void updateUser(User user);
}
