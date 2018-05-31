package com.chirq.study.jdbc.transaction.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.chirq.study.jdbc.DBConnectionPool;
import com.chirq.study.jdbc.simplejdbc.entity.User;
import com.chirq.study.jdbc.transaction.DBConnectionContextHolder;
import com.chirq.study.jdbc.transaction.dao.PaymentDao;
import com.chirq.study.jdbc.transaction.dao.UserTransationsDao;
import com.chirq.study.jdbc.transaction.entity.Payment;

public class TransactionService {

    PaymentDao paymentDao = new PaymentDao();

    UserTransationsDao userTransationsDao = new UserTransationsDao();

    public void saveUserAndPayment(User user, Payment payment) {
        try {
            this.brefor();

            userTransationsDao.saveUser(user);
            paymentDao.savePayment(payment);

            this.after();
        } catch (Exception e) {
            this.exception();
            e.printStackTrace();
        } finally {
            this.release();
        }
    }

    private void brefor() throws SQLException {
        Connection connection = DBConnectionPool.getConnection();
        DBConnectionContextHolder.setDBConnection(connection);
        System.out.println("业务处理开始，开启连接");
    }

    private void after() throws SQLException {
        DBConnectionContextHolder.getDBConnection().commit();
        System.out.println("业务处理完成，提交事务");
    }

    private void release() {
        // 释放资源
        try {
            DBConnectionContextHolder.getDBConnection().close();// 关闭连接
            System.out.println("业务处理完成，释放资源");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void exception() {
        try {
            DBConnectionContextHolder.getDBConnection().rollback();
            System.out.println("业务处理异常，回滚");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
