package com.treehole.demo.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.treehole.demo.entity.Comment;
import com.treehole.demo.entity.CommentDTO;
import com.treehole.demo.entity.Question;
import com.treehole.demo.entity.User;
import com.treehole.demo.entity.enums.CommentTypeEnum;
import com.treehole.demo.exception.CustomizeErrorCode;
import com.treehole.demo.exception.CustomizeException;
import com.treehole.demo.mapper.CommentMapper;
import com.treehole.demo.mapper.QuestionMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService extends ServiceImpl<CommentMapper, Comment> {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserService userService;


    @Transactional
    public void saveByCheck(Comment comment) {
        if (comment.getParentId() == null || comment.getParentId() == 0){
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if(comment.getType()==null || !CommentTypeEnum.isExist(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        if(comment.getType()==CommentTypeEnum.COMMENT.getType()){
            //回复评论
            Comment dbcomment = commentMapper.selectById(comment.getParentId());
            if(dbcomment == null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            } else {
                commentMapper.insert(comment);
                //增加评论的评论数
                commentMapper.incCommentCount(comment.getParentId());
            }
        } else {
            //回复问题
            Question question = questionMapper.getById(comment.getParentId());
            if(question==null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            questionMapper.incCommentCount(comment.getParentId());
        }
    }

    public List<CommentDTO> listByQuestionId(Integer id,Integer type) {
        List<Comment> comments = commentMapper.listByQuestionId(id,type);
        if(comments.size()==0){
            return new ArrayList<>();
        }
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for(Comment comment :comments){
            CommentDTO commentDTO =new CommentDTO();
            BeanUtils.copyProperties(comment,commentDTO);
            User user = userService.getById(comment.getCommentator());
            commentDTO.setUser(user);
            commentDTOList.add(commentDTO);
        }
        return commentDTOList;
    }

    //增加评论的评论数
    public void incCommentCount(Integer id) {
        commentMapper.incCommentCount(id);
    }

}
