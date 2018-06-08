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

    // 一级缓存
    @Test
    public void findUserByNameToOneCache() {
        SqlSession session = sqlSessionFactory.openSession();
        // ---------------
        UserMapper userMapper = session.getMapper(UserMapper.class);
        User user = userMapper.selectByName("张三");
        System.out.println(user);
        System.out.println(userMapper.selectByName("张三"));
        // --------------
        session.close();
    }

    @Test
    public void findUserByAge() {
        SqlSession session = sqlSessionFactory.openSession();
        // ---------------
        UserMapper userMapper = session.getMapper(UserMapper.class);
        // List<User> list = userMapper.selectByAge3(12, 13);
        List<User> list = userMapper.selectByAge(12);
        for (User user : list) {
            System.out.println(user);
        }
        list = userMapper.selectByAge2(12);
        // --------------
        session.close();
    }

    // 二级缓存
    @Test
    public void testTwoCache() {
        SqlSession session1 = sqlSessionFactory.openSession(true);
        SqlSession session2 = sqlSessionFactory.openSession(true);

        UserMapper userMapper1 = session1.getMapper(UserMapper.class);
        UserMapper userMapper2 = session2.getMapper(UserMapper.class);

        User user1 = userMapper1.selectByName("张三");
        session1.commit();
        System.out.println(user1);
        User user2 = userMapper2.selectByName("张三");
        System.out.println(user2);
    }

    public static void main(String[] args) {
        MybatisTest test = new MybatisTest();
        test.brefore();
        test.findUserByName();
    }
}
