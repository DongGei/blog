package com.donggei.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.donggei.domain.entity.ArticleTag;
import com.donggei.mapper.ArticleTagMapper;
import com.donggei.service.ArticleTagService;
import org.springframework.stereotype.Service;

/**
 * 文章标签关联表(ArticleTag)表服务实现类
 *
 * @author dzz
 * @since 2022-09-18 09:51:41
 */
@Service("articleTagService")
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {

}

