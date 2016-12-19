/**
 *  File: GatewayRequestDAO.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-19   Terry Ma    Create
 *
 */
package com.pay.app.dao.gateway;

import java.util.Map;

import com.pay.app.model.DealRequest;
import com.pay.inf.dao.BaseDAO;

/**
 * 
 */
public interface GatewayRequestDAO extends BaseDAO {

	/**
	 * 
	 * @param serialNo
	 * @return
	 */
	DealRequest queryDealRequestBySerialNo(final String serialNo);

	/**
	 * 
	 * @param memberCode
	 * @return totalAmount 总金额，totalCount 总记录数
	 */
	Map queryDealCountByMemberCodeAndType(final String memberCode,
			final Integer orderCode);
}
