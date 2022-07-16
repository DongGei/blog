package com.donggei.utils;

import com.donggei.domain.entity.Comment;
import com.donggei.domain.vo.CommentVo;
import com.donggei.mapper.UserMapper;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MakeCommentTree {


    @Autowired
    private static UserMapper userMapper;


    public static List<CommentVo> makeTree(List<Comment> commentList, Long pid){
        List<CommentVo> list = new ArrayList<>();
        Optional.ofNullable(commentList).orElse(new ArrayList<>())
                .stream()
                .forEach(comment -> {
                    CommentVo commentVo = new CommentVo();
                    commentVo = BeanCopyUtils.copyBean(comment, CommentVo.class);
                    String nickName = userMapper.selectById(comment.getCreateBy()).getNickName();
                    commentVo.setUsername(nickName);
                    List<CommentVo> children = makeTree(commentList,comment.getId());
                    commentVo.setChildren(children);
                    list.add(commentVo);
                });
        return list;
    }
}
