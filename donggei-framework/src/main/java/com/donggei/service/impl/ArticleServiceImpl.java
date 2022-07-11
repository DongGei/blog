package com.donggei.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.donggei.constants.SystemConstants;
import com.donggei.domain.ResponseResult;
import com.donggei.domain.entity.Article;
import com.donggei.domain.entity.Category;
import com.donggei.domain.vo.ArticleDetailVo;
import com.donggei.domain.vo.ArticleListVo;
import com.donggei.domain.vo.HotArticleVo;
import com.donggei.domain.vo.PageVo;
import com.donggei.mapper.ArticleMapper;
import com.donggei.service.ArticleService;
import com.donggei.service.CategoryService;
import com.donggei.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @className: ArticleServiceImpl
 * @description: TODO 类描述
 * @author: Dong
 * @date: 2022/7/9
 **/
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    @Lazy(value = true) //解决存在循环依赖问题  调mapper也行应该
    private CategoryService categoryService;

    @Override
    public ResponseResult hotArticleList() {

        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper();

        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);

        queryWrapper.orderByDesc(Article::getViewCount);
        Page<Article> page = new Page<>(1, 10);
        page(page, queryWrapper);

        List<Article> records = page.getRecords();

        //bean 拷贝
        List<HotArticleVo> articleVOS = BeanCopyUtils.copyBeanList(records, HotArticleVo.class);

        return ResponseResult.okResult(articleVOS);
    }

    /**
     * 在首页和分类页面都需要查询文章列表。
     * 首页：查询所有的文章
     * 分类页面：查询对应分类下的文章
     * 要求：①只能查询正式发布的文章 ②置顶的文章要显示在最前面
     *
     * @param pageNum
     * @param pageSize
     * @param categoryId
     * @return
     */
    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {

        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        //如果有categoryId 要和传入的相同匹配
        wrapper.eq(Objects.nonNull(categoryId) && categoryId > 0L, Article::getCategoryId, categoryId);
        //文章状态是正常的
        wrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //指定文章是要在前面的 字段是1   对字段进行排序实现要求
        wrapper.orderByDesc(Article::getIsTop);
        //分页查询
        // TODO pageNum,pageSize参数为空的少了判断
        Page<Article> page = new Page<>(pageNum, pageSize);
        Page<Article> articlePage = page(page, wrapper);
        List<Article> records = articlePage.getRecords();

        //查询分类名称  根据分类id
//        records.forEach(article -> {
//            Category category = categoryService.getById(article.getCategoryId());
//            article.setCategoryName(category.getName());
//        });
        records = records.stream()
                .map(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()))
                .collect(Collectors.toList());

        //封装成VO
        List<ArticleListVo> articleListVO = BeanCopyUtils.copyBeanList(records, ArticleListVo.class);


        PageVo pageVo = new PageVo(articleListVO, articlePage.getTotal());

        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getArticleDetail(Long id) {
        //根据id查询
        Article article = getById(id);
        //根据分类ID,查询分类名称
        Category category = categoryService.getById(article.getCategoryId());
        if (category != null) {
            article.setCategoryName(category.getName());
        }
        //封装VO
        ArticleDetailVo articleDetailVO = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        return ResponseResult.okResult(articleDetailVO);
    }
}
