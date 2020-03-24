package com.treehole.demo.web;

import com.treehole.demo.entity.Comment;
import com.treehole.demo.entity.CommentDTO;
import com.treehole.demo.entity.ResultDTO;
import com.treehole.demo.entity.User;
import com.treehole.demo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object post(
            @RequestBody CommentDTO commentDTO,
            HttpServletRequest request
    ){
        User user = (User)request.getSession().getAttribute("user");
        if(user==null){
            return ResultDTO.errorOf(2002,"未登录不能进行评论，请先登录！");
        }
        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0);
        commentService.saveByCheck(comment);
        Map<Object,Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("message","成功！");
        return objectObjectHashMap;
    }
}
