/**
 *  File: OrderCenterRefundServiceImpl.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-11-8      Sunsea.Li      Changes
 *  
 */
package com.pay.poss.service.ordercenter.fundout.impl.refund;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.exception.MaAccountQueryUntxException;
import com.pay.acc.exception.MaMemberQueryException;
import com.pay.acc.service.member.dto.MemberBaseInfoBO;
import com.pay.inf.dao.Page;
import com.pay.poss.dao.ordercenter.fundout.refund.OrderCenterRefundDao;
import com.pay.poss.service.ordercenter.common.AbstractOrderCenterCommonService;
import com.pay.poss.service.ordercenter.dto.detail.OrderDetailDTO;
import com.pay.poss.service.ordercenter.dto.list.OrderCenterQueryDTO;
import com.pay.poss.service.ordercenter.dto.list.OrderCenterResultDTO;

/**<p>查询充退订单结果列表</p>
 * @author Sunsea.Li
 *
 */
public class OrderCenterRefundServiceImpl extends AbstractOrderCenterCommonService{
	private static Log logger = LogFactory.getLog(OrderCenterRefundServiceImpl.class);
	
	private OrderCenterRefundDao orderCenterRefundDao;
	
	public void setOrderCenterRefundDao(OrderCenterRefundDao orderCenterRefundDao) {
		this.orderCenterRefundDao = orderCenterRefundDao;
	}

	@Override
	public Map<String, Page<OrderCenterResultDTO>> queryResultList(
			Page<OrderCenterResultDTO> page, OrderCenterQueryDTO dto) {
		Map<String,Page<OrderCenterResultDTO>> map = new HashMap<String,Page<OrderCenterResultDTO>>();
		map.put("page", orderCenterRefundDao.queryRefundResultList(page, dto));
		return map;
	}

	@Override
	public OrderDetailDTO queryOrderDetail(OrderCenterQueryDTO queryDto) {
		Long orderKy = Long.valueOf(queryDto.getOrderKy());
		Long memberCode = Long.valueOf(StringUtils.isEmpty(queryDto.getPayerMemberCode()) ? "0" : queryDto.getPayerMemberCode());
		Integer accountType = Integer.valueOf(StringUtils.isEmpty(queryDto.getPayerAccType()) ? "10" : queryDto.getPayerAccType());
		OrderDetailDTO orderDetailDTO = null;
		MemberBaseInfoBO memberBaseInfoBO = null;
		AcctAttribDto acctAttribDto = null;
		try {
			orderDetailDTO = orderCenterRefundDao.queryRefundOrderDetail(orderKy);
			memberBaseInfoBO =  memberQueryService.queryMemberBaseInfoByMemberCode(memberCode);
			acctAttribDto = accountQueryService.doQueryAcctAttribNsTx(memberCode,accountType);
			
			orderDetailDTO.setPayerKy(queryDto.getPayerMemberCode()); //付款人用户号
			orderDetailDTO.setPayerName(memberBaseInfoBO.getName()); //付款人用户 姓名
			orderDetailDTO.setPayerLevel(String.valueOf(memberBaseInfoBO.getServiceLevelCode()));//付款人用户等级
			orderDetailDTO.setPayerId(memberBaseInfoBO.getLoginName());//付款人用户标识
			orderDetailDTO.setPayerAcctType(String.valueOf(10));//付款人账户类型
			orderDetailDTO.setPayerAcct(acctAttribDto.getAcctCode());//付款人账户
			
		} catch (MaMemberQueryException e) {
			logger.error(e.getMessage(), e);
		}catch (MaAccountQueryUntxException e) {
			logger.error(e.getMessage(), e);
		}
		return orderDetailDTO;
	}

	@Override
	public Map<String, Object> queryOrderRelation(OrderCenterQueryDTO queryDto) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("relationList", orderCenterRefundDao.queryRefundRelationList(queryDto));
		return map;
	}
}
