package com.pay.pe.service.payment.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.pe.dao.BankOrgCodeMappingDAO;
import com.pay.pe.dto.AccountEntryDTO;
import com.pay.pe.dto.PaymentServiceDTO;
import com.pay.pe.dto.PostingRuleDTO;
import com.pay.pe.service.payment.AbstractPostingRule;
import com.pay.pe.service.payment.Payment;
import com.pay.util.MfDate;
import com.pay.util.Money;

public final class AccountingEvent {

	/**
	 * 日志.
	 */
	private final Log logger = LogFactory.getLog(getClass());

	/**
	 * 支付服务.
	 */
	private PaymentServiceDTO paymentServiceDTO;

	/**
	 * 记帐时间.
	 */
	private MfDate whenAccounting;

	/**
	 * 帐户分录.
	 */
	private List<AccountEntryDTO> resultingEntries;

	/**
	 * 记帐规则.
	 */
	private List<PostingRuleDTO> postingRules;
	
	private String bankCode;

	/**
	 * 支付用户.
	 */
	private String payer;

	private Integer payerMemberType;

	/**
	 * 收款用户.
	 */
	private String payee;

	private Integer payeeMemberType;

	/**
	 * 支付用户的机构代码.
	 */
	private String payerOrgCode;

	/**
	 * 付款方机构类型代码.
	 */
	private Integer payerOrgTypeCode;

	/**
	 * 收款用户的机构代码.
	 */
	private String payeeOrgCode;

	/**
	 * 收款方机构类型代码.
	 */
	private Integer payeeOrgTypeCode;

	/**
	 * 收款方Member Account Code.
	 */
	private String payeeMemberAccCode;

	/**
	 * 付款方Member Account Code.
	 */
	private String payerMemberAccCode;

	/**
	 * 收款方FullMember Account Code. 包含科目号
	 */
	private String payeeFullMemberAccCode;

	/**
	 * 付款方FullMember Account Code. 包含科目号
	 */
	private String payerFullMemberAccCode;

	/**
	 * 交易金额.
	 */
	private Money amount;

	/**
	 * 交易的订单号.
	 */
	private String orderCode;

	/**
	 * 会计凭证编号.
	 */
	private String voucherNo;

	/**
	 * 支付时间.
	 */
	private MfDate payTime;

	/**
	 * 记账汇率
	 */
	private Long accountingExchangeRate;

	/**
	 * 记账币种缩写
	 */
	private String accountingCurrencyCode;

	/**
	 * 记账币种号
	 */
	private String accountingCurrencyNum;

	// 交易类型
	private Integer dealType;
	
	private BankOrgCodeMappingDAO bankOrgCodeMappingDAO;
	
	public BankOrgCodeMappingDAO getBankOrgCodeMappingDAO() {
		return bankOrgCodeMappingDAO;
	}

	public void setBankOrgCodeMappingDAO(BankOrgCodeMappingDAO bankOrgCodeMappingDAO) {
		this.bankOrgCodeMappingDAO = bankOrgCodeMappingDAO;
	}

	public Integer getDealType() {
		return dealType;
	}

	public void setDealType(Integer dealType) {
		this.dealType = dealType;
	}

	public String getAccountingCurrencyNum() {
		return accountingCurrencyNum;
	}

	public void setAccountingCurrencyNum(String accountingCurrencyNum) {
		this.accountingCurrencyNum = accountingCurrencyNum;
	}

	public Long getAccountingExchangeRate() {
		return accountingExchangeRate;
	}

	public void setAccountingExchangeRate(Long accountingExchangeRate) {
		this.accountingExchangeRate = accountingExchangeRate;
	}

	public String getAccountingCurrencyCode() {
		return accountingCurrencyCode;
	}

	public void setAccountingCurrencyCode(String accountingCurrencyCode) {
		this.accountingCurrencyCode = accountingCurrencyCode;
	}

	/**
	 * 增加一个记帐分录.
	 * 
	 * @param entry
	 *            <code>AccountEntry</code> object.
	 */
	public void addEntry(final AccountEntryDTO entry) {
		if (null == resultingEntries) {
			resultingEntries = new ArrayList();
		}
		resultingEntries.add(entry);
	}

	/**
	 * 增加一个记帐规则.
	 * 
	 * @param rule
	 *            <code>PostingRule</code> object.
	 */
	public void addPostingRule(final PostingRuleDTO rule) {
		if (null == postingRules) {
			postingRules = new ArrayList();
		}
		postingRules.add(rule);
	}

	/**
	 * @return Returns the amount.
	 */
	public Money getAmount() {
		return amount;
	}

	/**
	 * @param money
	 *            The amount to set.
	 */
	public void setAmount(final Money money) {
		this.amount = money;
	}

	/**
	 * @return Returns the orderCode.
	 */
	public String getOrderCode() {
		return orderCode;
	}

	/**
	 * @param order
	 *            The orderCode to set.
	 */
	public void setOrderCode(final String order) {
		this.orderCode = order;
	}

	/**
	 * @return Returns the payee.
	 */
	public String getPayee() {
		return payee;
	}

	/**
	 * @param payee
	 *            The payee to set.
	 */
	public void setPayee(final String payee) {
		this.payee = payee;
	}

	/**
	 * @return Returns the payeeOrgCode.
	 */
	public String getPayeeOrgCode() {
		return payeeOrgCode;
	}

	/**
	 * @param orgCode
	 *            The payeeOrgCode to set.
	 */
	public void setPayeeOrgCode(final String orgCode) {
		this.payeeOrgCode = orgCode;
	}

	/**
	 * @return Returns the payer.
	 */
	public String getPayer() {
		return payer;
	}

