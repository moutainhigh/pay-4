/**
 * 
 */
package com.pay.txncore.crosspay.dao;

import java.util.Map;

import com.pay.inf.dao.BaseDAO;

/**
 * @author chaoyue
 * 
 */
public interface PartnerClearCycleDAO extends BaseDAO {

	boolean updatePartnerWithDraw(Map<String, Object> paramMap);
}
