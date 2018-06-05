package com.chirq.study.jdbc.transaction.proxy;

public class TransactionSession {

    public static <T> T getService(Class<T> type, Object subject) {
        return new TransactionProxyFactory<T>().newInstance(type, subject);
    }
}
