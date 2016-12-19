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

import org.apache.commons.lang.StringUtils;

import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.fo.elecbills.dto.ElecBillsDto;
import com.pay.fo.elecbills.impl.BaseElecBillsService;
import com.pay.fundout.dto.OrderInfoDetail;
import com.pay.fundout.service.ma.MemberInfo;
import com.pay.fundout.service.ma.MemberQueryFacadeService;
import com.pay.fundout.service.query.CommonQueryService;
import com.pay.util.StringUtil;

/**
 * 批量付款到银行
 */
public class WithdrawHandler extends BaseElecBillsService {
	
	private CommonQueryService commonQueryService;
	
	private MemberQueryFacadeService memberQueryFacadeService;
	
	@Override
	public ElecBillsDto getViewData(Map<String, Object> map){
		ElecBillsDto elecBillsDto = new ElecBillsDto();
		LoginSession loginSession = SessionHelper.getLoginSession();
		String memberCode = loginSession.getMemberCode();
		MemberInfo memberInfo = memberQueryFacadeService.getMemberInfo(loginSession.getLoginName());
		map.put("dealId", map.get("serialNo"));
		map.put("memberCode", memberCode);
		OrderInfoDetail orderDetail = commonQueryService
				.queryOrderDetailByOrderId(map);
		if(orderDetail != null){
			String payeeBeankNo = orderDetail.getPayeeBankNo();
			orderDetail.setPayeeBankNo(StringUtils.isNotEmpty(payeeBeankNo) ? payeeBeankNo.substring(0,5)+"******"+payeeBeankNo.substring(payeeBeankNo.length()-4) : "");
		}
		
		elecBillsDto.setTradeNo(orderDetail.getDealId());
		elecBillsDto.setPayerName(memberInfo.getMemberName());
		elecBillsDto.setPayeeName(orderDetail.getPayeeName());
		elecBillsDto.setPayerLoginName(loginSession.getLoginName());
		elecBillsDto.setPayeeBankNo(orderDetail.getPayeeBankNo());//收款方银行账号payeeLoginName
		
		elecBillsDto.setAmount(String.valueOf(orderDetail.getDealAmount()));
		elecBillsDto.setFee(String.valueOf(orderDetail.getDealFee()));
		elecBillsDto.setRealAmount(amountAdd(String.valueOf(orderDetail.getDealAmount()), String.valueOf(orderDetail.getDealFee())));
		elecBillsDto.setTradeType(getTradeType());
		elecBillsDto.setCreateTime(formatCreteTime(StringUtil.null2String(orderDetail.getStartDate())));
		elecBillsDto.setRemark(StringUtil.null2String(orderDetail.getRemark()));
		return elecBillsDto;
	}
	
	/**
	 * @param commonQueryService the commonQueryService to set
	 */
	public void setCommonQueryService(CommonQueryService commonQueryService) {
		this.commonQueryService = commonQueryService;
	}
	
	/**
	 * @param memberQueryFacadeService the memberQueryFacadeService to set
	 */
	public void setMemberQueryFacadeService(
			MemberQueryFacadeService memberQueryFacadeService) {
		this.memberQueryFacadeService = memberQueryFacadeService;
	}
}
