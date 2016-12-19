/**
 *  File: ElecBillsPay2BankSingleHandler.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-12-8   Sany        Create
 *
 */
package com.pay.elecbills;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.fo.elecbills.dto.ElecBillsDto;
import com.pay.fo.elecbills.impl.BaseElecBillsService;
import com.pay.fundout.service.ma.MemberInfo;
import com.pay.fundout.service.ma.MemberQueryFacadeService;
import com.pay.util.StringUtil;

/**
 * 网关支付
 */
public class GatewayPayHandler extends BaseElecBillsService {
	
	//private OrderQueryService orderQueryService; 
	
	private MemberQueryFacadeService memberQueryFacadeService;
	
	@Override
	public ElecBillsDto getViewData(Map<String, Object> map){
		
		ElecBillsDto elecBillsDto = new ElecBillsDto();
		try {
			LoginSession loginSession = SessionHelper.getLoginSession();
			String memberCode = loginSession.getMemberCode();
			MemberInfo memberInfo = memberQueryFacadeService.getMemberInfo(loginSession.getLoginName());
			map.put("serialNo", map.get("serialNo"));
			map.put("memberCode", memberCode);
			Map<String,Object> resultMap = null;//orderQueryService.querySingleIncomeDetail(map);
			if (resultMap != null) {
				elecBillsDto.setTradeNo(StringUtil.null2String(resultMap.get("tradeOrderNo")));
				elecBillsDto.setPayeeName(memberInfo.getMemberName());
				elecBillsDto.setPayeeLoginName(loginSession.getLoginName());
				elecBillsDto.setAmount(transAmount(StringUtil.null2String(resultMap.get("orderAmount"))));
				elecBillsDto.setFee(transAmount(StringUtil.null2String(resultMap.get("payeeFee"))));
				elecBillsDto.setRealAmount(transAmount(StringUtil.null2String(resultMap.get("orderAmount"))));
				elecBillsDto.setTradeType(getTradeType());
				elecBillsDto.setCreateTime(formatCreteTime(StringUtil.null2String(resultMap.get("createDate"))));
				elecBillsDto.setRemark(StringUtil.null2String(resultMap.get("remark")));
			}
		}catch (Exception e) {
			log.error("网关电子回单出错",e);
		}
		return elecBillsDto;
	}
	
	private String transAmount(String amount) {
		if (StringUtils.isBlank(amount)) {
			return "0";
		}
		Float amountL = (StringUtils.isNotEmpty(amount) ? Float.valueOf(amount) : 0) * 1000;
		BigDecimal amountBig = new BigDecimal(amountL);
		return amountBig.toString();
	}
	/**
	 * @param orderQueryService the orderQueryService to set
	 */
//	public void setOrderQueryService(OrderQueryService orderQueryService) {
//		this.orderQueryService = orderQueryService;
//	}
	
	/**
	 * @param memberQueryFacadeService the memberQueryFacadeService to set
	 */
	public void setMemberQueryFacadeService(
			MemberQueryFacadeService memberQueryFacadeService) {
		this.memberQueryFacadeService = memberQueryFacadeService;
	}
	
}
