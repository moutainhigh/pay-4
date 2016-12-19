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
import com.pay.tradequery.service.PayDetailService;
import com.pay.util.StringUtil;

/**
 * 付款到账户
 */
public class Pay2AcctHandler extends BaseElecBillsService {
	
	private PayDetailService payDetailService;
	
	@Override
	public ElecBillsDto getViewData(Map<String, Object> map){
		ElecBillsDto elecBillsDto = new ElecBillsDto();
		LoginSession loginSession = SessionHelper.getLoginSession();
		String memberCode = loginSession.getMemberCode();
		map.put("sequenceId", map.get("serialNo"));
		map.put("memberCode", memberCode);
		map.put("channel", "1");
		Map<String, Object> resultMap = payDetailService.querySinglePayDetail(map);
		elecBillsDto.setTradeNo(StringUtil.null2String(resultMap.get("sequenceId")));
		elecBillsDto.setBatchNo(StringUtil.null2String(map.get("batchNo")));
		elecBillsDto.setPayerName(StringUtil.null2String(resultMap.get("payerName")));
		elecBillsDto.setPayeeName(StringUtil.null2String(resultMap.get("payeeName")));
		elecBillsDto.setPayeeLoginName(StringUtil.null2String(resultMap.get("payeeLoginName")));
		elecBillsDto.setPayerLoginName(StringUtil.null2String(resultMap.get("payerLoginName")));
		elecBillsDto.setAmount(StringUtil.null2String(resultMap.get("amount")));
		elecBillsDto.setFee(StringUtil.null2String(resultMap.get("fee")));
		if (StringUtil.null2String(resultMap.get("payerMembercode")).equals("1")) {
			elecBillsDto.setRealAmount(amountAdd(StringUtil.null2String(resultMap.get("amount")), StringUtil.null2String(resultMap.get("fee"))));
		}else {
			elecBillsDto.setRealAmount(StringUtil.null2String(resultMap.get("amount")));
		}
		elecBillsDto.setTradeType(getTradeType());
		elecBillsDto.setCreateTime(formatCreteTime(StringUtil.null2String(resultMap.get("createTime"))));
		elecBillsDto.setRemark(StringUtil.null2String(resultMap.get("remark")));
		return elecBillsDto;
	}
	
	/**
	 * @param payDetailService the payDetailService to set
	 */
	public void setPayDetailService(PayDetailService payDetailService) {
		this.payDetailService = payDetailService;
	}
	
}
