package com.pay.poss.security.service.impl;

import com.pay.poss.security.commons.SecurityConstants;
import com.pay.poss.security.service.ISecurityStrategyService;

public class StaticSecurityStrategyServiceImpl implements
		ISecurityStrategyService {

	@Override
	public int getMaxFailedLoginCount(String loginId) throws Exception {
		return SecurityConstants.DEFAULT_SEC_STRATEGY_MAX_FAILED_LOGIN_COUNT;
	}

	@Override
	public int getPasswordValidDays(String loginId) throws Exception {
		return SecurityConstants.DEFAULT_SEC_STRATEGY_PASSWORD_VALID_DAYS;
	}

}
