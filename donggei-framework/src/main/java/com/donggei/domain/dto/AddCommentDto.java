package com.donggei.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@ApiModel(value = "评论表单实体")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddCommentDto {

    //文章id
    @ApiModelProperty(name = "articleId",value="文章id",required = true)
    private Long articleId;
    //评论类型 0文章的评论 1友链的评论
    @ApiModelProperty(name = "type",value="评论类型 0文章的评论 1友链的评论",required = true)
    //评论类型（0代表文章评论，1代表友链评论）
    private String type;
    //父级评论id
    @ApiModelProperty(name = "rootId",value="父级评论id",required = true)
    //根评论id
    private Long rootId;
    //回复目标评论id
    @ApiModelProperty(name = "toCommentId",value="回复目标评论id",required = true)
    private Long toCommentId;
    //所回复内容的目标评论的user id
    @ApiModelProperty(name = "toCommentUserId",value="所回复内容的目标评论的user id",required = true)
    private Long toCommentUserId;
    //文章根评论
    @ApiModelProperty(name = "content",value="文章根评论内容",required = true)
    private String content;


}
