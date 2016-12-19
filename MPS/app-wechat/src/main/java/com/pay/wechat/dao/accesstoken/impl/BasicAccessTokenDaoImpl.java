/**
 * 
 */
package com.pay.wechat.dao.accesstoken.impl;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.wechat.dao.accesstoken.BasicAccessTokenDao;

/**
 * @author PengJiangbo
 *
 */
public class BasicAccessTokenDaoImpl extends BaseDAOImpl implements
		BasicAccessTokenDao {

	@Override
	public void insertOrUpdate(String accessToken) {
		this.getSqlMapClientTemplate().queryForObject(this.getNamespace().concat("insertOrUpdate"), accessToken) ;
	}

}
