package com.chirq.study.jdbc.simplejdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.chirq.study.jdbc.DBConnection;
import com.chirq.study.jdbc.simplejdbc.entity.User;

public class UserDao {

    // 根据名称查询
    public User findUserByName(String name) {
        User userInfo = null;
        Connection con = DBConnection.getConnection();
        PreparedStatement pst = null; // sql预编译语句
        String sql = "select id,name,age,address from t_user where name=? "; // sql语句，‘?’表示占位符
        ResultSet rs = null;// 查询的结果集
        try {
            pst = con.prepareStatement(sql);// 获得预编译语句
            pst.setString(1, name);// 设置占位符的参数
            rs = pst.executeQuery();// 执行查询

            if (rs.first()) {// 遍历结果集
                userInfo = new User();
                userInfo.setId(rs.getLong("id"));
                userInfo.setName(rs.getString("name"));
                userInfo.setAge(rs.getInt("age"));
                userInfo.setAddress(rs.getString("address"));
                // getDate()返回的是java.sql.Date类型
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();// 关闭结果集
                pst.close();// 关闭预编译语句
                con.close();// 关闭连接
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return userInfo;
    }

    // 查询所有记录
    public List<User> getAll() {
        List<User> list = new ArrayList<>();
        Connection con = DBConnection.getConnection();// 获得数据库连接
        PreparedStatement pst = null; // sql预编译语句
        String sql = "select * from t_user";
        ResultSet rs = null;// 查询的结果集
        try {
            pst = con.prepareStatement(sql);// 获得预编译语句
            rs = pst.executeQuery();// 执行查询
            User userInfo = null;
            while (rs.next()) {// 遍历结果集
                userInfo = new User();
                userInfo.setId(rs.getLong("id"));
                userInfo.setAge(rs.getInt("age"));
                userInfo.setName(rs.getString("name"));
                userInfo.setAddress(rs.getString("address"));
                list.add(userInfo);
            }
        } catch (SQLException e) {
        } finally {
            try {
                rs.close();// 关闭结果集
                pst.close();// 关闭预编译语句
                con.close();// 关闭连接
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    // 保存数据
    public int saveUser(User user) {
        Connection con = DBConnection.getConnection();
        PreparedStatement pst = null; // sql预编译语句
        String sql = "insert into  t_user (name,age,address) values(?, ?, ?)";// sql语句
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, user.getName());
            pst.setInt(2, user.getAge());
            pst.setString(3, user.getAddress());
            int i = pst.executeUpdate();// 执行增加，返回值为该操作影响的行数
            return i;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 释放资源
            try {
                pst.close();// 关闭预编译语句
                con.close();// 关闭连接
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    // 查询数据字段
    public void findColumnsInfo() {
        Connection con = DBConnection.getConnection();
        PreparedStatement pst = null; // sql预编译语句
        String sql = "select  * from t_user limit 1";
        ResultSet rs;
        try {
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            ResultSetMetaData rd = rs.getMetaData();
            int len = rd.getColumnCount();
            for (int i = 1; i <= len; i++) {
                System.out.println("列名=" + rd.getColumnName(i));
                System.out.println("类型=" + rd.getColumnTypeName(i));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 释放资源
            try {
                pst.close();// 关闭预编译语句
                con.close();// 关闭连接
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteAndSave(String name, User user) {
        Connection con = DBConnection.getConnection();
        String deleteSql = "delete from t_user where name= ?";
        String saveSql = "insert into  t_user (name,age,address) values(?, ?, ?)";
        PreparedStatement delPst = null;
        PreparedStatement savePst = null;
        try {
            con.setAutoCommit(false);
            delPst = con.prepareStatement(deleteSql);
            delPst.setString(1, name);
            delPst.executeUpdate();
            savePst = con.prepareStatement(saveSql);
            savePst.setString(1, user.getName());
            savePst.setInt(2, user.getAge());
            savePst.setString(3, user.getAddress());
            savePst.executeUpdate();// 执行增加，返回值为该操作影响的行数
            con.commit();
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            // 释放资源
            try {
                delPst.close();
                savePst.close();// 关闭预编译语句
                con.close();// 关闭连接
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
