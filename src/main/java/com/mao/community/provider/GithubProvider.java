package com.mao.community.provider;

import com.alibaba.fastjson.JSON;
import com.mao.community.dto.AccesstokenDTO;
import com.mao.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {
    String s;

    public String getAccessToken(AccesstokenDTO accesstokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accesstokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            s = response.body().string();
//            System.out.println(s);
            String[] split = s.split("&");
            String act = split[0];
            String[] split1 = act.split("=");
            String act_value = split1[1];
            return act_value;
        } catch (IOException e) {
        }
        return null;
    }

    public GithubUser getGithubUser(String s) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + s)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            System.out.println(string);
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);//将string直接转为java类对象
            System.out.println(githubUser.toString());
            return githubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}