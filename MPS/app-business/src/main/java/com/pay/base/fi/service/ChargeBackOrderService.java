/**
 * 
 */
package com.pay.base.fi.service;

import java.util.Map;

import com.pay.base.fi.model.ChargeBackMemberRelation;

/**
 * @author PengJiangbo
 *
 */
public interface ChargeBackOrderService {
	
	void createRealtionRnTx(Map<String, Object> hMap) ;
	
	Integer countStatus(Map<String, Object> hMap) ;
	
	ChargeBackMemberRelation queryRelation(Map<String, Object> hMap) ;
	
}
