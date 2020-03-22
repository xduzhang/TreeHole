package com.treehole.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.treehole.demo.entity.Question;
import com.treehole.demo.entity.QuestionVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionMapper extends BaseMapper<Question> {

    @Select("Select * From Question ORDER BY gmt_create desc limit #{offset},#{size}")
    List<Question> listQuestion(@Param(value = "offset") Integer offset, @Param(value = "size")  Integer size);

    @Select("Select count(1) from question")
    Integer count();

    @Select("Select * From Question Where creator=#{userId} ORDER BY gmt_create desc limit #{offset},#{size}")
    List<Question> listQuestionById(String userId, Integer offset, Integer size);

    @Select("Select count(1) from question Where creator=#{userId}")
    Integer countById(String userId);

    @Select("Select * From Question Where id=#{id}")
    Question getById(Integer id);
}
