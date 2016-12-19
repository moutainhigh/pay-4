/** @Description 
 * @project 	fo-securitycheck
 * @file 		RiskDataDetail.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-11-2		Rick_lv			Create 
 */
package com.pay.fundout.securitycheck.riskdata.spi;

import java.util.HashMap;
import java.util.Map;

import com.pay.fundout.securitycheck.model.Principal;
import com.pay.fundout.securitycheck.model.RiskData;
import com.pay.fundout.securitycheck.riskdata.AbstractRiskDataStatist;

/**
 * <p>
 * 基础风控数据提取器
 * </p>
 * 
 * @author Rick_lv
 * @since 2010-11-2
 * @see
 */
public class Pay2AcctRiskDataStatist extends AbstractRiskDataStatist {
	private Map<String, String> busiTypeMapping = new HashMap<String, String>();
	 public Pay2AcctRiskDataStatist(){
	  busiTypeMapping.put("20", "20");
	  busiTypeMapping.put("21", "21");
	  busiTypeMapping.put("22", "23");
	  busiTypeMapping.put("23", "23");
	  busiTypeMapping.put("24", "23");
	 }

	@Override
	public RiskData getRiskData(Principal principal, Object... otherParam) {
		
		String type = (String) otherParam[0];
		Map<String,Object>  map=new HashMap<String,Object>(2);
		map.put("payerMemberCode", principal.getPayerMemberCode());
		map.put("payerMemberType", principal.getPayerMemberType());
		map.put("payeeMemberType", principal.getPayeeMemberType());
		map.put("busiType",busiTypeMapping.get(principal.getBusiType()));

		if (type.equals("BY_DAY")) {
			return (RiskData) daoService.findObjectByCriteria("securitycheck.pay2acct.queryRiskOfDay", map);
		} else if (type.equals("BY_MONTH")) {
			return (RiskData) daoService.findObjectByCriteria("securitycheck.pay2acct.queryRiskOfMonth", map);
		} else {
			return null;
		}
	}
}
