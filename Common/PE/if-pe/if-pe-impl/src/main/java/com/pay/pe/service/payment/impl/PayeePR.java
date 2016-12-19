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

public final class PayeePR extends AbstractPostingRule {

	/**
	 * 产生帐号代码.
	 * 
	 * 
	 * #generateAccountCode(.domain.service.payment.common\ .AccountingEvent)
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
		param.setFullMemberAccCode(accountingEvent.getPayeeFullMemberAccCode());
		param.setMemberAcctCode(accountingEvent.getPayeeMemberAccCode());

		// 设置orgCode
		BankOrgCodeMappingDAO bankOrgCodeMappingDAO = accountingEvent
				.getBankOrgCodeMappingDAO();

		if (postingRule.getOrgType() == OrgType.BANK.getValue()) {
			String bankCode = accountingEvent.getBankCode();
			String currencyCode = accountingEvent.getAccountingCurrencyCode();
			Integer psCode = accountingEvent.getPaymentServiceDTO().getPaymentservicecode();
			BankOrgCodeMapping bankOrgCodeMapping = bankOrgCodeMappingDAO
					.findOrgCode(bankCode, currencyCode, psCode,
							DependOnEnum.PAYEE.getCode());
			Assert.notNull(bankOrgCodeMapping,
					"bankOrgCodeMapping must be not null,bankCode:"+bankCode+",currencyCode:"+currencyCode+",psCode:"+psCode+",depondOn:"+DependOnEnum.PAYEE.getCode());
			param.setOrgCode(bankOrgCodeMapping.getOrgCode());
		}

		param.setOrgTypeCode(postingRule.getOrgType());
		param.setPostingRule(this);
		param.setMemberCode(accountingEvent.getPayee());
		param.setMemberType(accountingEvent.getPayeeMemberType());
		String acctCode = generater.generate(param);
		return acctCode;
	}

	/**
	 * 判断支付事件是否适用于此记帐规则. 如果收款方的机构类型代码不为空，则必须要求此机构类型代码与记帐规则中的机构类型代码相同.
	 * 
	 * 
	 * #isValid(.domain.service.payment .common.AccountingEvent)
	 * 
	 * @param accountingEvent
	 *            <code>AccountingEvent</code> object.
	 * @return Boolean
	 */
	public Boolean isValid(final AccountingEvent accountingEvent) {
		Integer payeeOrgTypeCode = accountingEvent.getPayeeOrgTypeCode();
		String accountTypeCode = getAcctAliasAcctTypeCode();

		boolean checkFlg = OrgType.MEMBER.getValue() == payeeOrgTypeCode
				|| !StringUtil.isEmpty(accountTypeCode)
				|| !StringUtil.isEmpty(getAccountCode());

		if (null != payeeOrgTypeCode) {
			return checkFlg;
		}
		return true;
	}

	/**
	 * 计算金额.
	 * 
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
