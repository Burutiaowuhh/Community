package com.mao.community.model;

import lombok.Data;

@Data
public class User {

    private Integer id;
    private String accountid;
    private String name;
    private String token;
    private Long gmtcreate;
    private Long gmtmodified;
    private String avatarUrl;


}
