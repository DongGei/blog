package com.donggei.constants;

public class SystemConstants
{
    /**
     *  文章是草稿
     */
    public static final int ARTICLE_STATUS_DRAFT = 1;
    /**
     *  文章是正常分布状态
     */
    public static final int ARTICLE_STATUS_NORMAL = 0;

    /**
     *  热门文章默认当前第一页 1
     */
    public static final int ARTICLE_PAGE_CURRENT = 1;

    /**
     *  热门文章默认当前分页大小 10
     */
    public static final int ARTICLE_PAGE_SIZE = 10;
    /**
     *  文章分类默认值 0
     */
    public static final String CATEGORY_STATUS_NORMAL = "0";
    /**
     * 友链状态为审核通过
     */
    public static final String LINK_STATUS_NORMAL = "0";
    /**
     * 关于评论信息分页查询的顶层id
     */
    public static final String COMMENT_TOP_ID = "-1";

    /**
     * 关于文章的评论信息
     */
    public static final String COMMENT_STATUS_ARTICLE_TYPE = "0";
    /**
     * 关于链接的评论信息
     */
    public static final String COMMENT_STATUS_LINK_TYPE = "0";

    /**
     * 评论类型为：文章评论
     */
    public static final String ARTICLE_COMMENT = "0";
    /**
     * 评论类型为：友联评论
     */
    public static final String LINK_COMMENT = "1";
}