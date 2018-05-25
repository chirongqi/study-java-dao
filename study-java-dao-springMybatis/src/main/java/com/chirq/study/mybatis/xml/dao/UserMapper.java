package com.chirq.study.mybatis.xml.dao;

import org.springframework.stereotype.Repository;

import com.chirq.study.mybatis.xml.entity.User;

@Repository
public interface UserMapper {
    User selectByPrimaryKey(Long id);

    User selectByName(String name);

    int deleteByPrimaryKey(Long id);

    int insert(User user);

    int insertSelective(User user);

    int updateByPrimaryKeySelective(User user);
}
