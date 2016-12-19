package com.pay.gateway.validate.crosspay.rule;

import org.apache.commons.lang.StringUtils;

import com.pay.acc.member.dto.MemberDto;
import com.pay.acc.member.service.MemberService;
import com.pay.gateway.dto.CrosspayRequest;
import com.pay.gateway.dto.CrosspayResponse;
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
		 
		CrosspayRequest crosspayApiRequest = (CrosspayRequest) validateBean;
		CrosspayResponse crosspayApiResponse = crosspayApiRequest.getCrosspayResponse();

		String partnerId = crosspayApiRequest.getPartnerId();
		
		if(StringUtil.isEmpty(partnerId) || partnerId.trim().length() > 32) {
			crosspayApiResponse.setResultCode(getMessageId());
			crosspayApiResponse.setResultMsg(getMessage());
			return false;
		}

		if (!StringUtil.isEmpty(partnerId) && StringUtils.isNumeric(partnerId.trim()) ) {
			try{
				MemberDto memberDto = memberService.queryMemberByMemberCode(Long
						.valueOf(partnerId));//
				if(memberDto==null){
					crosspayApiResponse.setResultCode(getMessageId());
					crosspayApiResponse.setResultMsg(getMessage());
					return false;
				}
				return true;
			}catch (Exception e){
				crosspayApiResponse.setResultCode(getMessageId());
				crosspayApiResponse.setResultMsg(getMessage());
				return false;
			}
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
