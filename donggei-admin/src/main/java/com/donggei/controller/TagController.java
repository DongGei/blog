package com.donggei.controller;

import com.donggei.domain.ResponseResult;
import com.donggei.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @className: TagController
 * @description: TODO 类描述
 * @author: Dong
 * @date: 2022/7/30
 **/
@RestController
@RequestMapping("/content/tag")
public class TagController {

    @Autowired
    TagService tagService;
    @GetMapping("/list")
    public ResponseResult list(){
        return ResponseResult.okResult(tagService.list());
    }

}
