/** @Description 
 * @project 	fo-securitycheck
 * @file 		BusiDataDispatcher.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-11-1		Rick_lv			Create 
 */
package com.pay.fundout.securitycheck.riskdata;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.pay.fundout.securitycheck.model.Principal;
import com.pay.fundout.securitycheck.model.RiskData;

/**
 * <p>
 * 业务数据分配处理器
 * </p>
 * 
 * @author Rick_lv
 * @since 2010-11-1
 * @see
 */
public class RiskDataStatistDispatcher implements InitializingBean {
	private Map<String, Object> providers;
	private Map<String, RiskDataStatist> repositorys = new HashMap<String, RiskDataStatist>();
	protected Log logger = LogFactory.getLog(getClass());

	public RiskData dispatch(Principal principal, Object... otherParam) {
		String busiType = principal.getBusiType();
		RiskDataStatist riskDataStatist = repositorys.get(busiType);
		if (riskDataStatist != null) {
			return riskDataStatist.getRiskData(principal, otherParam);
		} else {
			logger.error("找不到指定业务类型的数据收集器 [" + principal + "]");
			return null;
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notEmpty(providers, "必须指定业务数据收集器");

		for (String busiType : providers.keySet()) {
			Object temp = providers.get(busiType);
			if (temp instanceof RiskDataStatist) {
				String[] busiTypeArry = busiType.split("[,]");
				for (String busiTypeSignal : busiTypeArry) {
					repositorys.put(busiTypeSignal, (RiskDataStatist) temp);
				}
			} else {
				logger.warn("非法类型的业务数据收集器,系统将忽略此业务类型的业务数据收集器[busiType=" + busiType + ",className=" + temp.getClass() + "]");
			}
		}
		logger.info("成功加载业务数据收集器 : \n" + repositorys);
	}

	public void setProviders(Map<String, Object> providers) {
		this.providers = providers;
	}

}
