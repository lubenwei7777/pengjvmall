package com.pengjv.gmall.order.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pengjv.gmall.bean.UserInfo;
import com.pengjv.gmall.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {


    @Reference
    UserService userService;

    @GetMapping("/trade")
    public UserInfo trade(String userId){

      return   userService.getUserInfoById(userId);

    }

}
