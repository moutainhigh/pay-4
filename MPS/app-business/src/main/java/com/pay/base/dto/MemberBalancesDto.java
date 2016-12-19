package com.pay.base.dto;

import java.math.BigDecimal;

import com.pay.base.common.enums.MemberStatusEnum;

/**
 * @author fjl
 * @date 2011-7-1
 */
public class MemberBalancesDto extends MemberDto {

	/** 会员中文名称 */
	private String name;

	/** 钢付通用户名 */
	private String usteelName;

	private BigDecimal balance = new BigDecimal(0d);

	private BigDecimal frozeAmount = new BigDecimal(0d);

	private BigDecimal withdrawBalance = new BigDecimal(0d);

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the balance
	 */
	public BigDecimal getBalance() {
		return balance;
	}

	/**
	 * @param balance
	 *            the balance to set
	 */
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	/**
	 * @return the frozeAmount
	 */
	public BigDecimal getFrozeAmount() {
		return frozeAmount;
	}

	/**
	 * @param frozeAmount
	 *            the frozeAmount to set
	 */
	public void setFrozeAmount(BigDecimal frozeAmount) {
		this.frozeAmount = frozeAmount;
	}

	/**
	 * @return the withdrawBalance
	 */
	public BigDecimal getWithdrawBalance() {
		return withdrawBalance;
	}

	/**
	 * @param withdrawBalance
	 *            the withdrawBalance to set
	 */
	public void setWithdrawBalance(BigDecimal withdrawBalance) {
		this.withdrawBalance = withdrawBalance;
	}

	/**
	 * @return
	 */
	public String getStatusName() {
		MemberStatusEnum s = MemberStatusEnum.getByCode(getStatus());
		if (s == null) {
			return "";
		}
		return s.getMessage();
	}

	/**
	 * @return the usteelName
	 */
	public String getUsteelName() {
		return usteelName;
	}

	/**
	 * @param usteelName
	 *            the usteelName to set
	 */
	public void setUsteelName(String usteelName) {
		this.usteelName = usteelName;
	}

}
