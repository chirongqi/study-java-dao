package com.chirq.study.jdbc;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        System.out.println(DBConnection.getConnection());
        System.out.println(DBConnectionPool.getConnection());
    }
}
