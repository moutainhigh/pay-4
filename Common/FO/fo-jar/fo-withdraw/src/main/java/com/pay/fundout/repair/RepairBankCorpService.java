/**
 *  File: RepairBankCorpService.java
 *  Description:
 *  Copyright 2006-2012 pay Corporation. All rights reserved.
 *  Author   Changes     Date               
 *  Sandy    Create      2013-3-13         
 *
 */
package com.pay.fundout.repair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.pay.fo.bankcorp.model.BankChannelOrderCriteria;
import com.pay.fundout.withdraw.service.flowprocess.WithdrawService;
import com.pay.poss.base.exception.PossException;

/**		
 *  @author Sandy
 *  @Date 2013-3-13下午2:37:05
 *  @Description 银企直联修复
 */
public class RepairBankCorpService extends BaseRepairService{
	
	private WithdrawService withdrawService;
	
	@Override
	public Object getOrder(String orderId) {
		if (StringUtils.isBlank(orderId)) {
			return null;
		}
		Object obj = null;
		try {
			obj = fundoutOrderService.getOrder(Long.valueOf(orderId));
		}catch(Exception e) {
			logger.error("根据订单流水查询订单异常",e);
		}
		return obj;
	}

	/* (non-Javadoc)
	 * @see com.pay.fundout.repair.BaseRepairService#getOrder(java.lang.String)
	 */
	@Override
	public Object getWorkOrder(String orderId) {
		if (StringUtils.isBlank(orderId)) {
			return null;
		}
		return withdrawWorkDao.queryWorkOrderById("WF.queryWorkOrderByOrderId", Long.valueOf(orderId));
	}

	/* (non-Javadoc)
	 * @see com.pay.fundout.repair.BaseRepairService#getChannelOrder(java.lang.String)
	 */
	@Override
	public Object getChannelOrder(String orderId) {
		if (StringUtils.isBlank(orderId)) {
			return null;
		}
		BankChannelOrderCriteria criteria = new BankChannelOrderCriteria();
		criteria.createCriteria().andTradeOrderIdEqualTo(Long.valueOf(orderId));
		return bankChannelDao.findObjectByCriteria(criteria);
	}

	@Override
	public boolean repairOrder(String orderId) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("orderId", orderId);
		map.put("isSuccess", "1");
		try {
			return withdrawService.bankBackRdTx(map);
		} catch (PossException e) {
			logger.error("补单失败，订单号：" + orderId,e);
		}
		return false;
	}
	
	public void setWithdrawService(WithdrawService withdrawService) {
		this.withdrawService = withdrawService;
	}

	/* (non-Javadoc)
	 * @see com.pay.fundout.repair.BaseRepairService#getOrdersForRepair()
	 */
	@Override
	public List<String> getOrdersForRepair() {
		return baseDao.findByQuery("repairBankCorp.selectForRepair", new Integer(0));
	}

}
