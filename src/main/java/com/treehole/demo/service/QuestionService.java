package com.treehole.demo.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.treehole.demo.entity.PaginationDTO;
import com.treehole.demo.entity.Question;
import com.treehole.demo.entity.QuestionVo;
import com.treehole.demo.entity.User;
import com.treehole.demo.exception.CustomizeErrorCode;
import com.treehole.demo.exception.CustomizeException;
import com.treehole.demo.mapper.QuestionMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService extends ServiceImpl<QuestionMapper, Question> {


    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserService userService;

    //查询所有的questionVo对象
    public PaginationDTO findQuestionList(Integer page, Integer size){

         PaginationDTO paginationDTO =  new PaginationDTO();
         //总的数据数
         Integer totalCount = questionMapper.count();
         paginationDTO.setPagination(totalCount,page,size);
         if(page < 1){
             page = 1;
         }
         if(page > paginationDTO.getTotalPage()){
             page = paginationDTO.getTotalPage();
         }

         //size*(i-1)
         Integer offset = size * (page-1);
         List<Question> questions = questionMapper.listQuestion(offset,size);
         List<QuestionVo> questionVoList = new ArrayList<>();


         for(Question question : questions){
             User user = userService.getById(question.getCreator());
             QuestionVo questionVo = new QuestionVo();
             BeanUtils.copyProperties(question,questionVo);  //复制相同的属性
             questionVo.setUser(user);
             questionVoList.add(questionVo);
         }

         //把所有的列表放进去
         paginationDTO.setQuestions(questionVoList);
         return paginationDTO;
    }

    //查询固定的id的所有questionVo对象
    public PaginationDTO findQuestionListById(String userId, Integer page, Integer size) {

        PaginationDTO paginationDTO =  new PaginationDTO();
        //总的数据数
        Integer totalCount = questionMapper.countById(userId);
        paginationDTO.setPagination(totalCount,page,size);
        if(page < 1){
            page = 1;
        }
        if(page > paginationDTO.getTotalPage()){
            page = paginationDTO.getTotalPage();
        }
        //size*(i-1)
        Integer offset = size * (page-1);
        List<Question> questions = questionMapper.listQuestionById(userId,offset,size);
        List<QuestionVo> questionVoList = new ArrayList<>();
        for(Question question : questions){
            User user = userService.getById(question.getCreator());
            QuestionVo questionVo = new QuestionVo();
            BeanUtils.copyProperties(question,questionVo);  //复制相同的属性
            questionVo.setUser(user);
            questionVoList.add(questionVo);
        }
        //把所有的列表放进去
        paginationDTO.setQuestions(questionVoList);
        return paginationDTO;
    }


    public QuestionVo getById(Integer id){

        Question question = questionMapper.getById(id);
        if(question==null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionVo questionVo = new QuestionVo();
        BeanUtils.copyProperties(question,questionVo);
        User user = userService.getById(question.getCreator());
        questionVo.setUser(user);
        return questionVo;
    }

    public void createOrUpdate(Question question) {
        if(question.getId()==null){
            //创建
            question.setGmtCreate(System.currentTimeMillis());
            questionMapper.insert(question);
        } else {
            //更新
            question.setGmtModified(question.getGmtCreate());
            questionMapper.updateById(question);
        }
    }

    public void incView(Integer id) {
        questionMapper.updateViewCountById(id);
    }
}
