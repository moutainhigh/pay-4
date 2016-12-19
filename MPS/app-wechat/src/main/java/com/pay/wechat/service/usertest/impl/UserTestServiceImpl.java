/**
 * 
 */
package com.pay.wechat.service.usertest.impl;

import java.util.List;

import com.pay.wechat.dao.userTest.UserTestDao;
import com.pay.wechat.model.UserTest;
import com.pay.wechat.service.usertest.UserTestService;

/**
 * @author PengJiangbo
 *
 */
public class UserTestServiceImpl implements UserTestService {

	private UserTestDao userTestDao ;
	
	/**
	 * 新增
	 */
	public void insertUser(UserTest userTest) {
		userTestDao.insertUser(userTest);
	}

	//=============================setter=========

	public void setUserTestDao(UserTestDao userTestDao) {
		this.userTestDao = userTestDao;
	}

	@Override
	public long insertUserWidthBack(UserTest userTest) {
		return this.userTestDao.insertUserWidthBack(userTest) ;
	}

	@Override
	public void insertUserBatch(List list) {
		this.userTestDao.insertUserBatch(list);
	}
	
}
