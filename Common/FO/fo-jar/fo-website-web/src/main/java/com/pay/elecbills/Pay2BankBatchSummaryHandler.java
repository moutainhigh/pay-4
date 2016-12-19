/**
 *  File: ElecBillsPay2BankSingleHandler.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-12-6   Sany        Create
 *
 */
package com.pay.elecbills;

import java.util.HashMap;
import java.util.Map;

import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.fo.elecbills.dto.ElecBillsDto;
import com.pay.fo.elecbills.impl.BaseElecBillsService;
import com.pay.fundout.service.ma.MemberInfo;
import com.pay.fundout.service.ma.MemberQueryFacadeService;
import com.pay.tradequery.service.PayDetailService;
import com.pay.util.StringUtil;

/**
 * 批量付款到银行汇总回单
 */
public class Pay2BankBatchSummaryHandler extends BaseElecBillsService {
	
	private PayDetailService payDetailService;
	
	private MemberQueryFacadeService memberQueryFacadeService;
	
	@Override
	public ElecBillsDto getViewData(Map<String, Object> map){
		ElecBillsDto elecBillsDto = new ElecBillsDto();
		LoginSession loginSession = SessionHelper.getLoginSession();
		String memberCode = loginSession.getMemberCode();
		MemberInfo memberInfo = memberQueryFacadeService.getMemberInfo(loginSession.getLoginName());
		Map<String, Object> paraMap = new HashMap<String, Object>();

		paraMap.put("memberCode", memberCode);
		paraMap.put("serialNo", map.get("serialNo"));
		Map<String, Object> resultMap = payDetailService.querySinglePayBatchDetail(paraMap);


		elecBillsDto.setBatchNo(StringUtil.null2String(resultMap.get("batchNum")));
		elecBillsDto.setTradeNo(StringUtil.null2String(resultMap.get("sequenceId")));
		elecBillsDto.setPayerName(memberInfo.getMemberName());
		elecBillsDto.setPayerLoginName(loginSession.getLoginName());
		elecBillsDto.setAgreementPayName(getAgreementPayName());//协议代付方用户名
		
		elecBillsDto.setAmount(StringUtil.null2String(resultMap.get("validAmount")));
		elecBillsDto.setFee(resultMap.get("fee")+"");
		elecBillsDto.setRealAmount(amountAdd(resultMap.get("validAmount")+"", resultMap.get("fee")+""));
		elecBillsDto.setTradeType(getTradeType());
		elecBillsDto.setCreateTime(formatCreteTime(StringUtil.null2String(resultMap.get("createTime"))));
		elecBillsDto.setRemark(StringUtil.null2String(resultMap.get("auditRemark")));
		return elecBillsDto;
	}
	
	public void setPayDetailService(PayDetailService payDetailService) {
		this.payDetailService = payDetailService;
	}
	
	/**
	 * @param memberQueryFacadeService the memberQueryFacadeService to set
	 */
	public void setMemberQueryFacadeService(
			MemberQueryFacadeService memberQueryFacadeService) {
		this.memberQueryFacadeService = memberQueryFacadeService;
	}
	
}
