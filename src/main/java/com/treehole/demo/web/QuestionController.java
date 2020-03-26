package com.treehole.demo.web;

import com.treehole.demo.entity.CommentCreateDTO;
import com.treehole.demo.entity.CommentDTO;
import com.treehole.demo.entity.QuestionVo;
import com.treehole.demo.service.CommentService;
import com.treehole.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @RequestMapping("/question/{id}")
    public String question(
        @PathVariable(name = "id") Integer id,
        Model model
    ){
        QuestionVo  questionVo = questionService.getById(id);
        List<QuestionVo> relationQuestions = questionService.selectByTag(questionVo);
        List<CommentDTO> comments =  commentService.listByQuestionId(id,1);
        //累加浏览
        questionService.incView(id);
        model.addAttribute("question",questionVo);
        model.addAttribute("comments",comments);
        model.addAttribute("relatedQuestions",relationQuestions);
        return "question";
    }
}
