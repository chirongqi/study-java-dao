package com.chirq.study.mybatis.xml.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.chirq.study.mybatis.xml.dao.UserMapper;
import com.chirq.study.mybatis.xml.entity.User;
import com.chirq.study.mybatis.xml.service.UserTransactionService;

@RestController
@RequestMapping("test")
public class UserController {

	@Autowired
	UserMapper userMapper;
	UserTransactionService userTransactionService;

	@RequestMapping(value = "saveUser")
	public String saveUser() {
		User user = new User();
		user.setAddress("海南省三沙市");
		user.setAge(41);
		user.setName("李四");
		// userMapper.insertSelective(user);
		User select = userMapper.selectByName("张三");
		return JSONObject.toJSONString(select);
	}

	@RequestMapping(value = "saveAndDelete")
	public String saveAndDelete() {
		User user = new User();
		user.setAddress("spring事物");
		user.setAge(41);
		user.setName("spring事物");
		long id = 2;
		userTransactionService.saveAndDelete(user, id);
		return "success";
	}

	@RequestMapping(value = "selectOne")
	public String selectOne() {
		User user = new User();
		user.setName("张三");
		User select = userMapper.selectOne(user);
		// userMapper.selectByExample(example);
		return JSONObject.toJSONString(select);
	}
}
