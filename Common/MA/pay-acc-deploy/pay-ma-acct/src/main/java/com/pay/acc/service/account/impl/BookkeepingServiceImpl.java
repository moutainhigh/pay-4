package com.pay.acc.service.account.impl;

import com.pay.acc.deal.dao.BalanceDealDAO;
import com.pay.acc.deal.dto.BalanceDealSimpleDto;
import com.pay.acc.service.account.BookkeepingService;

public class BookkeepingServiceImpl implements BookkeepingService {

	private BalanceDealDAO balanceDealDAO = null;

	@Override
	public boolean isChargeSuccess(String orderId, Integer dealCode,
			Integer dealType) {

		BalanceDealSimpleDto deal = balanceDealDAO
				.queryBalanceDealByOrderidAndDealCode(orderId, dealCode,
						dealType);
		if (deal != null) {
			// if(deal.getChargeupStatus()==ChargeUpStatusEnum.CHARGEUP.getCode()){
			return true;
			// }
			// return false;
		}
		return false;
	}

	public boolean isChargeSuccess() {
		return false;
	}

	public void setBalanceDealDAO(BalanceDealDAO balanceDealDAO) {
		this.balanceDealDAO = balanceDealDAO;
	}

}