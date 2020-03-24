package com.treehole.demo.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.treehole.demo.entity.Comment;
import com.treehole.demo.mapper.CommentMapper;
import org.springframework.stereotype.Service;

@Service
public class CommentService extends ServiceImpl<CommentMapper, Comment> {

    public void saveByCheck(Comment comment) {
        if (comment.getParentId() == null || comment.getParentId() == 0){

        }
    }
}
