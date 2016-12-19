/**
 * 
 */
package com.pay.pe.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.pe.dao.BankOrgCodeMappingDAO;
import com.pay.pe.model.BankOrgCodeMapping;
import com.pay.util.StringUtil;

/**
 * @author chaoyue
 *
 */
public class BankOrgCodeMappingDAOImpl extends BaseDAOImpl<BankOrgCodeMapping>
		implements BankOrgCodeMappingDAO {

	private final Log logger = LogFactory.getLog(getClass());

	@Override
	public BankOrgCodeMapping findMapping(Integer orderCode, Integer dealCode,
			Integer dependOn, String bankCode, String currencyCode,
			String businessType) {

		Map paraMap = new HashMap();
		paraMap.put("orderCode", orderCode);
		paraMap.put("dealCode", dealCode);
		paraMap.put("dependOn", dependOn);
		paraMap.put("bankCode", bankCode);

		paraMap.put("currencyCode", currencyCode);

		if (!StringUtil.isEmpty(businessType)) {
			paraMap.put("businessType", businessType);
		}

		return super.findObjectByCriteria("queryBankOrgCodeMapping", paraMap);
	}

	@Override
	public BankOrgCodeMapping findOrgCode(String bankCode, String currencyCode,
			Integer psCode, Integer partType) {

		if (logger.isInfoEnabled()) {
			logger.info("psCode:" + psCode + "-bankCode:" + bankCode
					+ "-currencyCode:" + currencyCode + "-dependOn:" + partType);
		}

		Map paraMap = new HashMap();
		paraMap.put("psCode", psCode);
		paraMap.put("dependOn", partType);
		paraMap.put("bankCode", bankCode);
		paraMap.put("currencyCode", currencyCode);
		List<BankOrgCodeMapping> list = super.findByCriteria(
				"queryBankOrgCodeMapping", paraMap);

		if (null != list && !list.isEmpty()) {
			return list.get(0);
		} else {
			return null;
		}
	}

}
