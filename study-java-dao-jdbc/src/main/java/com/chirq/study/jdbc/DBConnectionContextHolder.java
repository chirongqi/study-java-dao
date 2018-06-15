package com.chirq.study.jdbc;

import java.sql.Connection;

public class DBConnectionContextHolder {
    // ThreadLocal，线程本地变量，ThreadLocal为变量在每个线程中都创建了一个副本，那么每个线程可以访问自己内部的副本变量。
    private static ThreadLocal<Connection> dbConnection = new ThreadLocal<>();

    public static void setDBConnection(Connection connection) {
        dbConnection.set(connection);
    }

    public static Connection getDBConnection() {
        return dbConnection.get();
    }
}
