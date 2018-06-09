package com.chirq.study.jdbc.orm.datasource.impl;

import java.sql.SQLException;
import java.util.List;

import com.chirq.study.jdbc.DBConnectionContextHolder;
import com.chirq.study.jdbc.orm.datasource.OrmSupport;
import com.chirq.study.jdbc.orm.datasource.orm.internal.OrmCoreAccessor;
import com.chirq.study.jdbc.orm.datasource.util.SqlHelper;

/**
 * 
 * <b>类名</b>：ServiceORMSupport.java<br>
 * <p>
 * <b>标题</b>：service实现ORM支持类
 * </p>
 * <p>
 * <b>描述</b>： ORM数据库操作实现类
 * </p>
 * </p>
 * 
 * @author <font color='blue'>chirq</font>
 * @version 1.0
 */
public class ORMSupportImpl<T> implements OrmSupport<T> {

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
	public T getEntity(Class<T> clazz, Integer id) {
		if (clazz == null) {
			throw new NullPointerException("传入的Class类为空");
		}
		try {
			return OrmCoreAccessor.getInstance().createOrmConnectionCallback(SqlHelper.SELECT_ENTITY, clazz, id)
					.doInConnection(DBConnectionContextHolder.getDBConnection());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
		// // 获取该类的ORM实体
		// long start = System.currentTimeMillis();
		// long end = System.currentTimeMillis();
		// System.out.println("执行sql用时: " + (end - start));
	}

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
	// @Override
	@SuppressWarnings("unchecked")
	public List<T> getAll(Class<T> clazz) {
		try {
			return (List<T>) OrmCoreAccessor.getInstance().createOrmConnectionCallback(SqlHelper.SELECT_ALL, clazz)
					.doInConnection(DBConnectionContextHolder.getDBConnection());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * <b>方法名</b>：save<br>
	 * <b>功能</b>：保存实体<br>
	 * 
	 * @author <font color='blue'>chirq</font>
	 * @param entity
	 */
	@Override
	public void save(Class<T> clazz, T entity) {
		if (clazz == null) {
			throw new NullPointerException("传入的Class类为空");
		}
		if (entity == null) {
			throw new NullPointerException("传入的参数为空");
		}
		try {
			OrmCoreAccessor.getInstance().createOrmConnectionCallback(SqlHelper.SAVE, clazz, entity)
					.doInConnection(DBConnectionContextHolder.getDBConnection());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * <b>方法名</b>：saveOrUpdate<br>
	 * <b>功能</b>：保存或更新实体<br>
	 * 该方法会自动根据实体类主键是否有值，进行相应的保存或更新
	 * 
	 * @author <font color='blue'>chirq</font>
	 * @param clazz
	 * @param entity
	 */
	public void saveOrUpdate(Class<T> clazz, T entity) {
		if (clazz == null) {
			throw new NullPointerException("传入的Class类为空");
		}
		if (entity == null) {
			throw new NullPointerException("传入的参数为空");
		}
		try {
			OrmCoreAccessor.getInstance().createOrmConnectionCallback(SqlHelper.SAVE_OR_UPDATE, clazz, entity)
					.doInConnection(DBConnectionContextHolder.getDBConnection());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * <b>方法名</b>：update<br>
	 * <b>功能</b>：更新实体<br>
	 * 
	 * @author <font color='blue'>chirq</font>
	 * @param entity
	 */
	@Override
	public void update(Class<T> clazz, T entity) {
		if (clazz == null) {
			throw new NullPointerException("传入的Class类为空");
		}
		if (entity == null) {
			throw new NullPointerException("传入的参数为空");
		}
		try {
			OrmCoreAccessor.getInstance().createOrmConnectionCallback(SqlHelper.UPDATE, clazz, entity)
					.doInConnection(DBConnectionContextHolder.getDBConnection());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * <b>方法名</b>：delete<br>
	 * <b>功能</b>：根据主键id 删除实体<br>
	 * 
	 * @author <font color='blue'>chirq</font>
	 * @param clazz
	 * @param deleteIds
	 */
	@Override
	public void delete(Class<T> clazz, Integer... deleteIds) {
		if (clazz == null) {
			throw new NullPointerException("传入的Class类为空");
		}
		if (deleteIds == null) {
			return;
		}
		try {
			OrmCoreAccessor.getInstance().createOrmConnectionCallback(SqlHelper.DELETE, clazz, (Object[]) deleteIds)
					.doInConnection(DBConnectionContextHolder.getDBConnection());
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * <b>方法名</b>：updateField<br>
	 * <b>功能</b>：根据 主键id 更新实体单个字段值<br>
	 * 
	 * @author <font color='blue'>chirq</font>
	 * @param clazz
	 * @param field
	 * @param value
	 * @param id
	 */
	@Override
	public void updateField(Class<T> clazz, String field, Object value, Integer... id) {
		if (clazz == null) {
			throw new NullPointerException("传入的Class类为空");
		}
		if (field == null || field.length() == 0 || value == null) {
			throw new NullPointerException("传入的参数为空");
		}
		if (id == null || id.length == 0) {
			throw new NullPointerException("传入的主键参数为空");
		}
		try {
			OrmCoreAccessor.getInstance()
					.createOrmConnectionCallback(SqlHelper.UPDATE_FIELD, clazz, clazz, field, value, id)
					.doInConnection(DBConnectionContextHolder.getDBConnection());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
