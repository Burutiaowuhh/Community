package com.mao.community.dto;

import com.mao.community.exception.CustomizeErrorCode;
import com.mao.community.exception.CustomizeException;
import lombok.Data;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Data
public class ResultDTO<T> {
    private Integer code;
    private String message;
    private T data;

    public static ResultDTO errorof(Integer code,String message){
        ResultDTO resultDTO=new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    public static ResultDTO errorof(CustomizeErrorCode noLogin) {
        ResultDTO resultDTO=new ResultDTO();
        resultDTO.setCode(noLogin.getCode());
        resultDTO.setMessage(noLogin.getMessage());
        return resultDTO;
    }

    public static ResultDTO okof() {
        ResultDTO resultDTO=new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("登录成功");
        return resultDTO;
    }

    public static ResultDTO errorof(CustomizeException e) {
        return errorof(e.getCode(),e.getMessage());
    }

    public static <T> ResultDTO okof(T t) {
        ResultDTO resultDTO=new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("登录成功");
        resultDTO.setData(t);
        return resultDTO;
    }
}
