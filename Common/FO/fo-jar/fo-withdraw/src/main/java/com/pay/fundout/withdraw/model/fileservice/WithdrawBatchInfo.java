package com.pay.fundout.withdraw.model.fileservice;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.pay.inf.model.BaseObject;

public class WithdrawBatchInfo extends BaseObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String channelCode;
	
	private Long fileKy ;
	
	private Integer flag;
	
	
	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	/**
	 * @return the fileKy
	 */
	public Long getFileKy() {
		return fileKy;
	}

	/**
	 * @param fileKy the fileKy to set
	 */
	public void setFileKy(Long fileKy) {
		this.fileKy = fileKy;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	private transient Long sureCount;
	
	public Long getSureCount() {
		return sureCount;
	}

	public void setSureCount(Long sureCount) {
		this.sureCount = sureCount;
	}

	/**
	 * 主键
	 */
	@NotNull
    private Long batchKy;
    /**
     * 批次号
     */
	@NotNull
    private String batchNum;
    /**
     * 文件路径
     */
	@NotNull
    private String filePath;
    /**
     * 文件名称
     */
	@NotNull
	@Size(max = 30)
	private String fileName;
    /**
     * 文件类型 1：外部文件 2：内部文件
     */
	@NotNull
    private Integer fileType;
    /**
     * 总金额
     */
	@NotNull
    private BigDecimal allAmount;
    /**
     * 总笔数
     */
	@NotNull
    private Long allCount;
    /**
     * 银行编码
     */
	@NotNull
    private String bankCode;
    /**
     * 操作人标识
     */
	@NotNull
    private String operators;
    /**
     * 操作时间。默认当前系统时间
     */
	@NotNull
    private Date updateTime;
    /**
     * 状态 0：文件无效 1： 文件有效
     */
	@NotNull
    private Integer status;
    /**
     * 规则号
     */
	@NotNull
    private Long ruleKy;
    /**
     * 规则名称
     */
	@NotNull
    private String ruleName;
    /**
     * 批次文件状态
     */
	@NotNull
    private Integer batchFileStatus;
    /**
     * 批次文件对应的状态值
     */
    private String batchFileStatusDesc;
	/**
	 * 批次状态
	 */
	private String statusDesc;
	
	
	private short fileBusiType;
	
	
	private String fileBusiTypeStr;
	/***
	 * 出款流水号
	 */
	private String tradeSeq;
	/**
	 * 出款状态
	 */
	private String finStatus;
	
	
	public String getFinStatus() {
		return finStatus;
	}

	public void setFinStatus(String finStatus) {
		this.finStatus = finStatus;
	}

	public String getTradeSeq() {
		return tradeSeq;
	}

	public void setTradeSeq(String tradeSeq) {
		this.tradeSeq = tradeSeq;
	}

	/**
	 * @return the fileBusiTypeStr
	 */
	public String getFileBusiTypeStr() {
		return fileBusiTypeStr;
	}

	/**
	 * @param fileBusiTypeStr the fileBusiTypeStr to set
	 */
	public void setFileBusiTypeStr(String fileBusiTypeStr) {
		this.fileBusiTypeStr = fileBusiTypeStr;
	}

	private Date foreviewTime;

	/**
	 * @return the foreviewTime
	 */
	public Date getForeviewTime() {
		return foreviewTime;
	}

	/**
	 * @param foreviewTime the foreviewTime to set
	 */
	public void setForeviewTime(Date foreviewTime) {
		this.foreviewTime = foreviewTime;
	}

	/**
	 * @return the fileBusiType
	 */
	public short getFileBusiType() {
		return fileBusiType;
	}

	/**
	 * @param fileBusiType the fileBusiType to set
	 */
	public void setFileBusiType(short fileBusiType) {
		this.fileBusiType = fileBusiType;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	/**
     * 生成时间
     */
    private Date generateTime ;
    private String generateTimes;
    public String getGenerateTimes() {
		return generateTimes;
	}

	public void setGenerateTimes(String generateTimes) {
		this.generateTimes = generateTimes;
	}

	/**
     * 下载时间
     */
    private Date downloadTime ;
    /**
     * 导入时间
     */
    private Date importTime;
    /**
     * 确认导入时间
     */
    private Date sureImportTime;
    
    
	public Date getGenerateTime() {
		return generateTime;
	}

	public void setGenerateTime(Date generateTime) {
		this.generateTime = generateTime;
	}

	public Date getDownloadTime() {
		return downloadTime;
	}

	public void setDownloadTime(Date downloadTime) {
		this.downloadTime = downloadTime;
	}

	public Date getImportTime() {
		return importTime;
	}

	public void setImportTime(Date importTime) {
		this.importTime = importTime;
	}

	public Date getSureImportTime() {
		return sureImportTime;
	}

	public void setSureImportTime(Date sureImportTime) {
		this.sureImportTime = sureImportTime;
	}

	public String getBatchFileStatusDesc() {
		return batchFileStatusDesc;
	}

	public void setBatchFileStatusDesc(String batchFileStatusDesc) {
		this.batchFileStatusDesc = batchFileStatusDesc;
	}

	/**
     * 业务类型
     */
    private Integer batchType ;
    public Integer getBatchType() {
		return batchType;
	}

	public void setBatchType(Integer batchType) {
		this.batchType = batchType;
	}

	/**
     * 下载批次文件的次数 默认0
     */
    private Integer dlBatchCount;
    /**
     * 下载批次业务汇款单 默认0
     */
    private Integer dlBatchBusiCount;
    /**
     * 下载银行提交批次文件的次数 默认0
     */
    private Integer dlBankCount;
    
    private Integer dlBankBusiCount;
    
	public Integer getDlBatchBusiCount() {
		return dlBatchBusiCount;
	}

	public void setDlBatchBusiCount(Integer dlBatchBusiCount) {
		this.dlBatchBusiCount = dlBatchBusiCount;
	}

	public Integer getDlBankBusiCount() {
		return dlBankBusiCount;
	}

	public void setDlBankBusiCount(Integer dlBankBusiCount) {
		this.dlBankBusiCount = dlBankBusiCount;
	}

	public Integer getDlBatchCount() {
		return dlBatchCount;
	}

	public void setDlBatchCount(Integer dlBatchCount) {
		this.dlBatchCount = dlBatchCount;
	}


	public Integer getDlBankCount() {
		return dlBankCount;
	}

	public void setDlBankCount(Integer dlBankCount) {
		this.dlBankCount = dlBankCount;
	}

	public Integer getBatchFileStatus() {
		return batchFileStatus;
	}

	public void setBatchFileStatus(Integer batchFileStatus) {
		this.batchFileStatus = batchFileStatus;
	}


	public Long getRuleKy() {
		return ruleKy;
	}

	public void setRuleKy(Long ruleKy) {
		this.ruleKy = ruleKy;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getBatchNum() {
        return batchNum;
    }

    public void setBatchNum(String batchNum) {
        this.batchNum = batchNum;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


    public BigDecimal getAllAmount() {
        return allAmount;
    }

    public void setAllAmount(BigDecimal allAmount) {
        this.allAmount = allAmount;
    }


	public Long getBatchKy() {
		return batchKy;
	}

	public void setBatchKy(Long batchKy) {
		this.batchKy = batchKy;
	}

	public Integer getFileType() {
		return fileType;
	}

	public void setFileType(Integer fileType) {
		this.fileType = fileType;
	}

	public Long getAllCount() {
		return allCount;
	}

	public void setAllCount(Long allCount) {
		this.allCount = allCount;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getOperators() {
        return operators;
    }

    public void setOperators(String operators) {
        this.operators = operators;
    }


	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getStatus() {
		return status;
	}

}