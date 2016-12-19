/**
 * 
 */
package com.pay.wechat.dao.userTest;

import java.util.List;

import com.pay.wechat.model.UserTest;

/**
 * @author PengJiangbo
 *
 */
public interface UserTestDao {
	
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
