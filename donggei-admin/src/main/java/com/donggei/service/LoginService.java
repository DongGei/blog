package com.donggei.service;

import com.donggei.domain.ResponseResult;
import com.donggei.domain.vo.LoginFormVo;

/**
 * @className: LoginService
 * @description: TODO 类描述
 * @author: Dong
 * @date: 2022/7/31
 **/
public interface LoginService {

    ResponseResult login(LoginFormVo user);

    ResponseResult logout();
}
