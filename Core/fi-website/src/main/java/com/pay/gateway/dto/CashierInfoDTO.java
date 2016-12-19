/**
 * 
 */
package com.pay.gateway.dto;

import java.util.List;
import java.util.Map;

/**
 * @author huhb
 *
 */
public class CashierInfoDTO {
	/**
	 * 付款方用户
	 */
	private Long payer;

	/**
	 * 是否存在支付密码
	 */
	private boolean isHavePaymentPwd;

	/**
	 * 帐户余额
	 */
	private Long accountBalance;

	/**
	 * 会员号
	 */
	private Long memberCode;

	/**
	 * 会员名称
	 */
	private String name;

	/**
	 * 会员类型
	 */
	private Integer memberType;

	private boolean hasAcctPay;

	private boolean hasBankPay;

	private boolean hasCardPay;

	private boolean hasConsumeCardPay;

	private boolean hasDebitQuick;

	private boolean hasCreditQuick;

	private boolean hasCrossPay;

	// private boolean
	private List<Map> orgItemDtos;

	// b2c银行列表
	private List<Map> b2cOrgItemDtos;
	// b2b银行列表
	private List<Map> b2bOrgItemDtos;

	Map<String, List<Map>> itemSortMap;

	public boolean isHasAcctPay() {
		return hasAcctPay;
	}

	public void setHasAcctPay(boolean hasAcctPay) {
		this.hasAcctPay = hasAcctPay;
	}

	public boolean isHasBankPay() {
		return hasBankPay;
	}

	public void setHasBankPay(boolean hasBankPay) {
		this.hasBankPay = hasBankPay;
	}

	public boolean isHasCardPay() {
		return hasCardPay;
	}

	public void setHasCardPay(boolean hasCardPay) {
		this.hasCardPay = hasCardPay;
	}

	public boolean isHasCrossPay() {
		return hasCrossPay;
	}

	public void setHasCrossPay(boolean hasCrossPay) {
		this.hasCrossPay = hasCrossPay;
	}

	public List<Map> getOrgItemDtos() {
		return orgItemDtos;
	}

	public void setOrgItemDtos(List<Map> orgItemDtos) {
		this.orgItemDtos = orgItemDtos;
	}

	public Long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getMemberType() {
		return memberType;
	}

	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}

	public Long getPayer() {
		return payer;
	}

	public void setPayer(Long payer) {
		this.payer = payer;
	}

	public boolean isHavePaymentPwd() {
		return isHavePaymentPwd;
	}

	public void setHavePaymentPwd(boolean isHavePaymentPwd) {
		this.isHavePaymentPwd = isHavePaymentPwd;
	}

	public Long getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(Long accountBalance) {
		this.accountBalance = accountBalance;
	}

	public List<Map> getB2cOrgItemDtos() {
		return b2cOrgItemDtos;
	}

	public void setB2cOrgItemDtos(List<Map> b2cOrgItemDtos) {
		this.b2cOrgItemDtos = b2cOrgItemDtos;
	}

	public List<Map> getB2bOrgItemDtos() {
		return b2bOrgItemDtos;
	}

	public void setB2bOrgItemDtos(List<Map> b2bOrgItemDtos) {
		this.b2bOrgItemDtos = b2bOrgItemDtos;
	}

	public boolean isHasConsumeCardPay() {
		return hasConsumeCardPay;
	}

	public void setHasConsumeCardPay(boolean hasConsumeCardPay) {
		this.hasConsumeCardPay = hasConsumeCardPay;
	}

	public boolean isHasDebitQuick() {
		return hasDebitQuick;
	}

	public void setHasDebitQuick(boolean hasDebitQuick) {
		this.hasDebitQuick = hasDebitQuick;
	}

	public boolean isHasCreditQuick() {
		return hasCreditQuick;
	}

	public void setHasCreditQuick(boolean hasCreditQuick) {
		this.hasCreditQuick = hasCreditQuick;
	}

	@Override
	public String toString() {
		return "CashierInfoDTO [accountBalance=" + accountBalance
				+ ", b2bOrgItemDtos=" + b2bOrgItemDtos + ", b2cOrgItemDtos="
				+ b2cOrgItemDtos + ", hasAcctPay=" + hasAcctPay
				+ ", hasBankPay=" + hasBankPay + ", hasCardPay=" + hasCardPay
				+ ", hasDebitQuick=" + hasDebitQuick + ", hasCreditQuick="
				+ hasCreditQuick + ", isHavePaymentPwd=" + isHavePaymentPwd
				+ ", itemSortMap=" + itemSortMap + ", memberCode=" + memberCode
				+ ", memberType=" + memberType + ", name=" + name
				+ ", orgItemDtos=" + orgItemDtos + ", payTypeEnum="
				+ ", payer=" + payer + " ,hasConsumeCardPay="
				+ hasConsumeCardPay + "]";
	}

}
