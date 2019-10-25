package com.mao.community.Mapper;

import com.mao.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create(Question question);

    @Select("select * from question limit #{offset},#{size}")
    List<Question> list(@Param(value = "offset") Integer offset, @Param(value = "size") Integer size);

    @Select("select count(1) from question")
    Integer findtotalCOunt();

    @Select("select * from question where creator = #{id} limit #{offset},#{size}")
    List<Question> listbyuserid(@Param("id") Integer id,@Param("offset") Integer offset,@Param("size") Integer size);

    @Select("select count(1) from question where creator = #{id}")
    Integer findtotalCOuntbyuser(@Param("id") Integer id);
}
