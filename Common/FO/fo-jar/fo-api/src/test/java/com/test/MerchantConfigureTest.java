/**
 *  File: MerchantConfigureTest.java
 *  Description:
 *  Copyright Corporation. All rights reserved.
 *  Date      Author      Changes
 *  Dec 15, 2011   ch-ma     Create
 *
 */
package com.test;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import com.pay.api.model.MerchantConfigure;
import com.pay.api.model.MerchantConfigureCriteria;
import com.pay.inf.dao.BaseDAO;

/**
 * 
 */
public class MerchantConfigureTest extends AbstractTestNG {

	@Resource(name = "merchantConfigureDao")
	private BaseDAO merchantConfigureDao;

	@Test
	public void testFind() {

		MerchantConfigureCriteria criteria = new MerchantConfigureCriteria();
		criteria.createCriteria().andMerchantCodeEqualTo("10000000001");
		MerchantConfigure merchantConfigure = (MerchantConfigure) merchantConfigureDao
				.findObjectByCriteria(criteria);
	}

}
