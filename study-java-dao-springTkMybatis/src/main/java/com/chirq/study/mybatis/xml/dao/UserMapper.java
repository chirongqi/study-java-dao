package com.chirq.study.mybatis.xml.dao;

import com.chirq.study.mybatis.xml.entity.User;

import tk.mybatis.mapper.common.Mapper;

public interface UserMapper extends Mapper<User> {

	User selectByName(String name);

	// int insert(User user);
	//
	// int insertSelective(User user);
	//
	// int updateByPrimaryKeySelective(User user);
}
