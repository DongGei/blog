package com.donggei.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.donggei.domain.ResponseResult;
import com.donggei.domain.entity.Category;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2022-07-10 20:05:28
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();
}

