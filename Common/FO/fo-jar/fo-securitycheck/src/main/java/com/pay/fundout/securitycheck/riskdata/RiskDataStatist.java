/** @Description 
 * @project 	fo-securitycheck
 * @file 		BusiDataDetail.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-10-30		Rick_lv			Create 
 */
package com.pay.fundout.securitycheck.riskdata;

import com.pay.fundout.securitycheck.model.Principal;
import com.pay.fundout.securitycheck.model.RiskData;

/**
 * <p>
 * 业务数据接口
 * </p>
 * 
 * @author Rick_lv
 * @since 2010-10-30
 * @see
 */
public interface RiskDataStatist {
	public RiskData getRiskData(Principal principal, Object... otherParam);
}
