package com.jie.maven.firstspringboot.provider;

import com.alibaba.fastjson.JSON;
import com.jie.maven.firstspringboot.dto.AccessTokenDTO;
import com.jie.maven.firstspringboot.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO){
         MediaType mediaType= MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();


            RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
            Request request = new Request.Builder()
                    .url("https://github.com/login/oauth/access_token")
                    .post(body)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                String str=response.body().string();
                System.out.println(str);
              String token =  str.split("&")[0].split("=")[1];
                return token;

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;


    }
    public GithubUser getUser(String accesstoken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accesstoken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String str=response.body().string();
            GithubUser githubUser = JSON.parseObject(str, GithubUser.class);
            return githubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;


    }

}
