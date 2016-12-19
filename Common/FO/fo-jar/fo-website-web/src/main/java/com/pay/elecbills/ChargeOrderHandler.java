/**
 *  File: ElecBillsPay2BankSingleHandler.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-12-8   Sany        Create
 *
 */
package com.pay.elecbills;

import java.util.Map;

import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.fo.elecbills.dto.ElecBillsDto;
import com.pay.fo.elecbills.impl.BaseElecBillsService;
import com.pay.fundout.service.ma.MemberInfo;
import com.pay.fundout.service.ma.MemberQueryFacadeService;

/**
 * 网关充值
 */
public class ChargeOrderHandler extends BaseElecBillsService {
	
	//private OrderQueryService orderQueryService; 
	
	private MemberQueryFacadeService memberQueryFacadeService;
	
	@Override
	public ElecBillsDto getViewData(Map<String, Object> map){
		
		LoginSession loginSession = SessionHelper.getLoginSession();
		String memberCode = loginSession.getMemberCode();
		MemberInfo memberInfo = memberQueryFacadeService.getMemberInfo(loginSession.getLoginName());
		ElecBillsDto elecBillsDto = new ElecBillsDto();
		map.put("dealId", map.get("serialNo"));
		map.put("memberCode", memberCode);
		//DepositOrderInfoDetailDTO depositOrderInfoDetailDTO = orderQueryService.queryDepositOrderInfoOrderId(map);
//		if (depositOrderInfoDetailDTO != null) {
//			elecBillsDto.setTradeNo(depositOrderInfoDetailDTO.getDealId());
//			elecBillsDto.setPayeeName(memberInfo.getMemberName());
//			elecBillsDto.setPayeeLoginName(loginSession.getLoginName());
//			elecBillsDto.setAmount(StringUtil.null2String(depositOrderInfoDetailDTO.getDealAmount()));
//			elecBillsDto.setFee(StringUtil.null2String(depositOrderInfoDetailDTO.getDealFee()));
//			elecBillsDto.setRealAmount(StringUtil.null2String(depositOrderInfoDetailDTO.getDealAmount()));
//			elecBillsDto.setTradeType(getTradeType());
//			elecBillsDto.setCreateTime(formatCreteTime(StringUtil.null2String(depositOrderInfoDetailDTO.getStartDate())));
//			elecBillsDto.setRemark(StringUtil.null2String(depositOrderInfoDetailDTO.getRemark()));
//		}
		return elecBillsDto;
	}
	
	/**
	 * @param orderQueryService the orderQueryService to set
	 */
	
	/**
	 * @param memberQueryFacadeService the memberQueryFacadeService to set
	 */
	public void setMemberQueryFacadeService(
			MemberQueryFacadeService memberQueryFacadeService) {
		this.memberQueryFacadeService = memberQueryFacadeService;
	}
	
}
