package com.donggei.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.donggei.constants.SystemConstants;
import com.donggei.domain.ResponseResult;
import com.donggei.domain.dto.AddCommentDto;
import com.donggei.domain.entity.Comment;
import com.donggei.domain.entity.User;
import com.donggei.domain.vo.CommentVo;
import com.donggei.domain.vo.PageVo;
import com.donggei.enums.AppHttpCodeEnum;
import com.donggei.exception.SystemException;
import com.donggei.mapper.CommentMapper;
import com.donggei.service.BlogLoginService;
import com.donggei.service.CommentService;
import com.donggei.utils.BeanCopyUtils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author makejava
 * @since 2022-05-14 23:36:51
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {


    @Autowired
    private BlogLoginService userService;



    @Override
    public ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize) {

        //查询对应文章的根评论
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        //对articleId进行判断
        queryWrapper.eq(SystemConstants.ARTICLE_COMMENT.equals(commentType),Comment::getArticleId,articleId);
        //根评论 rootId为-1
        queryWrapper.eq(Comment::getRootId, SystemConstants.COMMENT_TOP_ID);  //分页查询
        queryWrapper.eq(Comment::getType,commentType);  //查询文章评论查询
        queryWrapper.orderByDesc(Comment::getCreateTime);
        Page<Comment> page = new Page(pageNum,pageSize);
        page(page,queryWrapper);
        List<CommentVo> commentVoList = toCommentVoList(page.getRecords());
        //查询所有根评论对应的子评论集合，并且赋值给对应的属性
        Optional.ofNullable(commentVoList).orElse(new ArrayList<>())
                .parallelStream()
                .forEach(item -> {
                    String avartar = userService.getById(item.getCreateBy()).getAvatar();
                    User byId = userService.getById(item.getCreateBy());

                    item.setAvator(avartar);
                    //查询对应的子评论
                    List<CommentVo> children = getChildren(commentType,item.getId());
                    item.setChildren(children);
                });


        return ResponseResult.okResult(new PageVo(commentVoList,page.getTotal()));
    }


    @Override
    public ResponseResult addComment(AddCommentDto addCommentDto) {
        //评论内容不能为空
        if(!StringUtils.hasText(addCommentDto.getContent())){
            //结合前端响应
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        Comment comment = new Comment();
        comment = BeanCopyUtils.copyBean(addCommentDto,Comment.class);
        if(!Objects.isNull(comment)) {
            save(comment);
            return ResponseResult.okResult();
        }
        return ResponseResult.errorResult(-1,"保存失败");

    }

    /**
     * 根据根评论的id查询所对应的子评论的集合
     * @param id 根评论的id
     * @return
     */
    private List<CommentVo> getChildren(String type, Long id) {
        //查询对应文章的根评论
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        //对articleId进行判断
        queryWrapper.eq(Comment::getRootId,id);
        queryWrapper.orderByDesc(Comment::getCreateTime);
        //根评论 rootId为-1
        queryWrapper.eq(Comment::getType,type);  //查询文章评论查询
        List<Comment> comment =  this.list(queryWrapper);

        return toCommentVoList(comment);
    }

    //只进行第一级的遍历
    private List<CommentVo> toCommentVoList(List<Comment> list) {
        //查询对应文章的根评论
        List<CommentVo> lists =  BeanCopyUtils.copyBeanList(list,CommentVo.class);
        Optional.ofNullable(lists).orElse(new ArrayList<>())
                .parallelStream()
                .forEach(comment -> {
                    String avartar = userService.getById(comment.getCreateBy()).getAvatar();
                    comment.setAvator(avartar);
                    //通过creatyBy查询用户的昵称并赋值
                    if(comment.getCreateBy() != -1) {
                        String nickName = userService.getById(comment.getCreateBy()).getNickName();
                        comment.setUsername(nickName);
                    }

                    //对articleId进行判断
                    //通过toCommentUserId查询用户的昵称并赋值
                    //如果toCommentUserId不为-1才进行查询
                    if(comment.getToCommentUserId()!=-1){
                        String toCommentUserName = userService.getById(comment.getToCommentUserId()).getNickName();
                        comment.setToCommentUserName(toCommentUserName);
                    }
                });
        return lists;

    }


}

