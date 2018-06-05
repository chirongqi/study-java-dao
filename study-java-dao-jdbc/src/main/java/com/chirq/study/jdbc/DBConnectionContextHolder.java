package com.chirq.study.jdbc;

import java.sql.Connection;

public class DBConnectionContextHolder {
    private static ThreadLocal<Connection> dbConnection = new ThreadLocal<>();

    public static void setDBConnection(Connection connection) {
        // 不使用mycat时这里切换数据源的地方进行屏蔽
        dbConnection.set(connection);
    }

    public static Connection getDBConnection() {
        return dbConnection.get();
    }
}
