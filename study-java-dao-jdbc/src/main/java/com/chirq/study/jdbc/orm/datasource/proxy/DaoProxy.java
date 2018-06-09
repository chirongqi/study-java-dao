package com.chirq.study.jdbc.orm.datasource.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chirq.study.jdbc.DBConnectionContextHolder;
import com.chirq.study.jdbc.DBConnectionPool;
import com.chirq.study.jdbc.orm.datasource.OrmSupport;
import com.chirq.study.jdbc.orm.datasource.impl.ORMSupportImpl;

public class DaoProxy<T> implements InvocationHandler {

	private Logger logger = LoggerFactory.getLogger(DaoProxy.class);

	final OrmSupport<T> ormSupport = new ORMSupportImpl<T>();

	@Override
	@SuppressWarnings("unchecked")
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		logger.info("{}执行dao方法:[{}], 入参：{}", proxy.getClass().getName(), method.getName(), args);
		if (Object.class.equals(method.getDeclaringClass())) {
			return method.invoke(this, args);
		}

		Object result = null;
		try {
			this.brefor();
			logger.info("执行dao方法:[{}], 获取数据库连接完成", method.getName());

			String metName = method.getName();
			switch (metName) {
			case "getEntity":
				result = ormSupport.getEntity((Class<T>) args[0], (Integer) args[1]);
				break;
			case "getAll":
				result = ormSupport.getAll((Class<T>) args[0]);
				break;
			case "save":
				ormSupport.save((Class<T>) args[0], (T) args[1]);
				break;
			case "saveOrUpdate":
				ormSupport.saveOrUpdate((Class<T>) args[0], (T) args[1]);
				break;
			case "update":
				ormSupport.update((Class<T>) args[0], (T) args[1]);
				break;
			case "delete":
				ormSupport.delete((Class<T>) args[0], (Integer[]) args[1]);
				break;
			case "updateField":
				ormSupport.updateField((Class<T>) args[0], (String) args[1], (Object) args[2], (Integer[]) args[3]);
				break;
			}

			this.after();
			logger.info("执行dao方法:[{}], 事务提交", method.getName());
		} catch (Exception e) {
			e.printStackTrace();
			this.exception();
		} finally {
			this.release();
		}
		logger.info("执行dao方法:[{}], 执行完成", method.getName());
		return result;
	}

	private void brefor() throws SQLException {
		Connection connection = DBConnectionPool.getConnection();
		connection.setAutoCommit(false);
		DBConnectionContextHolder.setDBConnection(connection);
	}

	private void after() throws SQLException {
		DBConnectionContextHolder.getDBConnection().commit();
	}

	private void release() {
		// 释放资源
		try {
			DBConnectionContextHolder.getDBConnection().close();// 关闭连接
			logger.info("业务处理完成，释放资源");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void exception() {
		try {
			DBConnectionContextHolder.getDBConnection().rollback();
			logger.info("业务处理异常，回滚");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
