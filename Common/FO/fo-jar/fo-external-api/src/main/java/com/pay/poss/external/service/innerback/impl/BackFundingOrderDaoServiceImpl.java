package com.pay.poss.external.service.innerback.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.dao.BaseDAO;
import com.pay.poss.base.common.Constants;
import com.pay.poss.dto.withdraw.order.BackFundmentOrder;
import com.pay.poss.external.service.innerback.BackFundingOrderDaoService;

public class BackFundingOrderDaoServiceImpl implements
		BackFundingOrderDaoService {

	private BaseDAO daoService;
	private Log log = LogFactory.getLog(BackFundingOrderDaoServiceImpl.class);

	public boolean saveBackFundingOrderRnTx(BackFundmentOrder backOrder) {
		try {
			backOrder.setStatus(Constants.ORDER_STATUS_INIT);
			daoService.create("innerBackFund.inserInnerBackFundingOrder",
					backOrder);
			return true;
		} catch (Exception e) {
			log.error("写入退款订单失败 [" + backOrder + "]", e);
			return false;
		}
	}

	public boolean updateBackFundingOrderRdTx(BackFundmentOrder backOrder) {
		try {
			backOrder.setStatus(Constants.ORDER_STATUS_SUCC);
			daoService.update(
					"innerBackFund.updateInnerBackFundingOrderStatus",
					backOrder);
			return true;
		} catch (Exception e) {
			log.error("更新退款订单失败 [" + backOrder + "]", e);
			return false;
		}
	}

	public void setDaoService(BaseDAO daoService) {
		this.daoService = daoService;
	}

	@Override
	public boolean updateBackFundingOrderRnTx(BackFundmentOrder backOrder) {
		try {
			daoService.update(
					"innerBackFund.updateInnerBackFundingOrderStatus",
					backOrder);
			return true;
		} catch (Exception e) {
			log.error("更新退款订单失败 [" + backOrder + "]", e);
			return false;
		}
	}

}
