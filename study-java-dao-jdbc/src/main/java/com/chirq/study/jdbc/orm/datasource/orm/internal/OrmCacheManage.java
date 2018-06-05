package com.chirq.study.jdbc.orm.datasource.orm.internal;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 * <b>类名</b>：OrmCacheManage.java<br>
 * <p>
 * <b>标题</b>：ORM映射缓存管理
 * <p>
 * </p>
 * 
 * @author <font color='blue'>chirq</font>
 * @version 1.0
 */
final class OrmCacheManage {

    private static final Map<String, OrmVo> ormCache = new ConcurrentHashMap<>();

    private OrmCacheManage() {

    }

    /**
     * 
     * <b>方法名</b>：addOrmCache<br>
     * <b>功能</b>：添加ORM映射缓存<br>
     * 
     * @author <font color='blue'>chirq</font>
     * @param entity
     */
    protected static void addOrmCache(OrmVo entity) {
        ormCache.put(entity.getClassName(), entity);
    }

    /**
     * 
     * <b>方法名</b>：getOrmCache<br>
     * <b>功能</b>：获取ORM映射缓存<br>
     * 
     * @author <font color='blue'>chirq</font>
     * @param BundleSymbolicName
     * @param className
     * @return
     */
    protected static OrmVo getOrmCache(String className) {
        return ormCache.get(className);
    }

    protected static void removeOrmCache(String className) {
        ormCache.remove(className);
    }

}
