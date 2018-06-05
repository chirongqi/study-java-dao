package com.chirq.study.jdbc.orm.datasource.orm;

import com.chirq.study.jdbc.orm.datasource.orm.internal.OrmCoreAccessor;

/**
 * 
 * <b>类名</b>：OrmDispatch.java<br>
 * <p>
 * <b>标题</b>：orm数据访问分发器
 * </p>
 * <p>
 * <b>描述</b>：
 * </p>
 * <p>
 * </p>
 * 
 * @author <font color='blue'>chirq</font>
 * @version 1.0
 */
public class OrmDispatcher {

    // private FKLogger logger = FKLoggerFactory.getLogger(OrmDispatcher.class);

    private OrmDispatcher() {
        // 私有化构造方法
    }

    /**
     * 
     * <b>方法名</b>：getOrmConnectionCallback<br>
     * <b>功能</b>：获取数据访问处理回调类<br>
     * 
     * @author <font color='blue'>chirq</font>
     * @param sqlType
     * @param clazz
     * @param params
     * @return
     */
    public static <T> ConnectionCallback<T> getOrmConnectionCallback(String sqlType, Class<T> clazz, Object... params) {
        return OrmCoreAccessor.getInstance().createOrmConnectionCallback(sqlType, clazz, params);
    }
}
