package com.donggei.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.donggei.domain.ResponseResult;
import com.donggei.domain.entity.Link;

public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();
}
