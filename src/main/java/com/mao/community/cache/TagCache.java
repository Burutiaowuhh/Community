package com.mao.community.cache;

import com.mao.community.dto.TagDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TagCache {
    public static List<TagDTO> get(){
        ArrayList<TagDTO> tagDTOS = new ArrayList<>();
        TagDTO tagDTO=new TagDTO();
        tagDTO.setCategory("开发语言");
        tagDTO.setTags(Arrays.asList("java","cc++","php","perl","python","javascript","c#","ruby","go","lua","node.js","erlang","scala","bash","actionscript","golang","typescript","flutter"));

        TagDTO tagDTO0 = new TagDTO();
        tagDTO0.setCategory("Java开发");
        tagDTO0.setTags(Arrays.asList("java","java-ee","jar","spring","hibernate","struts","tomcat","maven","eclipse","intellij-idea"));

        TagDTO tagDTO1 = new TagDTO();
        tagDTO1.setCategory("前端开发");
        tagDTO1.setTags(Arrays.asList("html","html5","css","css3","javascript","jquery","json","ajax","正则表达式","bootstrap"));

        TagDTO tagDTO2 = new TagDTO();
        tagDTO2.setCategory("javascript开发");
        tagDTO2.setTags(Arrays.asList("javascript","jquery","node.js","chrome","firefox","internet-explorer","angular.js","typescript","ecmascript","vue.js","react.js"));

        TagDTO tagDTO3 = new TagDTO();
        tagDTO3.setCategory("安卓开发");
        tagDTO3.setTags(Arrays.asList("android","java","eclipse","xml","phonegap","json","webview","android-studio"));

        tagDTOS.add(tagDTO);
        tagDTOS.add(tagDTO0);
        tagDTOS.add(tagDTO1);
        tagDTOS.add(tagDTO2);
        tagDTOS.add(tagDTO3);
        return tagDTOS;
    }
    
    public static String filterInvalid(String tags){
        List<TagDTO> tagDTOS = get();
        String[] split = StringUtils.split(tags, ",");

        List<String> tagList = tagDTOS.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
        String invalid = Arrays.stream(split).filter(t -> !tagList.contains(t)).collect(Collectors.joining(","));
        return invalid;
    }
}
