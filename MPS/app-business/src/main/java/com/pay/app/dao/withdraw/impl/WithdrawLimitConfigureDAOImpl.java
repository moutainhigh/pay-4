/**
 *  File: WithdrawLimitConfigureDAOImpl.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-8-13   terry_ma     Create
 *
 */
package com.pay.app.dao.withdraw.impl;

import java.util.HashMap;
import java.util.Map;

import com.pay.app.dao.withdraw.WithdrawLimitConfigureDAO;
import com.pay.app.model.WithdrawLimitConfigure;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * 
 */
public class WithdrawLimitConfigureDAOImpl extends BaseDAOImpl implements
		WithdrawLimitConfigureDAO {

	private static final int COMMON = 0;
	private static final int AGENT = 1;

	@Override
	public WithdrawLimitConfigure queryConfigureByMemberCode(
			final Long memberCode) {

		Map paraMap = new HashMap();
		paraMap.put("type", AGENT);
		paraMap.put("memberCode", memberCode);

		WithdrawLimitConfigure model = (WithdrawLimitConfigure) super
				.getSqlMapClientTemplate().queryForObject(
						namespace.concat("findByMemberCodeAndType"), paraMap);

		if (null == model) {

			paraMap.put("type", COMMON);
			paraMap.remove("memberCode");
			model = (WithdrawLimitConfigure) super.getSqlMapClientTemplate()
					.queryForObject(
							namespace.concat("findByMemberCodeAndType"),
							paraMap);

		}

		return model;
	}
}
