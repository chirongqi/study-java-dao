package com.chirq.study.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    private static final String DBDRIVER = "com.mysql.jdbc.Driver";

    private static final String DBURL = "jdbc:mysql://127.0.0.1:3306/study?useUnicode=true&amp;characterEncoding=utf8";

    private static final String DBUSER = "root";// 用户名

    private static final String PASSWORD = "123456";// 密码

    static {
        try {
            // 加载名字为DBDRIVER的类执行该类的静态代码块。
            Class.forName(DBDRIVER);
        } catch (ClassNotFoundException e) {
        }
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {// 通过驱动管理器获得连接
            connection = DriverManager.getConnection(DBURL, DBUSER, PASSWORD);
        } catch (Exception e) {
        }
        return connection;
    }
}
