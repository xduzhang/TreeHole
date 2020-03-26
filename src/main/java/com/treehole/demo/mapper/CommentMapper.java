package com.treehole.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.treehole.demo.entity.Comment;
import com.treehole.demo.entity.CommentDTO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CommentMapper extends BaseMapper<Comment> {

    @Select("Select * From Comment Where parent_id=#{id} and type=#{type}")
    List<Comment> listByQuestionId(Integer id,Integer type);

    @Select("Update Comment Set comment_count = comment_count + 1 Where id=#{id}")
    void incCommentCount(Integer id);
}
