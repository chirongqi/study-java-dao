package com.chirq.study.mybatis.xml.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chirq.study.mybatis.xml.entity.User;

@Service
public class UserTransactionService {

    @Autowired
    UserService userService;

    @Transactional
    public void saveAndDelete(User user, long id) {
        userService.save(user);
        userService.delete(id);
    }
}
