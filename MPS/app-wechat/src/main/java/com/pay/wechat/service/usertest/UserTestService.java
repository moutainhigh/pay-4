/**
 * 
 */
package com.pay.wechat.service.usertest;

import java.util.List;

import com.pay.wechat.model.UserTest;

/**
 * @author PengJiangbo
 *
 */
public interface UserTestService {
	
	/**
	 * 
	 * @param userTest
	 * @return
	 */
	public void insertUser(UserTest userTest) ;
	
	/**
	 * 
	 * @param userTest
	 * @return
	 */
	public long insertUserWidthBack(UserTest userTest) ;
	
	/**
	 * 批量插入
	 * @param list
	 */
	public void insertUserBatch(List list) ;
}
