package com.pay.pe.service.payment.impl;

import org.springframework.util.Assert;

import com.pay.pe.dao.BankOrgCodeMappingDAO;
import com.pay.pe.helper.DependOnEnum;
import com.pay.pe.helper.OrgType;
import com.pay.pe.model.BankOrgCodeMapping;
import com.pay.pe.service.payment.AbstractAcctCodeGenerater;
import com.pay.pe.service.payment.AbstractPostingRule;
import com.pay.pe.service.payment.common.AccountingEvent;
import com.pay.pe.service.payment.common.AcctCodeGeneraterParam;
import com.pay.util.Money;
import com.pay.util.StringUtil;

/**
 * 
 *
 */
public final class PayerPR extends AbstractPostingRule {
	/**
	 * 得到帐号代码.
	 * 
	 * .AbstractPostingRule#generateAccountCode(
	 * .domain.service.payment.common.AccountingEvent)
	 * 
	 * @param accountingEvent
	 *            <code>AccountingEvent</code> object.
	 * @return AccountDTO
	 */
	public String generateAccountCode(final AccountingEvent accountingEvent) {

		AbstractAcctCodeGenerater generater = this
				.getAcctCodeGenerater(accountingEvent);

		AbstractPostingRule postingRule = this;
		AcctCodeGeneraterParam param = new AcctCodeGeneraterParam();
		param.setCurrencyNum(accountingEvent.getAccountingCurrencyNum());
		param.setFullMemberAccCode(accountingEvent.getPayerFullMemberAccCode());
		param.setMemberAcctCode(accountingEvent.getPayerMemberAccCode());

		// 设置orgCode
		if (postingRule.getOrgType() == OrgType.BANK.getValue()) {
			BankOrgCodeMappingDAO bankOrgCodeMappingDAO = accountingEvent
					.getBankOrgCodeMappingDAO();
			String bankCode = accountingEvent.getBankCode();
			String currencyCode = accountingEvent.getAccountingCurrencyCode();
			Integer psCode = accountingEvent.getPaymentServiceDTO().getPaymentservicecode();
			BankOrgCodeMapping bankOrgCodeMapping = bankOrgCodeMappingDAO
					.findOrgCode(bankCode, currencyCode, psCode,
							DependOnEnum.PAYER.getCode());
			Assert.notNull(bankOrgCodeMapping,
					"bankOrgCodeMapping must be not null");
			param.setOrgCode(bankOrgCodeMapping.getOrgCode());
		}

		param.setOrgTypeCode(postingRule.getOrgType());
		param.setPostingRule(postingRule);
		param.setMemberCode(accountingEvent.getPayer());
		param.setMemberType(accountingEvent.getPayerMemberType());
		return generater.generate(param);
	}

	/**
	 * 判断. 如果付款方的机构类型代码不为空，则必须要求此机构类型代码与记帐规则中的机构类型代码相同.
	 * 
	 * .AbstractPostingRule#isValid(.domain.service
	 * .payment.common.AccountingEvent)
	 * 
	 * @param accountingEvent
	 *            <code>AccountingEvent</code> object.
	 * @return Boolean
	 */
	public Boolean isValid(final AccountingEvent accountingEvent) {
		Integer payerOrgTypeCode = accountingEvent.getPayerOrgTypeCode();
		String accountTypeCode = getAcctAliasAcctTypeCode();
		boolean checkFlg = OrgType.MEMBER.getValue() == payerOrgTypeCode
				|| !StringUtil.isEmpty(accountTypeCode)
				|| !StringUtil.isEmpty(getAccountCode());
		if (null != payerOrgTypeCode) {
			return checkFlg;
		}
		return true;
	}

	/**
	 * 计算金额.
	 * 
	 * .AbstractPostingRule#calculateAmount(.domain.service
	 * .payment.common.AccountingEvent)
	 * 
	 * @param accountingEvent
	 *            <code>AccountingEvent</code> object.
	 * @return Money
	 */
	public Money calculateAmount(final AccountingEvent accountingEvent) {
		return accountingEvent.getAmount();
	}
}
