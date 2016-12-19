/**
 *  <p>File: DepositQueryParamDTO.java</p>
 *  <p>Description:</p>
 *  <p>Copyright: (c) 2004-2013 pay.com . All rights reserved.版权所有</p>
 *	<p>Company: </p>
 *  @author terry_ma
 *  @version 1.0  
 */
package com.pay.txncore.dto;

import java.io.Serializable;
import java.util.Date;

public class DepositQueryParamDTO implements Serializable {

	private static final long serialVersionUID = -4918806643610342837L;

	/**
	 * 会员号
	 */
	private Long memberCode;

	/**
	 * 充值流水号
	 */
	private Long depositProtocolId;

	/**
	 * 充值起始日期
	 */
	private Date depositProtocolStartDate;

	/**
	 * 充值结束日期
	 */
	private Date depositProtocolEndDate;

	/**
	 * 充值协议状态,0:创建,1:已报清算,2:清算成功,3:清算失败
	 */
	private Integer depositStatus;

	/**
	 * 提交给银行的定单编号
	 */
	private String bankOrderId;

	/**
	 * 银行返回的清算流水号
	 */
	private String bankSerialNo;

	/**
	 * 充值渠道（全部，列举各大合作银行）；
	 */
	private String bankChannel;

	/**
	 * 登入名--暂未使用
	 */
	private String loginName;

	/**
	 * 账户类型--暂未使用
	 */
	private Integer accountType;

	/**
	 * 会员类型--暂未使用
	 */
	private Integer memberTypeEnum;

	/**
	 * 当前是第几页
	 */
	private Integer pageNo;

	/**
	 * 本页大小
	 */
	private Integer pageSize;

	public Long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}

	public Long getDepositProtocolId() {
		return depositProtocolId;
	}

	public void setDepositProtocolId(Long depositProtocolId) {
		this.depositProtocolId = depositProtocolId;
	}

	public Date getDepositProtocolStartDate() {
		return depositProtocolStartDate;
	}

	public void setDepositProtocolStartDate(Date depositProtocolStartDate) {
		this.depositProtocolStartDate = depositProtocolStartDate;
	}

	public Date getDepositProtocolEndDate() {
		return depositProtocolEndDate;
	}

	public void setDepositProtocolEndDate(Date depositProtocolEndDate) {
		this.depositProtocolEndDate = depositProtocolEndDate;
	}

	public Integer getDepositStatus() {
		return depositStatus;
	}

	public void setDepositStatus(Integer depositStatus) {
		this.depositStatus = depositStatus;
	}

	public String getBankChannel() {
		return bankChannel;
	}

	public void setBankChannel(String bankChannel) {
		this.bankChannel = bankChannel;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public Integer getAccountType() {
		return accountType;
	}

	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}

	public Integer getMemberTypeEnum() {
		return memberTypeEnum;
	}

	public void setMemberTypeEnum(Integer memberTypeEnum) {
		this.memberTypeEnum = memberTypeEnum;
	}

	public Integer getPageNo() {
		if (null == pageNo) {
			pageNo = 1;
		}
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		if (null == pageSize) {
			pageSize = 100;
		}
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getBankOrderId() {
		return bankOrderId;
	}

	public void setBankOrderId(String bankOrderId) {
		this.bankOrderId = bankOrderId;
	}

	public String getBankSerialNo() {
		return bankSerialNo;
	}

	public void setBankSerialNo(String bankSerialNo) {
		this.bankSerialNo = bankSerialNo;
	}

	@Override
	public String toString() {
		return "DepositQueryParamDTO [accountType=" + accountType
				+ ", bankChannel=" + bankChannel + ", bankOrderId="
				+ bankOrderId + ", bankSerialNo=" + bankSerialNo
				+ ", depositProtocolEndDate=" + depositProtocolEndDate
				+ ", depositProtocolId=" + depositProtocolId
				+ ", depositProtocolStartDate=" + depositProtocolStartDate
				+ ", depositStatus=" + depositStatus + ", loginName="
				+ loginName + ", memberCode=" + memberCode
				+ ", memberTypeEnum=" + memberTypeEnum + ", pageNo=" + pageNo
				+ ", pageSize=" + pageSize + "]";
	}

}
