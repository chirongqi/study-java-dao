package com.chirq.study.jdbc.orm.datasource.proxy;

public class DaoSession {

    public static <T> T getDao(Class<T> type) {
        return new DaoProxyFactory<T>().newInstance(type);
    }
}