	/**
	 * @param payer
	 *            The payer to set.
	 */
	public void setPayer(final String payer) {
		this.payer = payer;
	}

	/**
	 * @return Returns the payerOrgCode.
	 */
	public String getPayerOrgCode() {
		return payerOrgCode;
	}

	/**
	 * @param orgCode
	 *            The payerOrgCode to set.
	 */
	public void setPayerOrgCode(final String orgCode) {
		this.payerOrgCode = orgCode;
	}

	/**
	 * @return Returns the paymentService.
	 */
	public PaymentServiceDTO getPaymentServiceDTO() {
		return paymentServiceDTO;
	}

	/**
	 * @param service
	 *            The paymentService to set.
	 */
	public void setPaymentServiceDTO(final PaymentServiceDTO service) {
		this.paymentServiceDTO = service;
	}

	/**
	 * @return Returns the resultingEntries.
	 */
	public List<AccountEntryDTO> getResultingEntries() {
		return resultingEntries;
	}

	/**
	 * @param entries
	 *            The resultingEntries to set.
	 */
	public void setResultingEntries(final List<AccountEntryDTO> entries) {
		this.resultingEntries = entries;
	}

	/**
	 * @return Returns the voucherNo.
	 */
	public String getVoucherNo() {
		return voucherNo;
	}

	/**
	 * @param no
	 *            The voucherNo to set.
	 */
	public void setVoucherNo(final String no) {
		voucherNo = no;
	}

	/**
	 * @return Returns the whenAccounting.
	 */
	public MfDate getWhenAccounting() {
		return whenAccounting;
	}

	/**
	 * @param date
	 *            The whenAccounting to set.
	 */
	public void setWhenAccounting(final MfDate date) {
		this.whenAccounting = date;
	}

	/**
	 * @return Returns the postingRules.
	 */
	public List<PostingRuleDTO> getPostingRules() {
		return postingRules;
	}

	/**
	 * @param rules
	 *            The postingRules to set.
	 */
	public void setPostingRules(final List<PostingRuleDTO> rules) {
		this.postingRules = rules;
	}

	/**
	 * @return Returns the payeeOrgTypeCode.
	 */
	public Integer getPayeeOrgTypeCode() {
		return payeeOrgTypeCode;
	}

	/**
	 * @param code
	 *            The payeeOrgTypeCode to set.
	 */
	public void setPayeeOrgTypeCode(final Integer code) {
		this.payeeOrgTypeCode = code;
	}

	/**
	 * @return Returns the payerOrgTypeCode.
	 */
	public Integer getPayerOrgTypeCode() {
		return payerOrgTypeCode;
	}

	/**
	 * @return Returns the payTime.
	 */
	public MfDate getPayTime() {
		return payTime;
	}

	/**
	 * @param date
	 *            The payTime to set.
	 */
	public void setPayTime(final MfDate date) {
		this.payTime = date;
	}

	/**
	 * @param code
	 *            The payerOrgTypeCode to set.
	 */
	public void setPayerOrgTypeCode(final Integer code) {
		this.payerOrgTypeCode = code;
	}

	/**
	 * @return Returns the payeeMemberAccCode.
	 */
	public String getPayeeMemberAccCode() {
		return payeeMemberAccCode;
	}

	/**
	 * @param code
	 *            The payeeMemberAccCode to set.
	 */
	public void setPayeeMemberAccCode(final String code) {
		this.payeeMemberAccCode = code;
	}

	/**
	 * @return Returns the payerMemberAccCode.
	 */
	public String getPayerMemberAccCode() {
		return payerMemberAccCode;
	}

	/**
	 * @param code
	 *            The payerMemberAccCode to set.
	 */
	public void setPayerMemberAccCode(final String code) {
		this.payerMemberAccCode = code;
	}
	
	/**
	 * 处理支付事件.
	 * 
	 * @param payment
	 *            <code>Payment</code> object.
	 */
	public void process(final Payment payment) {

		if (null != postingRules && postingRules.size() > 0) {
			Iterator it = postingRules.iterator();
			while (it.hasNext()) {
				PostingRuleDTO rule = (PostingRuleDTO) it.next();
				AbstractPostingRule postingRule = PostingRuleFactory
						.createPostingRule(rule);// party
				if (null != postingRule) {
					this.setBankOrgCodeMappingDAO(payment.getBankOrgCodeMappingDAO());
					postingRule.process(this);
				}
			}
		}
		if (null != resultingEntries && resultingEntries.size() > 0) {
			Iterator it = resultingEntries.iterator();
			while (it.hasNext()) {
				AccountEntryDTO entry = (AccountEntryDTO) it.next();
				if (null != entry) {
					// list中的先后顺序.
					payment.addAccountingEntry(entry);
				}
			}
			if (logger.isInfoEnabled()) {
				logger.info("Accounting with " + resultingEntries.size()
						+ " entries generated.");
			}
		}
	}

	public String getPayeeFullMemberAccCode() {
		return payeeFullMemberAccCode;
	}

	public void setPayeeFullMemberAccCode(String payeeFullMemberAccCode) {
		this.payeeFullMemberAccCode = payeeFullMemberAccCode;
	}

	public String getPayerFullMemberAccCode() {
		return payerFullMemberAccCode;
	}

	public void setPayerFullMemberAccCode(String payerFullMemberAccCode) {
		this.payerFullMemberAccCode = payerFullMemberAccCode;
	}

	public Integer getPayerMemberType() {
		return payerMemberType;
	}

	public void setPayerMemberType(Integer payerMemberType) {
		this.payerMemberType = payerMemberType;
	}

	public Integer getPayeeMemberType() {
		return payeeMemberType;
	}

	public void setPayeeMemberType(Integer payeeMemberType) {
		this.payeeMemberType = payeeMemberType;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

}
