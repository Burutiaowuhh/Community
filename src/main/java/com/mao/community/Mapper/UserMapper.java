package com.mao.community.Mapper;

import com.mao.community.model.User;
import org.apache.ibatis.annotations.*;


//private String accountid;
//private String username;
//private String token;
//private Long gmtcreate;
//private Long gmtmodified;
@Mapper
public interface UserMapper {
    @Insert("insert into user (account_id,name,token,gmt_create,gmt_modified,avatar_url) values (#{accountid},#{name},#{token},#{gmtcreate},#{gmtmodified},#{avatarUrl})")
    void insert(User user);        //#{}  会自动将方法里的model属性对应到#{}

    @Select("select * from user where token = #{token}")
    User findBytoken(@Param("token") String token);

    @Select("select * from user where id=#{id}")
    User findByid(@Param("id") Integer id);

    @Select("select * from user where account_id=#{accountid}")
    User findByAccountid(@Param("accountid") String accountid);

    @Update("update user set name=#{name},token=#{token},gmt_modified=#{gmtmodified},avatar_url=#{avatarUrl} where id=#{id}")
    void updateuser(User user);
}
