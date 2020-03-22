package com.treehole.demo.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.treehole.demo.entity.PaginationDTO;
import com.treehole.demo.entity.Question;
import com.treehole.demo.entity.QuestionVo;
import com.treehole.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class IndexController {


    @Autowired
    private QuestionService questionService;

    //转到主界面
    @RequestMapping("/index")
    public String index(Model model,
                        @RequestParam(name="page",defaultValue = "1") Integer page,
                        @RequestParam(name="size",defaultValue = "5") Integer size
    ) {
        PaginationDTO pagination = questionService.findQuestionList(page,size);
        model.addAttribute("pagination",pagination);
        return "index";
    }



}