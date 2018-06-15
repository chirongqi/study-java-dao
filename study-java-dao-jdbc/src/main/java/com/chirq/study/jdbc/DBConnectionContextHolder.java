package com.chirq.study.jdbc;

import java.sql.Connection;

public class DBConnectionContextHolder {
    private static ThreadLocal<Connection> dbConnection = new ThreadLocal<>();

    public static void setDBConnection(Connection connection) {
        dbConnection.set(connection);
    }

    public static Connection getDBConnection() {
        return dbConnection.get();
    }
}
