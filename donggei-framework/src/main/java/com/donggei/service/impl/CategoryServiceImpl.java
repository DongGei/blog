package com.donggei.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.donggei.constants.SystemConstants;
import com.donggei.domain.ResponseResult;
import com.donggei.domain.entity.Article;
import com.donggei.domain.entity.Category;
import com.donggei.domain.vo.CategoryVo;
import com.donggei.mapper.CategoryMapper;
import com.donggei.service.ArticleService;
import com.donggei.service.CategoryService;
import com.donggei.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2022-07-10 20:05:29
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private ArticleService articleService;

    @Override
    public ResponseResult getCategoryList() {
        //查询文章表 状态是已发布的
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper();
        wrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> list = articleService.list(wrapper);
        //获取文章的分类ID 并且去重
        Set<Long> categoryIds = list.stream().map(Article::getCategoryId).collect(Collectors.toSet());
        //根据分类ID的这个集合 去查询分类表 分类表的状态是正常的不是禁用 封装成VO
        List<Category> categories = listByIds(categoryIds);
        List<Category> categoryList = categories.stream().
                filter(category -> category.getStatus().equals(SystemConstants.CATEGORY_STATUS_NORMAL))
                .collect(Collectors.toList());

        //封装成VO
        List<CategoryVo> categoryVOS = BeanCopyUtils.copyBeanList(categoryList, CategoryVo.class);
        return ResponseResult.okResult(categoryVOS);
    }
}

