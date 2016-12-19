package com.pay.fundout.withdraw.model.result;

import java.math.BigDecimal;
import java.util.Date;

import com.pay.inf.model.BaseObject;
/**
 * 
 * <p>导入文件提现数据DTO</p>
 * @author Henry.Zeng
 * @since 2010-9-7
 * @see
 */
public class WithdrawImportRecord extends BaseObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 银行导入文件KY(主键)
	 */
    private Long importKy;
    /**
     * 关联导入的文件号
     */
    private Long fileKy;
    /**
     * 批次号（冗余）
     */
    private String batchNum;
    /**
     * 银行编号（冗余）
     */
    private String bankCode;
    /**
     * 银行返回流水
     */
    private String bankSeq;
    /**
     * 银行交易金额
     */
    private BigDecimal bankAmount;
    /**
     * 银行状态
     */
    private String bankStatus;
    /**
     * 银行交易时间
     */
    private Date busiTime;
    /**
     * 1：默认值
		0：重复无效
     */
    private String status;
    /**
     * 订单流水号
     */
    private String orderSeq;
    
    private String bankAcctName;
    
    private String bankAcct;
    
    private String bankBranch;
    
    private String bankRemark;
    
    private String bankFailureReason;
    


    public String getOrderSeq() {
		return orderSeq;
	}

	public void setOrderSeq(String orderSeq) {
		this.orderSeq = orderSeq;
	}

	public String getBankAcctName() {
		return bankAcctName;
	}

	public void setBankAcctName(String bankAcctName) {
		this.bankAcctName = bankAcctName;
	}

	public String getBankAcct() {
		return bankAcct;
	}

	public void setBankAcct(String bankAcct) {
		this.bankAcct = bankAcct;
	}

	public String getBankBranch() {
		return bankBranch;
	}

	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}

	public String getBankRemark() {
		return bankRemark;
	}

	public void setBankRemark(String bankRemark) {
		this.bankRemark = bankRemark;
	}

	public String getBankFailureReason() {
		return bankFailureReason;
	}

	public void setBankFailureReason(String bankFailureReason) {
		this.bankFailureReason = bankFailureReason;
	}

	public Long getImportKy() {
		return importKy;
	}

	public void setImportKy(Long importKy) {
		this.importKy = importKy;
	}

	public Long getFileKy() {
		return fileKy;
	}

	public void setFileKy(Long fileKy) {
		this.fileKy = fileKy;
	}


	public String getBatchNum() {
		return batchNum;
	}

	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}

	public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankSeq() {
        return bankSeq;
    }

    public void setBankSeq(String bankSeq) {
        this.bankSeq = bankSeq;
    }

    public BigDecimal getBankAmount() {
        return bankAmount;
    }

    public void setBankAmount(BigDecimal bankAmount) {
        this.bankAmount = bankAmount;
    }

    public String getBankStatus() {
        return bankStatus;
    }

    public void setBankStatus(String bankStatus) {
        this.bankStatus = bankStatus;
    }

    public Date getBusiTime() {
        return busiTime;
    }

    public void setBusiTime(Date busiTime) {
        this.busiTime = busiTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}