package com.chirq.study.jdbc.orm.datasource.proxy;

import java.lang.reflect.Proxy;

public class DaoProxyFactory<T> {

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
    public static <T> T getDao(Class<T> daoInterface) {
        return (T) Proxy.newProxyInstance(daoInterface.getClassLoader(), new Class[] { daoInterface }, new DaoProxy<T>());
    }
}
