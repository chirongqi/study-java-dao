package com.chirq.study.jdbc.transaction.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.chirq.study.jdbc.DBConnectionContextHolder;
import com.chirq.study.jdbc.simplejdbc.entity.User;

public class UserTransationsDao {

    // 保存数据
    public int saveUser(User user) {
        Connection con = DBConnectionContextHolder.getDBConnection();
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
            throw new RuntimeException(e);
        } finally {
            // 释放资源
            try {
                if (pst != null)
                    pst.close();// 关闭预编译语句
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
