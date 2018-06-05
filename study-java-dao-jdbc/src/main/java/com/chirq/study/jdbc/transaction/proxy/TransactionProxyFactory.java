package com.chirq.study.jdbc.transaction.proxy;

import java.lang.reflect.Proxy;

public class TransactionProxyFactory<T> {
    /**
     * 
     * <b>方法名</b>：newInstance<br>
     * <b>功能</b>：创建代理<br>
     * 
     * @author <font color='blue'>rongqi.chi</font>
     * @date 2018年6月5日 下午2:31:02
     * @param daoInterface
     * @return
     */
    @SuppressWarnings("unchecked")
    public T newInstance(Class<T> daoInterface, Object subject) {
        return (T) Proxy.newProxyInstance(daoInterface.getClassLoader(), new Class[] { daoInterface }, new TransactionProxy<T>(subject));
    }
}
