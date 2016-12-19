/**
 * 
 */
package com.pay.txncore.model;

import java.sql.Timestamp;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 退款异常监控农行退款批量状态更新
 * @author PengJiangbo
 *
 */
public class RefundExceptionBatch {

//	BATCH_NO
//	CREATOR
//	CREATE_TIME
//	UPDATE_TIME
//	BATCH_TOTAL
//	EXTEND1
//	EXTEND2
	/**
	 * 批次号
	 */
	private String batchNo ;
	/**
	 * 创建人
	 */
	private String creator ;
	/**
	 * 创建时间
	 */
	private Timestamp createTime ;
	/**
	 * 更新时间
	 */
	private Timestamp updateTime ;
	/**
	 * 批次数量
	 */
	private Long batchTotal ;

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public Long getBatchTotal() {
		return batchTotal;
	}

	public void setBatchTotal(Long batchTotal) {
		this.batchTotal = batchTotal;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE) ;
	}
	
	
}
