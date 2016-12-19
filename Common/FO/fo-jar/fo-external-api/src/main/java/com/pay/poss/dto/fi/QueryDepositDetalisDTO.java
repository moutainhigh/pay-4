/**
 *  <p>File: QueryDepositDetalisDTO.java</p>
 *  <p>Description:</p>
 *  <p>Copyright: (c) 2004-2013 pay.com . All rights reserved.版权所有</p>
 *	<p>Company: </p>
 *  @author terry_ma
 *  @version 1.0  
 */
package com.pay.poss.dto.fi;

import java.io.Serializable;
import java.util.Date;

public class QueryDepositDetalisDTO implements Serializable {
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 5583609133214695283L;

	/**
	 * 会员号
	 */
	private Long memberCode;

	/**
	 * 账户类型
	 */
	private Integer accountType;

	/**
	 * 登入名--暂未使用
	 */
	private String loginName;

	/**
	 * 充值流水号
	 */
	private Long depositProtocolId;

	/**
	 * 提交给银行的定单编号
	 */
	private String bankOrderId;

	/**
	 * 银行返回的清算流水号
	 */
	private String bankSerialNo;

	/**
	 * 充值时间
	 */
	private Date depositDate;

	/**
	 * 充值金额
	 */
	private Long depositAmount;

	/**
	 * 充值渠道,如建行网上银行:ccb001
	 */
	private String bankChannel;
	/**
	 * 充值渠道名称
	 */
	private String bankChannelName;

	/**
	 * @return the bankChannelName
	 */
	public String getBankChannelName() {
		return bankChannelName;
	}

	/**
	 * @param bankChannelName the bankChannelName to set
	 */
	public void setBankChannelName(String bankChannelName) {
		this.bankChannelName = bankChannelName;
	}

	/**
	 * 充值协议状态,0:创建,1:已报清算,2:清算成功,3:清算失败
	 */
	private Integer depositStatus;
	
	/**
	 * 商户号
	 */
	private String merchantId;
	
	private Integer refundType;

	public Long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}

	public Integer getAccountType() {
		return accountType;
	}

	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public Long getDepositProtocolId() {
		return depositProtocolId;
	}

	public void setDepositProtocolId(Long depositProtocolId) {
		this.depositProtocolId = depositProtocolId;
	}

	public Date getDepositDate() {
		return depositDate;
	}

	public void setDepositDate(Date depositDate) {
		this.depositDate = depositDate;
	}

	public Long getDepositAmount() {
		return depositAmount;
	}

	public void setDepositAmount(Long depositAmount) {
		this.depositAmount = depositAmount;
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

	
	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public Integer getRefundType() {
		return refundType;
	}

	public void setRefundType(Integer refundType) {
		this.refundType = refundType;
	}

	@Override
	public String toString() {
		return "QueryDepositDetalis [accountType=" + accountType
				+ ", bankChannel=" + bankChannel + ", bankOrderId="
				+ bankOrderId + ", bankSerialNo=" + bankSerialNo
				+ ", depositAmount=" + depositAmount + ", depositDate="
				+ depositDate + ", depositProtocolId=" + depositProtocolId
				+ ", depositStatus=" + depositStatus + ", loginName="
				+ loginName + ", memberCode=" + memberCode + ", merchantId="
				+ merchantId + "]";
	}
}
