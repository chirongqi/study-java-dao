package com.chirq.study.jdbc.orm.datasource;

import java.util.List;

/**
 * 
 * <b>类名</b>：ServiceOrmSupport.java<br>
 * <p>
 * <b>标题</b>：service接口ORM支持类
 * </p>
 * <p>
 * <b>描述</b>： 继承此接口扩展器，即可对实体进行ORM数据库操作
 * </p>
 * * 使用此扩展器前，要保证相应的操作实体使用注解，标注实体与数据库表的关系 {@link javax.persistence.Table},
 * {@link javax.persistence.Column},
 * <p>
 * </p>
 * 
 * @author <font color='blue'>chirq</font>
 * @version 1.0
 */
public interface OrmSupport<T> {

    /**
     * 
     * <b>方法名</b>：getEntity<br>
     * <b>功能</b>：根据主键id查询实体<br>
     * 
     * @author <font color='blue'>chirq</font>
     * @param clazz
     * @param id
     * @return
     */
    T getEntity(Class<T> clazz, Integer id);

    /**
     * 
     * <b>方法名</b>：getAll<br>
     * <b>功能</b>：查询所有实体<br>
     * 
     * @author <font color='blue'>chirq</font>
     * @param clazz
     * @param args
     * @return
     */
    List<T> getAll(Class<T> clazz);

    /**
     * 
     * <b>方法名</b>：save<br>
     * <b>功能</b>：保存实体<br>
     * 
     * @author <font color='blue'>chirq</font>
     * @param <T>
     * @param entity
     */
    void save(Class<T> clazz, T entity);

    /**
     * 
     * <b>方法名</b>：saveOrUpdate<br>
     * <b>功能</b>：保存或更新实体<br>
     * 
     * @author <font color='blue'>chirq</font>
     * @param clazz
     * @param entity
     */
    void saveOrUpdate(Class<T> clazz, T entity);

    /**
     * 
     * <b>方法名</b>：update<br>
     * <b>功能</b>：更新实体<br>
     * 
     * @author <font color='blue'>chirq</font>
     * @param entity
     */
    void update(Class<T> clazz, T entity);

    /**
     * 
     * <b>方法名</b>：delete<br>
     * <b>功能</b>：根据主键id 删除实体<br>
     * 
     * @author <font color='blue'>chirq</font>
     * @param clazz
     * @param deleteIds
     */
    void delete(Class<T> clazz, Integer... deleteIds);

    /**
     * 
     * <b>方法名</b>：updateField<br>
     * <b>功能</b>：根据 主键id 更新实体单个字段值<br>
     * 
     * @author <font color='blue'>chirq</font>
     * @param clazz
     *            实体类
     * @param field
     *            更新的属性字段名称
     * @param value
     *            属性字段的新值
     * @param id
     *            实体主键id
     */
    void updateField(Class<T> clazz, String field, Object value, Integer... id);
}
