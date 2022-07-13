package com.donggei.service;

import com.donggei.domain.ResponseResult;
import com.donggei.domain.entity.User;

/**
 * @className: BlogLoginService
 * @description: TODO 类描述
 * @author: Dong
 * @date: 2022/7/11
 **/
public interface BlogLoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
