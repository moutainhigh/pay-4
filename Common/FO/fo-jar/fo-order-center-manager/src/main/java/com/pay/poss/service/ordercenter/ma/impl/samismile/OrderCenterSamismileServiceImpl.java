package com.pay.poss.service.ordercenter.ma.impl.samismile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.dao.Page;
import com.pay.pe.dto.AccountEntryDTO;
import com.pay.pe.service.PEService;
import com.pay.poss.dao.ordercenter.ma.samismile.OrderCenterSamismileDao;
import com.pay.poss.service.ordercenter.common.AbstractOrderCenterCommonService;
import com.pay.poss.service.ordercenter.common.Constants;
import com.pay.poss.service.ordercenter.dto.detail.OrderDetailDTO;
import com.pay.poss.service.ordercenter.dto.entrie.OrderCenterEntrieDTO;
import com.pay.poss.service.ordercenter.dto.list.OrderCenterQueryDTO;
import com.pay.poss.service.ordercenter.dto.list.OrderCenterResultDTO;
import com.pay.poss.service.ordercenter.dto.relationorder.OrderRelationDTO;

/**
 * 
 * @Description 
 * @project 	fo-order-center-manager
 * @file 		OrderCenterSamismileServiceImpl.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright 2006-2010 pay Corporation. All rights reserved.
 * Date				Author			Changes
 * 2010-12-09		gungun_zhang			Create
 */
public class OrderCenterSamismileServiceImpl extends AbstractOrderCenterCommonService {

	private static Log logger = LogFactory.getLog(OrderCenterSamismileServiceImpl.class);	
	private PEService ma_peService;
	private OrderCenterSamismileDao ma_orderCenterSamismileDao;
	

	@Override
	public Map<String, Page<OrderCenterResultDTO>> queryResultList(Page<OrderCenterResultDTO> page, OrderCenterQueryDTO dto) {
		logger.debug("OrderCenterSamismileServiceImpl.queryResultList is running...");
		Map<String,Page<OrderCenterResultDTO>> map = new HashMap<String,Page<OrderCenterResultDTO>>();
		Page<OrderCenterResultDTO> orderList = this.ma_orderCenterSamismileDao.querySamismileList(page, dto);
		map.put("page", orderList);
		return map;	
	}

	@Override
	public Map<String, Object> queryOrderEntrie(OrderCenterQueryDTO queryDto) {
		logger.debug("OrderCenterSamismileServiceImpl.queryOrderEntrie is running...");
		List<OrderCenterEntrieDTO> entrieList = new ArrayList<OrderCenterEntrieDTO>();
		List<AccountEntryDTO> peEntryList = this.ma_peService.getAccountEntryByOrderId(queryDto.getOrderKy());
		for(AccountEntryDTO peDto:peEntryList){
			OrderCenterEntrieDTO entrie = new OrderCenterEntrieDTO();
			entrie.setAccoutCode(peDto.getAcctcode());
			entrie.setAmount(new BigDecimal(peDto.getValue()));
			entrie.setCertificateNo(peDto.getVouchercode().toString());
			entrie.setCreateDate(peDto.getTransactiondate());
			entrie.setDrMark(peDto.getCrdr().toString());
			entrie.setStatus(peDto.getStatus().toString());
			entrie.setEntrieNo(peDto.getEntrycode().toString());
			entrieList.add(entrie);
		}
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("entrieList", entrieList);
		return result;
	}

	

	@Override
	public Map<String, Object> queryOrderRelation(OrderCenterQueryDTO queryDto) {
		logger.debug("OrderCenterSamismileServiceImpl.queryOrderRelation is running...");
		String orderType = queryDto.getOrderType();
		String orderId = queryDto.getOrderKy(); 
		Map<String, Object> relationMap = new HashMap<String, Object>();
		List<OrderRelationDTO> relationList = new ArrayList<OrderRelationDTO>();
		if(StringUtils.isNotEmpty(orderType)&&StringUtils.isNotEmpty(orderId)){
			if(orderType.equals(Constants.ORDERTYPE_1015)){
				relationList = ma_orderCenterSamismileDao.querySamismileRelationList(orderId, Constants.ORDERTYPE_1016);
				relationMap.put("relationList",relationList);
			}else if(orderType.equals(Constants.ORDERTYPE_1016)){
				relationList = ma_orderCenterSamismileDao.querySamismileRelationList(orderId, Constants.ORDERTYPE_1015);
				relationMap.put("relationList",relationList);
			}else{
				
			}
			
		}
		relationMap.put("relationList", relationList);
		return relationMap;
	}
	@Override
	public Map<String, Object> queryOrderHistory(OrderCenterQueryDTO queryDto) {	
		return null;
	}
	@Override
	public OrderDetailDTO queryOrderDetail(OrderCenterQueryDTO queryDto) {

		return new OrderDetailDTO();
	}
	
	


	public void setMa_orderCenterSamismileDao(OrderCenterSamismileDao maOrderCenterSamismileDao) {
		ma_orderCenterSamismileDao = maOrderCenterSamismileDao;
	}

	public void setMa_peService(PEService maPeService) {
		ma_peService = maPeService;
	}

	
	

	

}
