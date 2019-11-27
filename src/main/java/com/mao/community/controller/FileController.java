package com.mao.community.controller;

import com.mao.community.dto.FileDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FileController {

    @RequestMapping("/file/upload")
    @ResponseBody
    public FileDTO upload(){
        FileDTO fileDTO = new FileDTO();
        fileDTO.setSuccess(1);
        fileDTO.setMessage("好好看啊");
        fileDTO.setUrl("/img/default.jpg");
        return fileDTO;
    }
}
