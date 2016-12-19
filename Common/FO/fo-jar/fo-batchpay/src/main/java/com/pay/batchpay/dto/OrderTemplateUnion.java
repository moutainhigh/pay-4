/**
 * 
 */
package com.pay.batchpay.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author PengJiangbo
 *
 */
public class OrderTemplateUnion {
	
	//id
	private long id ;
	//批次号
	private String bulkpayNo ;
	//申请时间
	private Date createTime ;
	//更新时间
	private Date updateTime ;
	//创建人
	private long creator ;
	//更新人
	private long updator ;
	//批量付款文件名
	private String fileName ;
	//批次付款状态
	private int status ;
	//付款结果说明
	private String resultDes ;
	//付款成功金额
	private BigDecimal success ;
	//付款中金额
	private BigDecimal paying ;
	//失败金额
	private BigDecimal fail ;
	//付款笔数
	private int template_Count ;
	//付款金额
	private BigDecimal sum_Amount ;

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

	public int getTemplate_Count() {
		return template_Count;
	}

	public void setTemplate_Count(int template_Count) {
		this.template_Count = template_Count;
	}

	public BigDecimal getSum_Amount() {
		return sum_Amount;
	}

	public void setSum_Amount(BigDecimal sum_Amount) {
		this.sum_Amount = sum_Amount;
	}

	public String getResultDes() {
		return resultDes;
	}

	public void setResultDes(String resultDes) {
		this.resultDes = resultDes;
	}

	public BigDecimal getSuccess() {
		return success;
	}

	public void setSuccess(BigDecimal success) {
		this.success = success;
	}

	public BigDecimal getPaying() {
		return paying;
	}

	public void setPaying(BigDecimal paying) {
		this.paying = paying;
	}

	public BigDecimal getFail() {
		return fail;
	}

	public void setFail(BigDecimal fail) {
		this.fail = fail;
	}

	@Override
	public String toString() {
		return "OrderTemplateUnion [id=" + id + ", bulkpayNo=" + bulkpayNo
				+ ", createTime=" + createTime + ", updateTime=" + updateTime
				+ ", creator=" + creator + ", updator=" + updator
				+ ", fileName=" + fileName + ", status=" + status
				+ ", resultDes=" + resultDes + ", success=" + success
				+ ", paying=" + paying + ", fail=" + fail + ", template_Count="
				+ template_Count + ", sum_Amount=" + sum_Amount + "]";
	}

}
