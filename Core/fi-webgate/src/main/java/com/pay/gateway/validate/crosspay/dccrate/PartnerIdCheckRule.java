package com.pay.gateway.validate.crosspay.dccrate;

import org.apache.commons.lang.StringUtils;

import com.pay.acc.member.dto.MemberDto;
import com.pay.acc.member.service.MemberService;
import com.pay.gateway.dto.DccRateQueryRequest;
import com.pay.gateway.dto.DccRateQueryResponse;
import com.pay.inf.rule.MessageRule;
import com.pay.util.StringUtil;

public class PartnerIdCheckRule extends MessageRule {
	
	private MemberService memberService;
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {
		DccRateQueryRequest crosspayApiRequest = (DccRateQueryRequest) validateBean;
		DccRateQueryResponse crosspayApiResponse = crosspayApiRequest.getDccRateQueryResponse();
		String partnerId = crosspayApiRequest.getPartnerId();
		
		if(StringUtil.isEmpty(partnerId) || partnerId.trim().length() > 32) {
			crosspayApiResponse.setResultCode(getMessageId());
			crosspayApiResponse.setResultMsg(getMessage());
			return false;
		}

		if (!StringUtil.isEmpty(partnerId) && StringUtils.isNumeric(partnerId.trim()) ) {
			MemberDto memberDto = memberService.queryMemberByMemberCode(Long
					.valueOf(partnerId));
			if(memberDto==null){
				crosspayApiResponse.setResultCode(getMessageId());
				crosspayApiResponse.setResultMsg(getMessage());
				return false;
			}
			return true;
		} else {
			crosspayApiResponse.setResultCode(getMessageId());
			crosspayApiResponse.setResultMsg(getMessage());
			return false;
		}
	}
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}
	
	public static void main(String [] args) {
		System.out.println(Long.valueOf("10000003671"));
	}
}
