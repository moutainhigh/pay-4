/**
 *  File: BaseRepairService.java
 *  Description:
 *  Copyright 2006-2012 pay Corporation. All rights reserved.
 *  Author   Changes     Date               
 *  Sandy    Create      2013-3-13         
 *
 */
package com.pay.fundout.repair;

import java.io.StringWriter;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonGenerator.Feature;
import org.codehaus.jackson.map.ObjectMapper;

import com.pay.fo.bankcorp.model.BankChannelOrder;
import com.pay.fo.order.service.base.FundoutOrderService;
import com.pay.fundout.withdraw.dao.flowprocess.WithdrawAuditWorkOrderDao;
import com.pay.inf.dao.BaseDAO;

/**
 * @author Sandy
 * @Date 2013-3-13上午11:42:07
 * @Description
 */
public abstract class BaseRepairService {

	protected FundoutOrderService fundoutOrderService;

	protected WithdrawAuditWorkOrderDao withdrawWorkDao;

	protected BaseDAO<BankChannelOrder> bankChannelDao;

	protected BaseDAO baseDao;

	protected transient Log logger = LogFactory.getLog(getClass());

	/**
	 * 查询订单信息
	 * 
	 * @param orderId
	 * @return
	 */
	public abstract Object getOrder(String orderId);

	public abstract Object getWorkOrder(String orderId);

	/**
	 * 查询渠道订单信息
	 * 
	 * @param orderId
	 * @return
	 */
	public abstract Object getChannelOrder(String orderId);

	/**
	 * 补单
	 * 
	 * @param orderId
	 * @return
	 */
	public abstract boolean repairOrder(String orderId);

	/**
	 * 查询需要补单的订单号
	 * 
	 * @return
	 */
	public abstract List<String> getOrdersForRepair();

	/**
	 * 查询订单所有信息
	 * 
	 * @param orderId
	 * @return
	 */
	public String getOrderInfos(String orderId) {
		Object objs[] = new Object[3];
		objs[0] = getOrder(orderId);
		objs[1] = getWorkOrder(orderId);
		objs[2] = getChannelOrder(orderId);

		return toJSonString(objs);
	}

	/**
	 * 修复了数字没加引号
	 * 
	 * @param obj
	 * @return
	 */
	public static String toJSonString(Object obj) {
		ObjectMapper mapper = new ObjectMapper();
		StringWriter out = new StringWriter();
		try {
			mapper.configure(Feature.WRITE_NUMBERS_AS_STRINGS, true);
			mapper.writeValue(out, obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return out.toString();
	}

	/**
	 * @param fundoutOrderService
	 *            the fundoutOrderService to set
	 */
	public void setFundoutOrderService(FundoutOrderService fundoutOrderService) {
		this.fundoutOrderService = fundoutOrderService;
	}

	public void setWithdrawWorkDao(WithdrawAuditWorkOrderDao withdrawWorkDao) {
		this.withdrawWorkDao = withdrawWorkDao;
	}

	public void setBankChannelDao(BaseDAO<BankChannelOrder> bankChannelDao) {
		this.bankChannelDao = bankChannelDao;
	}

	public void setBaseDao(BaseDAO<Object> baseDao) {
		this.baseDao = baseDao;
	}
}
