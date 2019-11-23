package com.mao.community.dto;

import lombok.Data;

@Data
public class NotificationDTO {
    private Long id;
    private Long notifier;
    private Long outerid;
    private Integer type;
    private String typeName;
    private Long gmtCreate;
    private Integer status;
    private String outerTitle;
    private String notifierName;

}
