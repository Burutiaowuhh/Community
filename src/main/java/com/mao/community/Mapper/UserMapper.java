package com.mao.community.Mapper;

import com.mao.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


//private String accountid;
//private String username;
//private String token;
//private Long gmtcreate;
//private Long gmtmodified;
@Mapper
public interface UserMapper {
    @Insert("insert into user (account_id,name,token,gmt_create,gmt_modified,avatar_url) values (#{accountid},#{name},#{token},#{gmtcreate},#{gmtmodified},#{avatarUrl})")
    void insert(User user);        //#{}  会自动将方法里的model属性对应到#{}

    @Select("select account_id,name,token,gmt_create,gmt_modified from user where token = #{token}")
    User findBytoken(@Param("token") String token);

    @Select("select * from user where name=#{name}")
    String finByname(@Param("name") String name);
}
