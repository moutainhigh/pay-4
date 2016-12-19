/**
 * 
 */
package com.pay.batchpay.dao.merchant.impl;


import com.pay.batchpay.dao.merchant.MerchantDao;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * @author PengJiangbo
 *
 */
public class MerchantDaoImpl extends BaseDAOImpl implements MerchantDao {

	@Override
	public String findMerchantNameByMemberCode(long memberCode) {
		return (String) this.getSqlMapClientTemplate().queryForObject(this.getNamespace().concat("findMerchantNameByMemberCode"), memberCode) ;
	}

}
