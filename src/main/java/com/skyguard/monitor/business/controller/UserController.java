package com.skyguard.monitor.business.controller;

import com.skyguard.monitor.business.entity.User;
import com.skyguard.monitor.business.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : xingrufei
 * create at:  2020-01-21  10:52
 * @description:
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
   private UserService userService;

    @GetMapping("/query")
    public User queryUser(String name){

        User user = userService.getUserByName(name);

        return user;
    }



}
