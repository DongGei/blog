package com.donggei.controller;

import com.donggei.domain.ResponseResult;
import com.donggei.domain.dto.AddArticleDto;
import com.donggei.domain.entity.Article;
import com.donggei.domain.vo.ArticleVo;
import com.donggei.domain.vo.PageVo;
import com.donggei.service.ArticleService;
import com.donggei.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private ArticleService articleService;

    @GetMapping("list")
    public ResponseResult list(Article article,Integer pageNum,Integer pageSize){
        PageVo pageVo = articleService.selectArticlePage(article, pageNum, pageSize);
        return ResponseResult.okResult(pageVo);
    }


    @GetMapping("/listAllCategory")
    public ResponseResult getAllCategory() {
        return categoryService.getAllCategory();
    }

    @PostMapping
    public ResponseResult add(@RequestBody AddArticleDto article) {
        return articleService.add(article);
    }

    @GetMapping("/{id}")
    public ResponseResult get(@PathVariable("id") Long id) {
        ArticleVo article = articleService.getInfo(id);
        return ResponseResult.okResult(article);
    }

    @PutMapping
    public ResponseResult edit(@RequestBody AddArticleDto article) {
        articleService.edit(article);
        return ResponseResult.okResult();
    }
    @DeleteMapping("/{id}")
    public ResponseResult delete(@PathVariable Long id){
        articleService.removeById(id);
        return ResponseResult.okResult();
    }
}
