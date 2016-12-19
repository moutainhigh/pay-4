package com.pay.fundout.withdraw.validate.rule;

import com.pay.acc.service.member.MemberProductService;
import com.pay.fo.order.dto.base.FundoutOrderDTO;
import com.pay.inf.rule.MessageRule;

/**
 * 验证商户是否开通银企直联产品
 */
public class PayerIsBankcorporateCheckRule extends MessageRule {

	/** 会员产品开通权限 **/
	protected MemberProductService memberProductService;

	public void setMemberProductService(MemberProductService memberProductService) {
		this.memberProductService = memberProductService;
	}

	@Override
	protected boolean makeDecision(Object order) throws Exception {

		FundoutOrderDTO fundoutOrderDTO = (FundoutOrderDTO) order;
		Long memberCode = fundoutOrderDTO.getPayerMemberCode();
		boolean isHaveProduct = memberProductService.isHaveProduct(memberCode, "BANK_CORPORATE");

		if (isHaveProduct) {
			return true;
		} else {
			return false;
		}
	}
}
