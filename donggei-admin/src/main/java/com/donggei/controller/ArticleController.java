package com.donggei.controller;

import com.donggei.domain.ResponseResult;
import com.donggei.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @className: ArticleController
 * @description: TODO 类描述
 * @author: Dong
 * @date: 2022/9/1
 **/
@RestController
@RequestMapping("/content/article")
public class ArticleController {
    @Autowired
    CategoryService categoryService;
    @GetMapping("/listAllCategory")
    public ResponseResult getAllCategory(){

        return   categoryService.getAllCategory();
    }
}
