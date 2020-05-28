package com.excmmy.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import model.UserDTO;

/**
 * @author Administrator
 * @version 1.0
 **/
@RestController
public class TestController {

    @GetMapping(value = "/r/p1")
    @PreAuthorize("hasAuthority('P1')") //拥有p1权限方可访问此url
    public String p1(){
        // 获取用户身份信息
        UserDTO userDTO = JSON.parseObject(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString(), UserDTO.class);
        return userDTO.getId();
    }

    @GetMapping(value = "/r/p2")
    @PreAuthorize("hasAuthority('P2')") //拥有p1权限方可访问此url
    public String p2(){
        UserDTO userDTO = JSON.parseObject(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString(), UserDTO.class);
        return userDTO.getName();
    }

    @GetMapping(value = "/hello") // 任意请求
    public String hello1(){
        return "Hello_Get";
    }

    @PostMapping(value = "/hello") // 任意请求
    public String hello2(){
        return "Hello_Post";
    }
}