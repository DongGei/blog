package com.donggei.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.donggei.constants.SystemConstants;
import com.donggei.domain.ResponseResult;
import com.donggei.domain.dto.AddCategoryDTO;
import com.donggei.domain.dto.UpdateCategoryDTO;
import com.donggei.domain.entity.Article;
import com.donggei.domain.entity.Category;
import com.donggei.domain.vo.CategoryVo;
import com.donggei.domain.vo.PageVo;
import com.donggei.enums.AppHttpCodeEnum;
import com.donggei.exception.SystemException;
import com.donggei.mapper.CategoryMapper;
import com.donggei.service.ArticleService;
import com.donggei.service.CategoryService;
import com.donggei.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2022-05-12 10:23:22
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private ArticleService articleService;
    @Autowired
    CategoryService categoryService;
    @Override
    public ResponseResult getCategoryList() {
        //查询文章表  状态为已发布的文章
        LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper<>();
        articleWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleService.list(articleWrapper);
        //获取文章的id并去重
        Set<Long> categoryIds = articleList.stream()
                .map(Article::getCategoryId)
                .collect(Collectors.toSet());
        //查询分类表
        List<Category> categories = this.listByIds(categoryIds);
        categories = categories.stream().
                filter(category -> SystemConstants.CATEGORY_STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());
        //封装vo
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories,CategoryVo.class);
        return ResponseResult.okResult(categoryVos);
    }

    @Override
    public ResponseResult getAllCategory() {
        //1.查询以发布的文章
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Article::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleService.list(lambdaQueryWrapper);
        //查询这些文章的分类id，并去重set集合没有重复的数据
        Set<Long> categoryId = articleList.stream()
                .map(article -> article.getCategoryId())
                .collect(Collectors.toSet());
        //使用分类id去查询分类表
        List<Category> categoryList =  listByIds(categoryId);

        List<Category> collect = categoryList.stream()
                .filter(category -> category.getStatus().equals(SystemConstants.STATUS_NORMAL))
                .collect(Collectors.toList());
        //封装vo

        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(collect, CategoryVo.class);
        //返回
        ResponseResult result = ResponseResult.okResult(categoryVos);

        return result;
    }

    @Override
    public ResponseResult addCategory(AddCategoryDTO addCategoryDTO) {

        Category category = BeanCopyUtils.copyBean(addCategoryDTO, Category.class);
        boolean save = save(category);
        if(save){
            return ResponseResult.okResult();
        }else {

            throw new SystemException(AppHttpCodeEnum.CATEGORY_INSERT_FAIL);
        }

    }

    @Override
    public ResponseResult deleteCategoryById(List<Long> idList) {
        idList.stream()
                .forEach(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) {
                        UpdateWrapper<Category> categoryUpdateWrapper = new UpdateWrapper<>();
                        categoryUpdateWrapper.eq("id",aLong).set("del_flag",1);
                        boolean update = update(categoryUpdateWrapper);
                        if(!update){
                            throw new SystemException(AppHttpCodeEnum.DELETE_ERROR);
                        }
                    }
                });
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getCategoryById(Long id) {
        Category category = categoryService.getBaseMapper().selectById(id);
        CategoryVo categoryVo = BeanCopyUtils.copyBean(category, CategoryVo.class);
        return ResponseResult.okResult(categoryVo);
    }

    /**
     * 更新
     * @param updateCategoryDTO
     * @return
     */
    @Override
    public ResponseResult updateCategory(UpdateCategoryDTO updateCategoryDTO) {
        Category category = BeanCopyUtils.copyBean(updateCategoryDTO, Category.class);
        boolean b = updateById(category);
        if(b){
            return ResponseResult.okResult();
        }else{
            throw new SystemException(AppHttpCodeEnum.CATEGORY_UPDATE_ERROR);
        }

    }

    @Override
    public PageVo selectCategoryPage(Category category, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper();

        queryWrapper.like(StringUtils.hasText(category.getName()),Category::getName, category.getName());
        queryWrapper.eq(Objects.nonNull(category.getStatus()),Category::getStatus, category.getStatus());

        Page<Category> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page,queryWrapper);

        //转换成VO
        List<Category> categories = page.getRecords();

        PageVo pageVo = new PageVo();
        pageVo.setTotal(page.getTotal());
        pageVo.setRows(categories);
        return pageVo;
    }
}

