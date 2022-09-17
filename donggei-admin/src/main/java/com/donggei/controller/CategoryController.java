package com.donggei.controller;

import com.donggei.domain.ResponseResult;
import com.donggei.service.CategoryService;
import com.donggei.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @className: CategoryController
 * @description: TODO 类描述
 * @author: Dong
 * @date: 2022/8/31
 **/
@RestController
@RequestMapping("/content/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    TagService tagService;
    @GetMapping("/listAllCategory")
    public ResponseResult listAllCategory(){
        return categoryService.getCategoryList();
    }
    @GetMapping("/listAllTag")
    public ResponseResult listAllTag(){
        return tagService.getAllTag();
    }

}
