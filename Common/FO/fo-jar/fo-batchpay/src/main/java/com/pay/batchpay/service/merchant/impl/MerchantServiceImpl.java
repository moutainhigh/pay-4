/**
 * 
 */
package com.pay.batchpay.service.merchant.impl;

import com.pay.batchpay.dao.merchant.MerchantDao;
import com.pay.batchpay.service.merchant.MerchantService;

/**
 * @author PengJiangbo
 *
 */
public class MerchantServiceImpl implements MerchantService {

	//注入
	private MerchantDao merchantDao ;
	
	public void setMerchantDao(MerchantDao merchantDao) {
		this.merchantDao = merchantDao;
	}

	@Override
	public String findMerchantNameByMemberCode(long memberCode) {
		return this.merchantDao.findMerchantNameByMemberCode(memberCode) ;
	}

}
