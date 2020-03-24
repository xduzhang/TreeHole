package com.treehole.demo.web;

import com.treehole.demo.entity.QuestionVo;
import com.treehole.demo.mapper.QuestionMapper;
import com.treehole.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @RequestMapping("/question/{id}")
    public String question(
        @PathVariable(name = "id") Integer id,
        Model model
    ){
        QuestionVo  questionVo = questionService.getById(id);
        //累加浏览
        questionService.incView(id);
        model.addAttribute("question",questionVo);
        return "question";
    }
}
