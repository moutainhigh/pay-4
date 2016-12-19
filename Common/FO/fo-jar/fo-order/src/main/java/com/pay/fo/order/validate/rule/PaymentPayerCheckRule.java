/**
 *  File: PaymentPayerCheckRule.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-13   terry     Create
 *
 */
package com.pay.fo.order.validate.rule;

import com.pay.acc.member.memberenum.MemberStatusEnum;
import com.pay.acc.service.member.MemberQueryService;
import com.pay.acc.service.member.dto.MemberInfoDto;
import com.pay.fo.order.validate.BatchPaymentRequest;
import com.pay.inf.rule.MessageRule;
import com.pay.util.StringUtil;

/**
 * 
 */
public class PaymentPayerCheckRule extends MessageRule {

	private MemberQueryService memberQueryService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {

		BatchPaymentRequest request = (BatchPaymentRequest) validateBean;
		Long memberCode = request.getMemberCode();
		MemberInfoDto dto = memberQueryService.doQueryMemberInfoNsTx(null,
				memberCode, null, null);

		String errorMsg = validatePayerMemberInfo(dto);
		if(!StringUtil.isEmpty(errorMsg)){
			request.getBatchPaymentResponse().setErrorMsg(errorMsg);
			return false;
		}else{
			return true;
		}
		
	}

	public void setMemberQueryService(MemberQueryService memberQueryService) {
		this.memberQueryService = memberQueryService;
	}

	public String validatePayerMemberInfo(MemberInfoDto payer) {
		String errorMsg = "";
		int status = payer.getStatus();
		if (status != MemberStatusEnum.NORMAL.getCode()) {
			String msg = "";
			if (status == MemberStatusEnum.DELETE.getCode()) {
				msg = MemberStatusEnum.DELETE.getMessage();
			} else if (status == MemberStatusEnum.FROZEEN.getCode()) {
				msg = MemberStatusEnum.FROZEEN.getMessage();
			} else if (status == MemberStatusEnum.NO_ACTIVE.getCode()) {
				msg = MemberStatusEnum.NO_ACTIVE.getMessage();
			}
			if (!StringUtil.isNull(msg)) {
				errorMsg = "您的会员账户处于" + msg + "状态,暂不能付款。如有疑问，请联系客服";

			} else {
				errorMsg = "您的会员账户状态异常，暂不能付款。如有疑问，请联系客服";
			}
		}
		return errorMsg;
	}
}
