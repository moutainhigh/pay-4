/**
 *  <p>File: CompositionQueryServiceImpl.java</p>
 *  <p>Description:</p>
 *  <p>Copyright: (c) 2004-2013 pay.com . All rights reserved.版权所有</p>
 *	<p>Company: </p>
 *  @author terry_ma
 *  @version 1.0  
 */
package com.pay.fundout.service.query.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.acc.service.account.constantenum.AcctTypeEnum;
import com.pay.fundout.dao.query.CommonQueryDao;
import com.pay.fundout.dto.OrderCondition;
import com.pay.fundout.dto.OrderInfo;
import com.pay.fundout.dto.OrderInfoDetail;
import com.pay.fundout.service.query.CommonQueryService;
import com.pay.inf.dao.Page;

public class DefaultQueryServiceImpl implements CommonQueryService {

	protected CommonQueryDao commonQueryDao;
	protected String sqlId;
	protected String detailSqlId;

	@Override
	public OrderInfoDetail queryOrderDetailByOrderId(Map<String, Object> map) {
		return commonQueryDao.queryOrderDetailByOrderId(detailSqlId, map);
	}

	@Override
	public Page<OrderInfo> queryOrdersByCondition(Page<OrderInfo> page,
			OrderCondition condition) {

		return commonQueryDao.queryOrdersByCondition(sqlId, page, condition);
	}

	@Override
	public Map<String, Object> queryOrderReturnMap(Page<OrderInfo> page,
			OrderCondition condition) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Page<OrderInfo> pageResult = commonQueryDao.queryOrdersByCondition(
				sqlId, page, condition);

		long income = 0;
		long paycount = 0;

		// 计算收支
		List<OrderInfo> orderInfos = pageResult.getResult();
		if (null != orderInfos && !orderInfos.isEmpty()) {
			for (OrderInfo orderInfo : orderInfos) {
				//TODO hard code
				if (orderInfo.getDealStatus().intValue() == 111) {
					if (orderInfo.getCrdr() == 1) {
						paycount += orderInfo.getRealpayAmount();
						paycount += orderInfo.getDealFee();
					} else {
						income += orderInfo.getRealpayAmount();
					}
					if(orderInfo.getDealType() == 2){
						paycount += orderInfo.getDealFee();
					}
				}
			}
		}
		//判断账户余额为是否为null 
		List<OrderInfo> result = pageResult.getResult();
		for (OrderInfo orderInfo : result) {
			if(orderInfo.getBalance()!=null){
				BigDecimal balance = new BigDecimal(orderInfo.getBalance()).divide(new BigDecimal(1000)).setScale(3,BigDecimal.ROUND_HALF_UP);
				orderInfo.setBalanceStr(balance.toString());
			}
			orderInfo.setCurrencyCode(AcctTypeEnum.getAcctCurrencyByCode(Integer.valueOf(orderInfo.getPayerAcctType())));//获取账户币种
		}
		resultMap.put("income", income);
		resultMap.put("paycount", paycount);
		resultMap.put("page", pageResult);
		return resultMap;
	}

	public void setCommonQueryDao(CommonQueryDao commonQueryDao) {
		this.commonQueryDao = commonQueryDao;
	}

	public void setSqlId(String sqlId) {
		this.sqlId = sqlId;
	}

	public void setDetailSqlId(String detailSqlId) {
		this.detailSqlId = detailSqlId;
	}

	@Override
	public List<OrderInfo> queryOrdersByCondition(OrderCondition condition) {
		List<OrderInfo> result = commonQueryDao.queryOrdersByCondition(sqlId, condition);
		for (OrderInfo orderInfo : result) {
			orderInfo.setCurrencyCode(AcctTypeEnum.getAcctCurrencyByCode(Integer.valueOf(orderInfo.getPayerAcctType())));//获取账户币种
		}
		return result;
	}
	
}
