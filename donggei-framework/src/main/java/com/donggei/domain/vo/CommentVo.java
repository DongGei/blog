package com.donggei.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentVo {

    private Long id;
    //文章id
    private Long articleId;
    //文章根评论
    private String content;
    //所回复内容的目标评论的userid
    private Long toCommentUserId;
    private String toCommentUserName;

    //回复目标评论id
    private Long toCommentId;
    //存放着评论人的id对应着use表的id
    private Long createBy;
    //
    private Date createTime;
    //写这个评论人的名字
    private String username;

    private String avator;
    private List<CommentVo> children;


}
