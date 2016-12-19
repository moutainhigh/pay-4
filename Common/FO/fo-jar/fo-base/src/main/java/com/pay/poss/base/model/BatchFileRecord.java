package com.pay.poss.base.model;

import java.util.Date;

import com.pay.inf.model.BaseObject;

/***
 * BATCH_FILE_RECORD
 * @author DDL
 *
 */

public class BatchFileRecord extends BaseObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/***
	 * 关联batch_file_info 主键file_ky
	 */
	private Long gFileKy;  
	/***
	 * 交易流水号 
	 */
	private Long tradeSeq;
	/***
	 * 交易金额
	 */
	private Long tradeAmount;
	/***
	 * 交易收款人账户
	 */
	private String accountNo;
	 /***
	  * 交易收款人名称
	  */
	private String accountName;
	/***
	 * 交易日期
	 */
	private  Date tradeDate;
	/***
	 * 备注
	 */
	private String remark;
	/***
	 * 状态
	 */
	private Integer stauts;
	/***
	 * 银行开户名称
	 */
	private String bankBranch;

	public Long getgFileKy() {
		return gFileKy;
	}

	public void setgFileKy(Long gFileKy) {
		this.gFileKy = gFileKy;
	}

	public Long getTradeSeq() {
		return tradeSeq;
	}

	public void setTradeSeq(Long tradeSeq) {
		this.tradeSeq = tradeSeq;
	}

	public Long getTradeAmount() {
		return tradeAmount;
	}

	public void setTradeAmount(Long tradeAmount) {
		this.tradeAmount = tradeAmount;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public Date getTradeDate() {
		return tradeDate;
	}

	public void setTradeDate(Date tradeDate) {
		this.tradeDate = tradeDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getStauts() {
		return stauts;
	}

	public void setStauts(Integer stauts) {
		this.stauts = stauts;
	}

	public String getBankBranch() {
		return bankBranch;
	}

	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}
	
}
