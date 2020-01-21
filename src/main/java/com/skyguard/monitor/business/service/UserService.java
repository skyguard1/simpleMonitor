package com.skyguard.monitor.business.service;

import com.skyguard.monitor.business.entity.User;
import org.springframework.stereotype.Service;

/**
 * @author : xingrufei
 * create at:  2020-01-21  10:54
 * @description:
 */
@Service
public class UserService {

    public User getUserByName(String name){
        User user = new User();
        user.setId("1");
        user.setName(name);
        user.setAddress("beijing");
        return user;
    }

}
