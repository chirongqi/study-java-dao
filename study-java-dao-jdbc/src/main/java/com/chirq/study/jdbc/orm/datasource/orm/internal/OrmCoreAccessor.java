package com.chirq.study.jdbc.orm.datasource.orm.internal;

import com.chirq.study.jdbc.orm.datasource.orm.ConnectionCallback;

/**
 * 
 * <b>类名</b>：OrmCoreAccessor.java<br>
 * <p>
 * <b>标题</b>：ORM內部映射核心访问类
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
public class OrmCoreAccessor {

    private OrmCoreAccessor() {
        // 私有化构造方法
    }

    public <T> ConnectionCallback<T> createOrmConnectionCallback(String sqlType, Class<T> clazz, Object... params) {
        OrmVo ormVo = this.injectOrmCacheVo(clazz);
        return new OrmConnectionCallback<T>(sqlType, ormVo, params);
    }

    /**
     * 
     * <b>方法名</b>：injectOrmCacheVo<br>
     * <b>功能</b>：将传入的类注入到ORM缓存中<br>
     * 
     * @author <font color='blue'>chirq</font>
     * @param entity
     * @return
     */
    protected OrmVo injectOrmCacheVo(Class<?> clazz) {
        OrmVo ormVo = OrmCacheManage.getOrmCache(clazz.getName());
        if (ormVo == null) { // 该类是第一次加载，需要进行解析并加入到cache
            ormVo = ParseEntityOrm.getInstance().parseEntity(clazz);
            ormVo.setcLoader(this.getClass().getClassLoader());
            OrmCacheManage.addOrmCache(ormVo); // 放入到缓存中
        }
        return ormVo;
    }

    /**
     * 
     * <b>方法名</b>：getInstance<br>
     * <b>功能</b>：获取实例<br>
     * 
     * @author <font color='blue'>chirq</font>
     * @date 2014-7-7 上午10:10:15
     * @return
     */
    public static OrmCoreAccessor getInstance() {
        return SingletonHoledr.INSTANCE;
    }

    /**
     * 静态内部类实现单例模式，实现延迟加载
     */
    private static class SingletonHoledr {
        // 单例对象
        private static final OrmCoreAccessor INSTANCE = new OrmCoreAccessor();
    }
}
