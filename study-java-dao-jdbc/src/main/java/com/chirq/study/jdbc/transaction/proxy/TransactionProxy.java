package com.chirq.study.jdbc.transaction.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chirq.study.jdbc.DBConnectionContextHolder;
import com.chirq.study.jdbc.DBConnectionPool;

public class TransactionProxy<T> implements InvocationHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    // 这个就是我们要代理的真实对象
    private Object subject;

    public TransactionProxy(Object subject) {
        this.subject = subject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        try {
            this.brefor();

            result = method.invoke(this.subject, args);

            this.after();
        } catch (Exception e) {
            this.exception();
            e.printStackTrace();
        } finally {
            this.release();
        }
        return result;
    }

    private void brefor() throws SQLException {
        Connection connection = DBConnectionPool.getConnection();
        connection.setAutoCommit(false);
        DBConnectionContextHolder.setDBConnection(connection);
        logger.info("获取数据库连接");
    }

    private void after() throws SQLException {
        DBConnectionContextHolder.getDBConnection().commit();
        logger.info("事务提交");
    }

    private void release() {
        // 释放资源
        try {
            DBConnectionContextHolder.getDBConnection().close();// 关闭连接
            logger.info("业务处理完成，释放资源");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void exception() {
        try {
            DBConnectionContextHolder.getDBConnection().rollback();
            logger.info("业务处理异常，回滚");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
