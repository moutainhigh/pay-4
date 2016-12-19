/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.txncore.handler.bounced;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.commons.BouncedEnum;
import com.pay.txncore.model.BouncedOrderVO;
import com.pay.txncore.model.ChargeBackOrder;
import com.pay.txncore.model.PartnerSettlementOrder;
import com.pay.txncore.service.chargeback.ChargeBackOrderService;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;
import com.pay.util.StringUtil;

/**
 * 拒付清算
 * 资金状态：1直接扣款，2已扣款，3冻结，4已冻结，5解冻，6已解冻，7申诉失败扣款，8申诉失败扣款成功
 *  File: BouncedLiquidationHandler.java
 *  Description:
 *  Copyright 2016-2030 IPAYLINKS Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2016年5月24日   mmzhang     Create
 *
 */
public class BouncedLiquidationHandler implements EventHandler {

	public final Log logger = LogFactory
			.getLog(BouncedLiquidationHandler.class);
	private ChargeBackOrderService chargeBackOrderService;

	public void setChargeBackOrderService(
			ChargeBackOrderService chargeBackOrderService) {
		this.chargeBackOrderService = chargeBackOrderService;
	}

	private int maxSize = 100;

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map paraMap = null;
		Map<String,String> resultMap = new HashMap<String,String>();

		paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());
		String[] amountTypes = new String[]{
				BouncedEnum.amountType1.getType(),
				BouncedEnum.amountType3.getType(),
				BouncedEnum.amountType5.getType(),
				BouncedEnum.amountType7.getType()
				};
		
		String[] amountTypes2 = new String[]{
				BouncedEnum.amountType4.getType()
				};
		String[] cpTypes = new String[]{
				BouncedEnum.bouncedType1.getType(),
				BouncedEnum.bouncedType2.getType()
		};
		//不申诉和申诉失败
		String[] statuss = new String[]{
				"7","5"
		};
		//accountingFlg 是否扣款记账：0登记未记账，1-登记不够扣款，任务循环扣款 3

		List<BouncedOrderVO> bouncedOrders = 
				chargeBackOrderService.queryBouncedOrders(new Date(),amountTypes,1);
		
		//调单 已经冻结的 不申诉180天，申诉失败180后解冻
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("overdays", 180); //
		map.put("amountTypes", amountTypes2); 
		map.put("statuss", statuss);     //审批状态未处理
		map.put("cpTypes", cpTypes);  //调单
		List<BouncedOrderVO> bouncedOrders1 = chargeBackOrderService.queryBouncedOrders(map);
		
		//大于最后回复日期未处理 不申诉
		Map<String,Object> map2 = new HashMap<String,Object>();
		map2.put("days", 0); //
		map2.put("status", 0);     //审批状态未处理
		List<BouncedOrderVO> bouncedOrders2 = chargeBackOrderService.queryBouncedOrders(map2);
		
		List<BouncedOrderVO> bouncedOrders3=null;
		//调单需要解冻的	
		Map<String,Object> map3 = new HashMap<String,Object>();
			map3.put("cpTypes", cpTypes);  //调单
			map3.put("amountTypes", amountTypes2); 
			map3.put("accountingFlg", 2); 
			// 2如果该订单，以前为银行调单或内部调单，现在为拒付,直接解冻
			bouncedOrders3 = chargeBackOrderService.queryBouncedOrders(map3);
		List<BouncedOrderVO> bouncedOrdersAll=new ArrayList<BouncedOrderVO>();
		
		
		if (null != bouncedOrders
				&& !bouncedOrders.isEmpty()) {
			logger.info("拒付记账处理开始,bouncedOrders="+bouncedOrders.size());
			for (BouncedOrderVO bouncedOrderVO : bouncedOrders) {
				try {
					logger.info("拒付记账处理，拒付订单号: "+bouncedOrderVO.getOrderId());
					chargeBackOrderService.liquidationRnTx(bouncedOrderVO,false);
				} catch (Exception e) {
					logger.info("BouncedLiquidationHandler-method: handle []"+e.getMessage());
				}
			}
			logger.info("拒付记账处理结束！");
		}
		if (null != bouncedOrders3
				&& !bouncedOrders3.isEmpty())
		{
			bouncedOrdersAll=bouncedOrders1;
			bouncedOrdersAll.addAll(bouncedOrders3);
		}
		if (null != bouncedOrdersAll
				&& !bouncedOrdersAll.isEmpty()) {
			
			logger.info("已经冻结的调单数据不申诉或申诉失败180后解冻处理开始,bouncedOrders1="+bouncedOrders1.size());
			for (BouncedOrderVO bouncedOrderVO : bouncedOrdersAll) {
				try {
					logger.info("调单解冻处理，拒付订单号: "+bouncedOrderVO.getOrderId());
					chargeBackOrderService.freeFrozenRnTx(bouncedOrderVO);
				} catch (Exception e) {
					logger.info("BouncedLiquidationHandler-method: handle []"+e.getMessage());
				}
			}
			logger.info("已经冻结的调单数据不申诉或申诉失败180后解冻处理结束");
		}
		if (null != bouncedOrders2
				&& !bouncedOrders2.isEmpty()) {
			logger.info("更新超过最晚回复时间的为不申诉开始,bouncedOrders2="+bouncedOrders2.size());
			for (BouncedOrderVO bouncedOrderVO : bouncedOrders2) {
				try {
					logger.info("更新不申诉处理，拒付订单号: "+bouncedOrderVO.getOrderId());
					chargeBackOrderService.alertStatus(bouncedOrderVO);
				} catch (Exception e) {
					logger.info("BouncedLiquidationHandler-method: handle []"+e.getMessage());
				}
			}
			logger.info("更新超过最晚回复时间的为不申诉结束");
		}

		resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
		resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
		return JSonUtil.toJSonString(resultMap);
	}
}
