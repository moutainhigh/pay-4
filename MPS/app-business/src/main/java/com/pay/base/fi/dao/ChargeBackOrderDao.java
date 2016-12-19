/**
 * 
 */
package com.pay.base.fi.dao;

import java.util.Map;

import com.pay.base.fi.model.ChargeBackMemberRelation;
import com.pay.inf.dao.BaseDAO;

/**
 * @author PengJiangbo
 *
 */
public interface ChargeBackOrderDao extends BaseDAO {

	void createRealtion(Map<String, Object> hMap) ;
	
	Integer countStatus(Map<String, Object> hMap) ;
	
	ChargeBackMemberRelation queryRelation(Map<String, Object> hMap) ;

}
