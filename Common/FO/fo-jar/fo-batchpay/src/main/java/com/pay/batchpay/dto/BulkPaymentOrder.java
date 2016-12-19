/**
 * 
 */
package com.pay.batchpay.dto;

import java.util.Date;

/**
 * 批量付款订单
 * @author PengJiangbo
 *
 */
public class BulkPaymentOrder {
	
	//主键
	private long id ;
	//批量付款订单号（批次号）
	private String bulkpayNo ;
	//申请时间
	private Date createTime ;
	//更新时间
	private Date updateTime ;
	//创建人，关联商户会员号
	private long creator ;
	//更新人
	private long updator ;
	//付款文件名
	private String fileName ;
	//模板文件路径
	private String filePath ;
	//付款状态，0：待审核1：商户审核拒绝2：商户审核通过待复核3：复核拒绝4：付款中5：付款完成6：已作废
	private int status ;
	
	//登录标志
	private String loginName ;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getBulkpayNo() {
		return bulkpayNo;
	}

	public void setBulkpayNo(String bulkpayNo) {
		this.bulkpayNo = bulkpayNo;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public long getCreator() {
		return creator;
	}

	public void setCreator(long creator) {
		this.creator = creator;
	}

	public long getUpdator() {
		return updator;
	}

	public void setUpdator(long updator) {
		this.updator = updator;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@Override
	public String toString() {
		return "BulkPaymentOrder [id=" + id + ", bulkpayNo=" + bulkpayNo
				+ ", createTime=" + createTime + ", updateTime=" + updateTime
				+ ", creator=" + creator + ", updator=" + updator
				+ ", fileName=" + fileName + ", filePath=" + filePath
				+ ", status=" + status + ", loginName=" + loginName + "]";
	}

}
