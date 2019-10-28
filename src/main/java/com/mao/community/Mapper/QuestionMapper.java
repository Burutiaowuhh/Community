package com.mao.community.Mapper;

import com.mao.community.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create(Question question);

    @Select("select * from question limit #{offset},#{size}")
    List<Question> list(@Param(value = "offset") Integer offset, @Param(value = "size") Integer size);

    @Select("select count(1) from question")
    Integer findtotalcount();

    @Select("select * from question where creator = #{id} limit #{offset},#{size}")
    List<Question> listbyuserid(@Param("id") Integer id,@Param("offset") Integer offset,@Param("size") Integer size);

    @Select("select count(1) from question where creator = #{id}")
    Integer findtotalCOuntbyuser(@Param("id") Integer id);

    @Select("select * from question where id = #{id}")
    Question findQuesInfoById(@Param("id") Integer id);

    @Update("update question set title=#{title},description=#{description},gmt_modified=#{gmtModified},tag=#{tag} where id=#{id}")
    void update(Question question);
}
