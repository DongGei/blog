package com.donggei.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.donggei.constants.SystemConstants;
import com.donggei.domain.ResponseResult;
import com.donggei.domain.dto.AddArticleDto;
import com.donggei.domain.entity.Article;
import com.donggei.domain.entity.ArticleTag;
import com.donggei.domain.vo.*;
import com.donggei.mapper.ArticleMapper;
import com.donggei.service.ArticleService;
import com.donggei.service.ArticleTagService;
import com.donggei.service.CategoryService;
import com.donggei.utils.BeanCopyUtils;
import com.donggei.utils.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
@Slf4j
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Autowired
    private ArticleTagService articleTagService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RedisCache redisCache;
    @Value("${ArticleViewCount}")
    private String ArticleViewCount;

    @Autowired
    private ArticleMapper articleMapper;


    @Override
    public ResponseResult hotArticleList() {
        //查询热门文章 封装成ResponseResult返回
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //必须是正式文章
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //按照浏览量进行排序
        queryWrapper.orderByDesc(Article::getViewCount);
        //最多只查询10条
        Page<Article> page = new Page<>(SystemConstants.ARTICLE_PAGE_CURRENT,SystemConstants.ARTICLE_PAGE_SIZE);
        page(page,queryWrapper);
        List<Article> articles = page.getRecords();
        List<Object> collect = articles.stream().map(article -> {
                    Integer viewCount = redisCache.getCacheMapValue(ArticleViewCount, article.getId().toString());
                     return article.setViewCount(viewCount.longValue());
                }
        ).collect(Collectors.toList());


        //stream流
        List<HotArticleVo> articleVos = BeanCopyUtils.copyBeanList(collect,HotArticleVo.class);
        return ResponseResult.okResult(articleVos);

    }

    @Override
    public ResponseResult articleList(Integer pageNum,Integer pageSize,Long categoryId){
        //查询条件
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 如果 有categoryId 就要 查询时要和传入的相同
        lambdaQueryWrapper.eq(Objects.nonNull(categoryId)&&categoryId>0,Article::getCategoryId,categoryId);
        // 状态是正式发布的
        lambdaQueryWrapper.eq(Article::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);
        //对isTop进行降序排列
        lambdaQueryWrapper.orderByDesc(Article::getIsTop);
        lambdaQueryWrapper.orderByDesc(Article::getCreateTime);

        Page<Article> page = new Page<>(pageNum,pageSize);
        page(page,lambdaQueryWrapper);
        List<Article> articles = page.getRecords();

        List<Article> collect = articles.stream()
                .peek(article -> {

                    Integer viewCount = redisCache.getCacheMapValue(ArticleViewCount, article.getId().toString());

                    article.setViewCount(viewCount.longValue());
                    log.info("setCategoryName======================"+article.getCategoryId().toString()+"viewCount======================");
                    if (article.getCategoryId()!=null) {
                        article.setCategoryName(categoryService.getById(article.getCategoryId()).getName());
                    }
                }).collect(Collectors.toList());

        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(collect,ArticleListVo.class);
        PageVo pageVo = new PageVo(articleListVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
        //articleId去查询articleName进行设置
//        for (Article article : articles) {
//            Category category = categoryService.getById(article.getCategoryId());
//            article.setCategoryName(category.getName());
//        }
    }

    @Override
    public ResponseResult getArticleDetail(Long id) {
        //根据id查询文章
        Article article = getById(id);
        //从redis当中获取viewCount
        Integer viewCount = redisCache.getCacheMapValue(ArticleViewCount,id.toString());
        article.setViewCount(viewCount.longValue());
        //转换成VO
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article,ArticleDetailVo.class);
        System.out.println(articleDetailVo.getViewCount());
        //根据分类id查询分类名
        categoryService.getById(articleDetailVo.getCategoryId());
        articleDetailVo.setCategoryName(articleDetailVo.getCategoryName());
        //封装响应返回
        return ResponseResult.okResult(articleDetailVo);
    }

    @Override
    public ResponseResult updateViewCount(Long id) {
        //更新Redis中对应id的浏览量
        redisCache.incrementCacheMapValue(ArticleViewCount,id.toString(),1);
        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult add(AddArticleDto articleDto) {
        //添加 博客
        Article article = BeanCopyUtils.copyBean(articleDto, Article.class);
        save(article);




        List<ArticleTag> articleTags = articleDto.getTags().stream()
                .map(tagId -> new ArticleTag(article.getId(), tagId))
                .collect(Collectors.toList());

        //添加 博客和标签的关联
        articleTagService.saveBatch(articleTags);

        //下面是更新redis数据

        System.out.println("程序初始化"+ArticleViewCount);

        //查询博客信息 id viewCount
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.select(Article::getId,Article::getViewCount);
        List<Article> articles = articleMapper.selectList(queryWrapper);
        Map<String, Integer> viewCountMap = articles.parallelStream()
                .collect(Collectors.toMap(article2 -> article2.getId().toString(),
                        article2 -> article.getViewCount().intValue()));

        //存入Redis  变成map集合存入
        redisCache.setCacheMap(ArticleViewCount,viewCountMap);



        return ResponseResult.okResult();
    }

    @Override
    public PageVo selectArticlePage(Article article, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper();

        queryWrapper.like(StringUtils.hasText(article.getTitle()),Article::getTitle, article.getTitle());
        queryWrapper.like(StringUtils.hasText(article.getSummary()),Article::getSummary, article.getSummary());
        Page<Article> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page,queryWrapper);

        //转换成VO
        List<Article> articles = page.getRecords();
        List<ArticleVo> articleVos = BeanCopyUtils.copyBeanList(articles, ArticleVo.class);
        //这里偷懒没写VO的转换 应该转换完在设置到最后的pageVo中

        PageVo pageVo = new PageVo();
        pageVo.setTotal(page.getTotal());
        pageVo.setRows(articleVos);
        return pageVo;
    }

    @Override
    public ArticleVo getInfo(Long id) {
        Article article = getById(id);
        //获取关联标签
        LambdaQueryWrapper<ArticleTag> articleTagLambdaQueryWrapper = new LambdaQueryWrapper<>();
        articleTagLambdaQueryWrapper.eq(ArticleTag::getArticleId,article.getId());
        List<ArticleTag> articleTags = articleTagService.list(articleTagLambdaQueryWrapper);
        List<Long> tags = articleTags.stream().map(articleTag -> articleTag.getTagId()).collect(Collectors.toList());

        ArticleVo articleVo = BeanCopyUtils.copyBean(article,ArticleVo.class);
        articleVo.setTags(tags);
        return articleVo;
    }

    @Override
    public void edit(AddArticleDto articleDto) {
        Article article = BeanCopyUtils.copyBean(articleDto, Article.class);
        //更新博客信息
        updateById(article);
        //删除原有的 标签和博客的关联
        LambdaQueryWrapper<ArticleTag> articleTagLambdaQueryWrapper = new LambdaQueryWrapper<>();
        articleTagLambdaQueryWrapper.eq(ArticleTag::getArticleId,article.getId());
        articleTagService.remove(articleTagLambdaQueryWrapper);
        //添加新的博客和标签的关联信息
        List<ArticleTag> articleTags = articleDto.getTags().stream()
                .map(tagId -> new ArticleTag(articleDto.getId(), tagId))
                .collect(Collectors.toList());
        articleTagService.saveBatch(articleTags);
    }

}

