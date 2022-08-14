package com.donggei.controller;


import com.donggei.constants.SystemConstants;
import com.donggei.domain.ResponseResult;
import com.donggei.domain.dto.AddCommentDto;
import com.donggei.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "文章评论管理")
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ApiOperation(value = "查询评论树")
    @ApiImplicitParams({@ApiImplicitParam(name = "pageNum",required = false,value = "当前页"),
            @ApiImplicitParam(name = "pageSize",required = false,value = "每页大小"),
            @ApiImplicitParam(name = "articleId",required = false,value = "文章id")})
    @GetMapping("/commentList")
    public ResponseResult commentList(
            @RequestParam(value = "pageNum",required = false,defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize",required = false,defaultValue = "10") Integer pageSize,
            @RequestParam(value = "articleId",required = false) Long articleId
          ){
        return commentService.commentList(SystemConstants.ARTICLE_COMMENT,articleId,pageNum,pageSize);
    }

    @ApiOperation(value = "查询友链评论树")
    @ApiImplicitParams({@ApiImplicitParam(name = "pageNum",required = false,value = "当前页"),
            @ApiImplicitParam(name = "pageSize",required = false,value = "每页大小"),
            @ApiImplicitParam(name = "articleId",required = false,value = "文章id")})
    @GetMapping("/linkCommentList")
    public ResponseResult linkCommentList(
            @RequestParam(value = "pageNum",required = false,defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize",required = false,defaultValue = "10") Integer pageSize
    ){
        return commentService.commentList(SystemConstants.LINK_COMMENT,null,pageNum,pageSize);
    }

    @ApiOperation(value = "文章评论接口")
    @ApiImplicitParam(name = "commentReplyVo",value = "文章评论",required = true,dataType = "AddCommentDto")
    @PostMapping
    public ResponseResult addComment(@RequestBody AddCommentDto addCommentDto){
        return commentService.addComment(addCommentDto);
    }
}
