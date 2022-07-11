package com.donggei.controller;


import com.donggei.domain.ResponseResult;
import com.donggei.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @className: CategoryController
 * @description: TODO 类描述
 * @author: Dong
 * @date: 2022/7/10
 **/
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/getCategoryList")
    public ResponseResult getCategoryList(){
        return   categoryService.getCategoryList();

    }



}
