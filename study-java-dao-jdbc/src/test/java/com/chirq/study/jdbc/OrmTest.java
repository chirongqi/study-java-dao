package com.chirq.study.jdbc;

import java.util.List;

import com.chirq.study.jdbc.orm.datasource.proxy.DaoProxyFactory;
import com.chirq.study.jdbc.orm.test.dao.UserOrmDao;
import com.chirq.study.jdbc.orm.test.entity.UserOrm;

import junit.framework.TestCase;

public class OrmTest extends TestCase {

	UserOrmDao userOrmDao = DaoProxyFactory.getDao(UserOrmDao.class);

	public void testSelectUser() {
		UserOrm user = userOrmDao.getEntity(UserOrm.class, 1);
		System.out.println(user);
	}

	public void testSave() {
		UserOrm user = new UserOrm();
		user.setAddress("海南省三沙市");
		user.setAge(41);
		user.setName("李四");
		user.setUserDept("技術部11");
		// user.setId(51L);
		// userOrmDao.save(UserOrm.class, user);
		userOrmDao.saveOrUpdate(UserOrm.class, user);
		System.out.println(user);
	}

	public void testSelectAll() {
		List<UserOrm> list = userOrmDao.getAll(UserOrm.class);
		for (UserOrm user : list) {
			System.out.println(user);
		}
	}

	public void testDelete() {
		userOrmDao.delete(UserOrm.class, 52);
	}

	public void testUpdate() {
		userOrmDao.updateField(UserOrm.class, "address", "新地址", 51);
	}
}
