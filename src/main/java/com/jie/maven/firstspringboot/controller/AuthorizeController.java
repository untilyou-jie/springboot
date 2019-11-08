package com.jie.maven.firstspringboot.controller;

import com.jie.maven.firstspringboot.dto.AccessTokenDTO;
import com.jie.maven.firstspringboot.dto.GithubUser;
import com.jie.maven.firstspringboot.mapper.UserMapper;
import com.jie.maven.firstspringboot.model.User;
import com.jie.maven.firstspringboot.provider.GithubProvider;
import com.jie.maven.firstspringboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {
    @Value("${github.client.id}")
    private String clientId;


    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;
    @Autowired
    private GithubProvider githubProvider;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name="state") String state ,
                           HttpServletRequest request, HttpServletResponse response){
       AccessTokenDTO accessTokenDTO =  new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if(githubUser!=null && String.valueOf(githubUser.getId())!= null){
            //登陆成功
            User user = new User();
            String token =UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setAvatarUrl(githubUser.getAvatarUrl());
            userService.createOrUpdate(user);
           response.addCookie(new Cookie("token",token));
            return "redirect:/";

        }else{
            //登录失败
            return "redirect:/";
        }


    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request,HttpServletResponse response){
        request.getSession().removeAttribute("user");
        Cookie c = new Cookie("token",null);
        c.setMaxAge(0);
        response.addCookie(c);
        return "redirect:/";
    }
}
