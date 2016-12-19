/**
 * 
 */
package com.pay.base.fi.service.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import com.pay.base.fi.dao.ChargeBackOrderDao;
import com.pay.base.fi.model.ChargeBackMemberRelation;
import com.pay.base.fi.service.ChargeBackOrderService;

/**
 * @author PengJiangbo
 *
 */
public class ChargeBackOrderServiceImpl implements ChargeBackOrderService {

	//注入
	private ChargeBackOrderDao chargeBackOrderDao ;
	
	/* (non-Javadoc)
	 * @see com.pay.base.fi.service.ChargeBackOrderService#createRealtion(java.util.Map)
	 */
	@Override
	public void createRealtionRnTx(Map<String, Object> hMap) {
		Map<String, Object> relationMap = new HashMap<String, Object>() ;
		relationMap.put("orderId", hMap.get("orderId")) ;
		ChargeBackMemberRelation queryRelation = this.queryRelation(hMap) ;
		//关联关系不存在则创建
		if(null == queryRelation){
			Timestamp createTime = new Timestamp(System.currentTimeMillis()) ;
			hMap.put("createTime", createTime) ;
			this.chargeBackOrderDao.createRealtion(hMap);
		}
	}

	/* (non-Javadoc)
	 * @see com.pay.base.fi.service.ChargeBackOrderService#countStatus(java.util.Map)
	 */
	@Override
	public Integer countStatus(Map<String, Object> hMap) {
		return this.chargeBackOrderDao.countStatus(hMap);
	}

	public void setChargeBackOrderDao(ChargeBackOrderDao chargeBackOrderDao) {
		this.chargeBackOrderDao = chargeBackOrderDao;
	}

	/* (non-Javadoc)
	 * @see com.pay.base.fi.service.ChargeBackOrderService#queryRelation(java.util.Map)
	 */
	@Override
	public ChargeBackMemberRelation queryRelation(Map<String, Object> hMap) {
		return this.chargeBackOrderDao.queryRelation(hMap) ;
	}
	
	
}
