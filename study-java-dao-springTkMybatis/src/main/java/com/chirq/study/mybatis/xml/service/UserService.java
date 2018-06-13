package com.chirq.study.mybatis.xml.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.chirq.study.mybatis.xml.dao.UserMapper;
import com.chirq.study.mybatis.xml.entity.User;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public void save(User user) {
        userMapper.insert(user);
    }

    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public void delete(long id) {
        userMapper.deleteByPrimaryKey(id);
    }

}
