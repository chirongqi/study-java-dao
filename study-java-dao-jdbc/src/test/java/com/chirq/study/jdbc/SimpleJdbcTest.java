package com.chirq.study.jdbc;

import java.util.List;

import com.chirq.study.jdbc.simplejdbc.dao.UserDao;
import com.chirq.study.jdbc.simplejdbc.entity.User;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class SimpleJdbcTest extends TestCase {

    UserDao userDao = new UserDao();

    public void testGetByUserName() {
        User user = userDao.findUserByName("张三");
        System.out.println(user);
    }

    public void testInsertUser() {
    	User user = new User();
    	user.setAddress("海南省三沙市");
    	user.setAge(41);
    	user.setName("李四");
    	int res = userDao.saveUser(user);
    	System.out.println(res);
    	assertEquals(1, res);
    }
    
    public void testGetAll() {
        List<User> list = userDao.getAll();
        for (User user : list) {
            System.out.println(user);
        }
    }


    public void testfindColumnsInfo() {
        userDao.findColumnsInfo();
    }

    public void testDeleteAndSave() {
        User user = new User();
        user.setAddress("上海市");
        user.setAge(32);
        user.setName("王五");
        userDao.deleteAndSave("李四", user);
    }

    /**
     * Create the test case
     *
     * @param testName
     *            name of the test case
     */
    public SimpleJdbcTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(SimpleJdbcTest.class);
    }

}
