package com.pay.pe.service.payment.common;

import com.pay.pe.dto.PostingRuleDTO;
import com.pay.pe.helper.Transactor;
import com.pay.pe.service.payment.AbstractPostingRule;
import com.pay.pe.service.payment.impl.PayPR;
import com.pay.pe.service.payment.impl.PayeePR;
import com.pay.pe.service.payment.impl.PayerPR;
import com.pay.util.MfDate;

/**
 * add aync update balance support.
 */
public final class PostingRuleFactory {
	/**
	 * Default constructor.
	 *
	 */
	private PostingRuleFactory() {
	}

	/**
	 * 创建记帐规则.
	 * 
	 * @param rule
	 *            <code>PostingRuleDTO</code> object.
	 * @return AbstractPostingRule
	 */
	public static AbstractPostingRule createPostingRule(
			final PostingRuleDTO rule) {
		if (Transactor.PAYEE.getValue() == rule.getParty()) {
			return createPayeePostingRule(rule);
		} else if (Transactor.PAYER.getValue() == rule.getParty()) {
			return createPayerPostingRule(rule);
		} else if (Transactor.ACCOUNT.getValue() == rule.getParty()) {
			return createpayPostingRule(rule);
		}
		return null;
	}

	/**
	 * 创建一个收款方的记帐规则.
	 * 
	 * @param rule
	 *            <code>PostingRuleDTO</code> object.
	 * @return PayeePR.
	 */
	private static PayeePR createPayeePostingRule(final PostingRuleDTO rule) {
		PayeePR postingRule = new PayeePR();
		if (null != rule.getAcctcode()) {
			postingRule.setAccountCode(String.valueOf(rule.getAcctcode()));
		}
		postingRule
				.setMbrAcctSpecific(null == rule.getMbrAcctSpecific() ? false
						: 1 == rule.getMbrAcctSpecific() ? true : false);
		postingRule.setValidDate(new MfDate(rule.getValiddate()));
		postingRule.setInvalidateDate(new MfDate(rule.getInvaliddate()));
		postingRule.setCrdr(rule.getCrdr());
		postingRule.setPaymentServiceCode(rule.getPaymentServiceDTO()
				.getPaymentservicecode());
		postingRule.setAcctAliasAcctTypeCode(String.valueOf(rule
				.getAcctAliasAcctType()));
		postingRule.setOrgType(rule.getOrgType());
		postingRule.setAcctAliasIndMbr("" + rule.getAcctAliasIndMbr());
		postingRule.setAcctAliasBizMbr("" + rule.getAcctAliasBizMbr());
		postingRule.setAsyncUpdateBalance(1 == rule.getAsynchronousPosting());
		postingRule.setParty(rule.getParty());
		return postingRule;
	}

	/**
	 * 创建一个付款方的记帐规则.
	 * 
	 * @param rule
	 *            <code>PostingRuleDTO</code> object.
	 * @return PayerPR.
	 */
	private static PayerPR createPayerPostingRule(final PostingRuleDTO rule) {
		PayerPR postingRule = new PayerPR();
		if (null != rule.getAcctcode()) {
			postingRule.setAccountCode(String.valueOf(rule.getAcctcode()));
		}
		postingRule
				.setMbrAcctSpecific(null == rule.getMbrAcctSpecific() ? false
						: 1 == rule.getMbrAcctSpecific() ? true : false);
		postingRule.setValidDate(new MfDate(rule.getValiddate()));
		postingRule.setInvalidateDate(new MfDate(rule.getInvaliddate()));
		postingRule.setCrdr(rule.getCrdr());
		postingRule.setAcctAliasAcctTypeCode(String.valueOf(rule
				.getAcctAliasAcctType()));
		postingRule.setPaymentServiceCode(rule.getPaymentServiceDTO()
				.getPaymentservicecode());
		postingRule.setOrgType(rule.getOrgType());
		postingRule.setAcctAliasIndMbr("" + rule.getAcctAliasIndMbr());
		postingRule.setAcctAliasBizMbr("" + rule.getAcctAliasBizMbr());
		postingRule.setAsyncUpdateBalance(1 == rule.getAsynchronousPosting());
		postingRule.setParty(rule.getParty());
		return postingRule;
	}

	/**
	 * 创建一个付款方的记帐规则.
	 * 
	 * @param rule
	 *            <code>PostingRuleDTO</code> object.
	 * @return PayerPR.
	 */
	private static PayPR createpayPostingRule(final PostingRuleDTO rule) {
		PayPR postingRule = new PayPR();
		if (null != rule.getAcctcode()) {
			postingRule.setAccountCode(String.valueOf(rule.getAcctcode()));
		}
		postingRule.setValidDate(new MfDate(rule.getValiddate()));
		postingRule.setInvalidateDate(new MfDate(rule.getInvaliddate()));
		postingRule.setCrdr(rule.getCrdr());
		postingRule.setAcctAliasAcctTypeCode(String.valueOf(rule
				.getAcctAliasAcctType()));
		postingRule.setPaymentServiceCode(rule.getPaymentServiceDTO()
				.getPaymentservicecode());
		postingRule.setOrgType(rule.getOrgType());
		postingRule.setAsyncUpdateBalance(1 == rule.getAsynchronousPosting());
		postingRule.setParty(rule.getParty());
		return postingRule;
	}
}
