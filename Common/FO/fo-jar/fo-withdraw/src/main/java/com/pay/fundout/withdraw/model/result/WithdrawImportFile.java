package com.pay.fundout.withdraw.model.result;

import java.util.Date;

import com.pay.inf.model.BaseObject;
/**
 * 
 * <p>提现导入文件DTO</p>
 * @author Henry.Zeng
 * @since 2010-9-7
 * @see
 */
public class WithdrawImportFile extends BaseObject {
	Integer category;
	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	/**
	 * 生成文件Ky
	 */
	private Long gFileKy;
    /**
	 * @return the gFileKy
	 */
	public Long getgFileKy() {
		return gFileKy;
	}

	/**
	 * @param gFileKy the gFileKy to set
	 */
	public void setgFileKy(Long gFileKy) {
		this.gFileKy = gFileKy;
	}

	private String batchName;
	
	/**
	 * @return the batchName
	 */
	public String getBatchName() {
		return batchName;
	}

	/**
	 * @param batchName the batchName to set
	 */
	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 文件编号
	 */
	private Long fileKy;
	/**
	 * 批次号
	 */
    private String batchNum;
    /**
     * 银行编号
     */
    private String bankCode;
    /**
     * 状态文件名称
     */
    private String fileName;
    /**
     * 上传时间戳
     */
    private Date uploadTime;
    /**
     * 1：上传完毕
		2：上传失败
		2：解析成功
		3：解析失败
		4：导入数据库成功
		5：对账完成
		6：对账失败 
     */
    private String status;
    /**
     * 错误提示
     */
    private String errorTips;
    
    private String filePath;
    /**
     *1对公,0对私,2兼容对公对私标志
     */
    private String tradeType;
    
    /**
     * 传入获取解析文件类型 FC代表复核FR代表结果导入
     */
    private String bussinessType;
    
    /**
	 * @return the bussinessType
	 */
	public String getBussinessType() {
		return bussinessType;
	}

	/**
	 * @param bussinessType the bussinessType to set
	 */
	public void setBussinessType(String bussinessType) {
		this.bussinessType = bussinessType;
	}

	/**
	 * @return the tradeType
	 */
	public String getTradeType() {
		return tradeType;
	}

	/**
	 * @param tradeType the tradeType to set
	 */
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getOperators() {
		return operators;
	}

	public void setOperators(String operators) {
		this.operators = operators;
	}

	private String operators;

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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorTips() {
        return errorTips;
    }

    public void setErrorTips(String errorTips) {
        this.errorTips = errorTips;
    }
}