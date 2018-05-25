package com.chirq.study.mybatis.xml.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.chirq.study.mybatis.xml.dao.UserMapper;
import com.chirq.study.mybatis.xml.entity.User;

@RestController
@RequestMapping("test")
public class UserController {
    @Autowired
    UserMapper userMapper;

    @RequestMapping(value = "saveUser")
    public String saveUser() {
        User user = new User();
        user.setAddress("海南省三沙市");
        user.setAge(41);
        user.setName("李四");
//        userMapper.insertSelective(user);
        return JSONObject.toJSONString(userMapper.selectByName("张三"));
    }
}
