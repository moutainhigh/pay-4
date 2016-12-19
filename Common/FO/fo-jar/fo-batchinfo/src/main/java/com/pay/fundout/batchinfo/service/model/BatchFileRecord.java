/**
 *  <p>File: BatchFileRecord.java</p>
 *  <p>Description:</p>
 *  <p>Copyright: (c) 2004-2013 pay.com . All rights reserved.版权所有</p>
 *	<p>Company: </p>
 *  @author zengli
 *  @version 1.0  
 */
package com.pay.fundout.batchinfo.service.model;

import java.math.BigDecimal;
import java.util.Date;

import com.pay.inf.model.BaseObject;

/**
 * <p>生成批次明细记录</p>
 * @author zengli
 * @since 2011-4-21
 * @see 
 */
public class BatchFileRecord extends BaseObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 生成文件FileKy
	 */
	private Long gFileKy;
	/**
	 * 交易流水号
	 */
	private Long tradeSeq;
	/**
	 * 交易金额
	 */
	private  Long tradeAmount;
	/**
	 * 收款人账户
	 */
	private String accountNo;
	/**
	 * 收款人名称
	 */
	private String accountName;
	/**
	 * 收款人开户行
	 */
	private String bankBranch;
	
	/**
	 * @return the gFileKy
	 */
	public Long getgFileKy() {
		return gFileKy;
	}
	/**
	 * @return the bankBranch
	 */
	public String getBankBranch() {
		return bankBranch;
	}
	/**
	 * @param bankBranch the bankBranch to set
	 */
	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}
	/**
	 * @param gFileKy the gFileKy to set
	 */
	public void setgFileKy(Long gFileKy) {
		this.gFileKy = gFileKy;
	}
	/**
	 * @return the tradeSeq
	 */
	public Long getTradeSeq() {
		return tradeSeq;
	}
	/**
	 * @param tradeSeq the tradeSeq to set
	 */
	public void setTradeSeq(Long tradeSeq) {
		this.tradeSeq = tradeSeq;
	}
	/**
	 * @return the tradeAmount
	 */
	public Long getTradeAmount() {
		return tradeAmount;
	}
	/**
	 * @param tradeAmount the tradeAmount to set
	 */
	public void setTradeAmount(Long tradeAmount) {
		this.tradeAmount = tradeAmount;
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
	 * @return the tradeDate
	 */
	public Date getTradeDate() {
		return tradeDate;
	}
	/**
	 * @param tradeDate the tradeDate to set
	 */
	public void setTradeDate(Date tradeDate) {
		this.tradeDate = tradeDate;
	}
	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 交易日期
	 */
	private Date tradeDate;
	/**
	 *备注
	 */
	private String remark;
	
	/***
	 *状态
	 * @param i
	 */
	private Integer stauts;

	public Integer getStauts() {
		return stauts;
	}
	public void setStauts(Integer stauts) {
		this.stauts = stauts;
	}
	
}
