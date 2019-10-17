package com.mao.community.Mapper;

import com.mao.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;


//private String accountid;
//private String username;
//private String token;
//private Long gmtcreate;
//private Long gmtmodified;
@Mapper
public interface UserMapper {
    @Insert("insert into user (account_id,name,token,gmt_create,gmt_modified) values (#{accountid},#{username},#{token},#{gmtcreate},#{gmtmodified})")
    void insert(User user);
}
