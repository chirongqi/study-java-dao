package com.chirq.study.jdbc.transaction.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.chirq.study.jdbc.transaction.DBConnectionContextHolder;
import com.chirq.study.jdbc.transaction.entity.Payment;

public class PaymentDao {

    // 保存数据
    public int savePayment(Payment payment) {
        Connection con = DBConnectionContextHolder.getDBConnection();
        PreparedStatement pst = null; // sql预编译语句
        String sql = "insert into  t_payment (user_id,pay_money,pay_time,pay_status,pay_msg) values(?, ?, ?,?,?)";
        try {
            pst = con.prepareStatement(sql);
            pst.setLong(1, payment.getUserId());
            pst.setBigDecimal(2, payment.getPayMoney());
            pst.setTimestamp(3, new Timestamp(payment.getPayTime().getTime()));
            pst.setInt(4, payment.getPayStatus());
            pst.setString(5, payment.getPayMsg());
            return pst.executeUpdate();// 执行增加，返回值为该操作影响的行数
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 释放资源
            try {
                if (pst != null)
                    pst.close();// 关闭预编译语句
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

}
