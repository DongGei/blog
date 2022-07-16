package com.donggei.controller;

import com.donggei.annotation.SystemLog;
import com.donggei.domain.ResponseResult;
import com.donggei.domain.entity.Article;
import com.donggei.service.ArticleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @className: ArticleController
 * @description: TODO 类描述
 * @author: Dong
 * @date: 2022/7/9
 **/
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/hotArticleList")
    public ResponseResult hotArticleList(){

        ResponseResult result =  articleService.hotArticleList();
        return result;
    }
    @GetMapping("/articleList")
    public ResponseResult articleList(Integer pageNum,Integer pageSize,Long categoryId) {
        return articleService.articleList(pageNum, pageSize, categoryId);
    }
    @GetMapping("/{id}")
    public ResponseResult getArticleDetail(@PathVariable("id") Long id){
        return  articleService.getArticleDetail(id);
    }

    @ApiOperation(value = "更新浏览次数")
    @SystemLog(businessName = "更新浏览次数")
    @PutMapping("/updateViewCount/{id}")
    public ResponseResult articleList(@PathVariable("id")  Long id) {
        return articleService.updateViewCount(id);
    }
}
