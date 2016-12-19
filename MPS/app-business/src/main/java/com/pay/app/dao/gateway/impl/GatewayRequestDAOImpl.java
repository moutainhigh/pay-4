/**
 *  File: GatewayRequestDAOImpl.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-19   Terry Ma    Create
 *
 */
package com.pay.app.dao.gateway.impl;

import java.util.HashMap;
import java.util.Map;

import com.pay.app.dao.gateway.GatewayRequestDAO;
import com.pay.app.model.DealRequest;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * 
 */
public class GatewayRequestDAOImpl extends BaseDAOImpl implements
		GatewayRequestDAO {

	/**
	 * 
	 * @param serialNo
	 * @return
	 */
	public DealRequest queryDealRequestBySerialNo(final String serialNo) {

		return (DealRequest) super.getSqlMapClientTemplate().queryForObject(
				namespace.concat("findBySerialNo"), serialNo);
	}

	/**
	 * 
	 * @param memberCode
	 * @return totalAmount 总金额，totalCount 总记录数
	 */
	public Map queryDealCountByMemberCodeAndType(final String memberCode,
			final Integer orderCode) {

		Map paraMap = new HashMap();
		paraMap.put("partner", memberCode);
		paraMap.put("orderCode", orderCode);

		return (Map) super.getSqlMapClientTemplate().queryForObject(
				namespace.concat("findByMemberCode"), paraMap);
	}
}
