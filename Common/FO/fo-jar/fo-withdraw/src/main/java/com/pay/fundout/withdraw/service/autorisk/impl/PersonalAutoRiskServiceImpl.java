package com.pay.fundout.withdraw.service.autorisk.impl;

import com.pay.fo.order.dto.base.FundoutOrderDTO;

/**
 * 个人出款自动过风控规则
 * @author meng.li
 *
 */
public class PersonalAutoRiskServiceImpl extends AbstractAutoRiskServiceImpl {

	@Override
	boolean m1(FundoutOrderDTO order) {
		return true;
	}

	@Override
	boolean m2(FundoutOrderDTO order) {
		return true;
	}

	@Override
	boolean m3(FundoutOrderDTO order) {
		return true;
	}
	
	@Override
	boolean m4(FundoutOrderDTO order) {
		return true;
	}
	
}
