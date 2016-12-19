/**
 * 
 */
package com.pay.wechat.dao.userTest.impl;

import java.util.List;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.wechat.dao.userTest.UserTestDao;
import com.pay.wechat.model.UserTest;

/**
 * @author PengJiangbo
 *
 */
public class UserTestDaoImpl extends BaseDAOImpl<UserTest> implements
		UserTestDao {

	@Override
	public void insertUser(UserTest userTest) {
		getSqlMapClientTemplate().insert(getNamespace().concat("insertUserTest"), userTest); 
	}

	@Override
	public long insertUserWidthBack(UserTest userTest) {
		long result = (Long) getSqlMapClientTemplate().insert(getNamespace().concat("insertUserWidthBack"), userTest) ;
		return result ;
	}

	@Override
	public void insertUserBatch(List list) {
		this.getSqlMapClientTemplate().insert(getNamespace().concat("inserUserBatch"), list) ;
	}
	
}
