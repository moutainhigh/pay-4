/**
 * 
 */
package com.pay.pe.dao;

import com.pay.inf.dao.BaseDAO;
import com.pay.pe.model.BankOrgCodeMapping;

/**
 * @author chaoyue
 *
 */
public interface BankOrgCodeMappingDAO extends BaseDAO<BankOrgCodeMapping> {

	/**
	 * 
	 * @param bankCode
	 * @param currencyCode
	 * @param businessType
	 * @return
	 */
	BankOrgCodeMapping findMapping(Integer orderCode, Integer dealCode,
			Integer dependOn, String bankCode, String currencyCode,
			String businessType);

	/**
	 * 根据psCode和partType获取orgCode
	 * 
	 * @param psCode
	 * @param partType
	 * @return
	 */
	BankOrgCodeMapping findOrgCode(String bankCode, String currencyCode,
			Integer psCode, Integer partType);
}
