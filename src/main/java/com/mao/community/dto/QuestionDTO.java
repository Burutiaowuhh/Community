package com.mao.community.dto;

import com.mao.community.model.User;
import lombok.Data;

@Data
public class QuestionDTO {
    private Integer id;
    private String title;
    private String description;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private Integer viewCount;
    private String tag;
    private String commentCount;
    private String likeCount;
    private User user;
}
