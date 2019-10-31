package com.jie.maven.firstspringboot.controller;

import com.jie.maven.firstspringboot.dto.AccessTokenDTO;
import com.jie.maven.firstspringboot.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name="state") String state ){
       AccessTokenDTO accessTokenDTO =  new AccessTokenDTO();
        accessTokenDTO.setClient_id("Iv1.4e08d515c4dc25cd");
        accessTokenDTO.setClient_secret("4b50422988014846f92c9a9adb4f3133dc9c0ff4");
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri("http://localhost:8888/callback");
        accessTokenDTO.setState(state);
        githubProvider.getAccessToken(accessTokenDTO);
        return "index";

    }
}
