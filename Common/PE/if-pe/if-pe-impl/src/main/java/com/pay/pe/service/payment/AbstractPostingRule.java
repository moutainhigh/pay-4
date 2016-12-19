package com.pay.pe.service.payment;

import java.util.Date;

import com.pay.pe.dto.AccountEntryDTO;
import com.pay.pe.service.payment.common.AccountingEvent;
import com.pay.pe.service.payment.common.AcctCodeGeneraterFactory;
import com.pay.util.MfDate;
import com.pay.util.Money;

public abstract class AbstractPostingRule {

	private Integer party;
	/**
	 * 记帐规则代码.
	 */
	private String postingRuleCode;

	private Integer paymentServiceCode;
	/**
	 * 帐号.
	 */
	private String accountCode;
	/**
	 * 支付行为：付款/收款.
	 */
	private Integer crdr;
	/**
	 * 帐户类型代码.
	 */
	private String acctAliasAcctTypeCode;

	private Integer orgType;
	/**
	 * 账户别名－个人会员账户类型
	 */
	private String acctAliasIndMbr;
	/**
	 * 账户别名－单位会员账户类型
	 */
	private String acctAliasBizMbr;

	/**
	 * 生效日期.
	 */
	private MfDate validDate;
	/**
	 * 失效日期.
	 */
	private MfDate invalidateDate;

	/**
	 * 是否需要异步更改帐户余额.
	 */
	private boolean isAsyncUpdateBalance;

	/**
	 * 是否指定完整会员帐户
	 */
	private boolean mbrAcctSpecific;

	public boolean isMbrAcctSpecific() {
		return mbrAcctSpecific;
	}

	public void setMbrAcctSpecific(boolean mbrAcctSpecific) {
		this.mbrAcctSpecific = mbrAcctSpecific;
	}

	/**
	 * @return Returns the accountCode.
	 */
	public final String getAccountCode() {
		return accountCode;
	}

	/**
	 * @param code
	 *            The accountCode to set.
	 */
	public final void setAccountCode(final String code) {
		this.accountCode = code;
	}

	/**
	 * @return Returns the acctAliasAcctTypeCode.
	 */
	public final String getAcctAliasAcctTypeCode() {
		return acctAliasAcctTypeCode;
	}

	/**
	 * @param code
	 *            The acctAliasAcctTypeCode to set.
	 */
	public final void setAcctAliasAcctTypeCode(final String code) {
		this.acctAliasAcctTypeCode = code;
	}

	public Integer getOrgType() {
		return orgType;
	}

	public void setOrgType(Integer orgType) {
		this.orgType = orgType;
	}

	/**
	 * @return Returns the crdr.
	 */
	public final Integer getCrdr() {
		return crdr;
	}

	/**
	 * @param creditDebit
	 *            The crdr to set.
	 */
	public final void setCrdr(final Integer creditDebit) {
		this.crdr = creditDebit;
	}

	/**
	 * @return Returns the invalidateDate.
	 */
	public final MfDate getInvalidateDate() {
		return invalidateDate;
	}

	/**
	 * @param date
	 *            The invalidateDate to set.
	 */
	public final void setInvalidateDate(final MfDate date) {
		this.invalidateDate = date;
	}

	/**
	 * @return Returns the postingRuleCode.
	 */
	public final String getPostingRuleCode() {
		return postingRuleCode;
	}

	/**
	 * @param code
	 *            The postingRuleCode to set.
	 */
	public final void setPostingRuleCode(final String code) {
		this.postingRuleCode = code;
	}

	/**
	 * @return Returns the validDate.
	 */
	public final MfDate getValidDate() {
		return validDate;
	}

	/**
	 * @param date
	 *            The validDate to set.
	 */
	public final void setValidDate(final MfDate date) {
		this.validDate = date;
	}

	/**
	 * @return Returns the isAsyncUpdateBalance.
	 */
	public boolean isAsyncUpdateBalance() {
		return isAsyncUpdateBalance;
	}

	/**
	 * @param isAsyncUpdateBalance
	 *            The isAsyncUpdateBalance to set.
	 */
	public void setAsyncUpdateBalance(boolean isAsyncUpdateBalance) {
		this.isAsyncUpdateBalance = isAsyncUpdateBalance;
	}

	/**
	 * 根据支付请求处理，生成记帐分录.
	 * 
	 * @param accountingEvent
	 *            <code>AccountingEvent</code> object.
	 */
	public final void process(final AccountingEvent accountingEvent) {
		if (isValid(accountingEvent)) {
			makeEntry(accountingEvent, calculateAmount(accountingEvent));
		}
	}

	/**
	 * 生成记帐分录记录.
	 * 
	 * @param accountingEvent
	 *            <code>AccountingEvent</code> object.
	 * @param amount
	 *            <code>Money</code> object.
	 */
	protected final void makeEntry(final AccountingEvent accountingEvent,
			final Money amount) {

		AccountEntryDTO newEntry = new AccountEntryDTO();

		String acctCode = generateAccountCode(accountingEvent);

		newEntry.setAcctcode(acctCode);

		newEntry.setCrdr(this.crdr);
		newEntry.setValue(amount.getAmount());
		newEntry.setDealId(accountingEvent.getOrderCode());
		newEntry.setPaymentServiceId(accountingEvent.getPaymentServiceDTO()
				.getPaymentservicecode());
		newEntry.setCreatedate(new Date());
		newEntry.setTransactiondate(accountingEvent.getPayTime().getTime());// 设置交易时间
		// 同步更新余额.
		newEntry.setStatus(1);
		newEntry.setText("");
		// 设置记账币种
		newEntry.setCurrencyCode(accountingEvent.getAccountingCurrencyCode());
		// 设置记账汇率
		newEntry.setExchangeRate(accountingEvent.getAccountingExchangeRate());
		newEntry.setDealType(accountingEvent.getDealType());
		accountingEvent.addEntry(newEntry);
	}

	protected final AbstractAcctCodeGenerater getAcctCodeGenerater(
			AccountingEvent accountingEvent) {
		return AcctCodeGeneraterFactory.createGenerater(accountingEvent);
	}

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
	public abstract String generateAccountCode(AccountingEvent accountingEvent);

	/**
	 * 判断.
	 * 
	 * .AbstractPostingRule#isValid(.domain.service
	 * .payment.common.AccountingEvent)
	 * 
	 * @param accountingEvent
	 *            <code>AccountingEvent</code> object.
	 * @return Boolean
	 */
	public abstract Boolean isValid(AccountingEvent accountingEvent);

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
	public abstract Money calculateAmount(AccountingEvent accountingEvent);

	public String getAcctAliasBizMbr() {
		return acctAliasBizMbr;
	}

	public void setAcctAliasBizMbr(String acctAliasBizMbr) {
		this.acctAliasBizMbr = acctAliasBizMbr;
	}

	public String getAcctAliasIndMbr() {
		return acctAliasIndMbr;
	}

	public void setAcctAliasIndMbr(String acctAliasIndMbr) {
		this.acctAliasIndMbr = acctAliasIndMbr;
	}

	public Integer getParty() {
		return party;
	}

	public void setParty(Integer party) {
		this.party = party;
	}

	public Integer getPaymentServiceCode() {
		return paymentServiceCode;
	}

	public void setPaymentServiceCode(Integer paymentServiceCode) {
		this.paymentServiceCode = paymentServiceCode;
	}

}
