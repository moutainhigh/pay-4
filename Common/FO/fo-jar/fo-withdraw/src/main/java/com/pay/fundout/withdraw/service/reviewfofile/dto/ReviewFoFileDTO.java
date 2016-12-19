/**
 *  <p>File: ReviewFoFileDTO.java</p>
 *  <p>Description:</p>
 *  <p>Copyright: (c) 2004-2013 pay.com . All rights reserved.版权所有</p>
 *	<p>Company: </p>
 *  @author zengli
 *  @version 1.0  
 */
package com.pay.fundout.withdraw.service.reviewfofile.dto;

import java.math.BigDecimal;

import com.pay.inf.model.BaseObject;

/**
 * <p></p>
 * @author zengli
 * @since 2011-4-12
 * @see 
 */
public class ReviewFoFileDTO extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 交易流水号
	 */
	private Long serialNo;
	/**
	 * 结算种类
	 */
	private Integer  payType;
	/**
	 * 交易类型
	 */
	private Integer transactionType;
	/**
	 * 付款人帐号
	 */
	private String accountNo;
	/**
	 * 付款账户名称
	 */
	private String accountName;
	/**
	 * 收款人帐号
	 */
	private String payeeAccountNo;
	/**
	 * 收款人名称
	 */
	private String payeeName;
	/**
	 * 收款人开户行
	 */
	private String payeeBank;
	/**
	 * 用途说明
	 */
	private String usage;
	/**
	 * 交易币种
	 */
	private Integer fenCode;
	/**
	 * 钞汇种类
	 */
	private Integer currFlag;
	/**
	 * 交易金额
	 */
	private BigDecimal amount;
	/**
	 * 收款行行号
	 */
	private String zfbranchno;
	/**
	 * 
	 */
	private Integer tranStatus;
	private String errorCode;
	private String resultDesc;
	/**
	 * @return the serialNo
	 */
	public Long getSerialNo() {
		return serialNo;
	}
	/**
	 * @param serialNo the serialNo to set
	 */
	public void setSerialNo(Long serialNo) {
		this.serialNo = serialNo;
	}
	/**
	 * @return the resultDesc
	 */
	public String getResultDesc() {
		return resultDesc;
	}
	/**
	 * @param resultDesc the resultDesc to set
	 */
	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}
	/**
	 * @return the payType
	 */
	public Integer getPayType() {
		return payType;
	}
	/**
	 * @param payType the payType to set
	 */
	public void setPayType(Integer payType) {
		this.payType = payType;
	}
	/**
	 * @return the transactionType
	 */
	public Integer getTransactionType() {
		return transactionType;
	}
	/**
	 * @param transactionType the transactionType to set
	 */
	public void setTransactionType(Integer transactionType) {
		this.transactionType = transactionType;
	}
	/**
	 * @return the accountNo
	 */
	public String getAccountNo() {
		return accountNo;
	}
	/**
	 * @param accountNo the accountNo to set
	 */
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	/**
	 * @return the accountName
	 */
	public String getAccountName() {
		return accountName;
	}
	/**
	 * @param accountName the accountName to set
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	/**
	 * @return the payeeAccountNo
	 */
	public String getPayeeAccountNo() {
		return payeeAccountNo;
	}
	/**
	 * @param payeeAccountNo the payeeAccountNo to set
	 */
	public void setPayeeAccountNo(String payeeAccountNo) {
		this.payeeAccountNo = payeeAccountNo;
	}
	/**
	 * @return the payeeName
	 */
	public String getPayeeName() {
		return payeeName;
	}
	/**
	 * @param payeeName the payeeName to set
	 */
	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}
	/**
	 * @return the payeeBank
	 */
	public String getPayeeBank() {
		return payeeBank;
	}
	/**
	 * @param payeeBank the payeeBank to set
	 */
	public void setPayeeBank(String payeeBank) {
		this.payeeBank = payeeBank;
	}
	/**
	 * @return the usage
	 */
	public String getUsage() {
		return usage;
	}
	/**
	 * @param usage the usage to set
	 */
	public void setUsage(String usage) {
		this.usage = usage;
	}
	/**
	 * @return the fenCode
	 */
	public Integer getFenCode() {
		return fenCode;
	}
	/**
	 * @param fenCode the fenCode to set
	 */
	public void setFenCode(Integer fenCode) {
		this.fenCode = fenCode;
	}
	/**
	 * @return the currFlag
	 */
	public Integer getCurrFlag() {
		return currFlag;
	}
	/**
	 * @param currFlag the currFlag to set
	 */
	public void setCurrFlag(Integer currFlag) {
		this.currFlag = currFlag;
	}
	/**
	 * @return the amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	/**
	 * @return the zfbranchno
	 */
	public String getZfbranchno() {
		return zfbranchno;
	}
	/**
	 * @param zfbranchno the zfbranchno to set
	 */
	public void setZfbranchno(String zfbranchno) {
		this.zfbranchno = zfbranchno;
	}
	/**
	 * @return the tranStatus
	 */
	public Integer getTranStatus() {
		return tranStatus;
	}
	/**
	 * @param tranStatus the tranStatus to set
	 */
	public void setTranStatus(Integer tranStatus) {
		this.tranStatus = tranStatus;
	}
	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}
	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReviewFoFileDTO dto = (ReviewFoFileDTO) o;
//        if (!serialNo.equals(dto.serialNo)) return false;
        if (!usage.equals(dto.getUsage())) return false;
        if (!amount.equals(dto.getAmount())) return false;
        if (!payeeAccountNo.equals(dto.getPayeeAccountNo())) return false;
        if (!payeeName.equals(dto.getPayeeName())) return false;
//        if (!zfbranchno.equals(dto.getZfbranchno())) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result;
        result = serialNo.hashCode();
        result = 31 * result + amount.hashCode();
        return result;
    }
	
}
