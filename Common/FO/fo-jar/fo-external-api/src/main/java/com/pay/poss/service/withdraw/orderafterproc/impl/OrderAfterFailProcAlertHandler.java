/** @Description 
 * @project 	poss-external-api
 * @file 		OrderAfterFailProcHandler.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-10-25		Rick_lv			Create 
 */
package com.pay.poss.service.withdraw.orderafterproc.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.dao.BaseDAO;
import com.pay.poss.dto.withdraw.order.OrderFailProcAlertModel;
import com.pay.poss.service.withdraw.orderafterproc.OrderAfterFailProcHandler;

/**
 * <p>
 * 订单后处理失败处理服务的预警实现
 * </p>
 * 
 * @author Rick_lv
 * @since 2010-10-25
 * @see
 */
public class OrderAfterFailProcAlertHandler implements
		OrderAfterFailProcHandler {

	private BaseDAO daoService;
	private Log log = LogFactory.getLog(getClass());

	@Override
	public void execute(OrderFailProcAlertModel alertModel) {
		if (alertModel != null) {
			try {
				daoService.create("innerBackFund.inserOrderFailProc",
						alertModel);
			} catch (Exception e) {
				log.error("订单处理失败，写入预警记录失败 [" + alertModel + "]", e);
			}
		}
	}

	public void setDaoService(BaseDAO daoService) {
		this.daoService = daoService;
	}

}
