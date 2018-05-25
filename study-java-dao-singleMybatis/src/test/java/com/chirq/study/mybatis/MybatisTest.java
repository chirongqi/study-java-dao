package com.chirq.study.mybatis;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import com.chirq.study.mybatis.entity.User;
import com.chirq.study.mybatis.mapper.UserMapper;

public class MybatisTest {

    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void brefore() {
        String resource = "mybatis/mybatis-config.xml";
        InputStream inputStream;
        try {
            inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findUserByName() {
        SqlSession session = sqlSessionFactory.openSession();
        // ---------------
        UserMapper userMapper = session.getMapper(UserMapper.class);
        User user = userMapper.selectByName("张三");
        System.out.println(user);
        // --------------
        session.close();
    }

    @Test
    public void findUserByAge() {
        SqlSession session = sqlSessionFactory.openSession();
        // ---------------
        UserMapper userMapper = session.getMapper(UserMapper.class);
        List<User> list = userMapper.selectByAge3(12, 13);
        for (User user : list) {
            System.out.println(user);
        }
        // --------------
        session.close();
    }

    // public static void main(String[] args) {
    // MybatisTest test = new MybatisTest();
    // test.brefore();
    // test.findUserByName();
    // }
}
