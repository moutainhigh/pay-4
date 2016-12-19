/**
 * 
 */
package com.pay.txncore.model;

import java.sql.Timestamp;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 退款异常监控农行退款批量状态更新批次详情
 * @author PengJiangbo
 *
 */
public class RefundExceptionBatchDetail {

//	BATCH_NO
//	CREATE_TIME
//	UPDATE_TIME
//	CHANNEL_CREATE_TIME
//	CHANNEL_ORDER_NO
//	REFUND_RESULT
//	UPDATE_RESULT
//	REMARK
//	BATCH_DETAI_NO
//	EXTEND2
	/**
	 * 批次号
	 */
	private String batchNo ;
	/**
	 * 创建时间
	 */
	private Timestamp createTime ;
	/**
	 * 更新时间
	 */
	private Timestamp updateTime ;
	/**
	 * 渠道订单创建时间（考虑要不要该字段）
	 */
	private Timestamp channelCreateTime ;
	/**
	 * 渠道订单号
	 */
	private Long channelOrderNo ;
	/**
	 * 线下退款结果(S:成功，F:失败)
	 */
	private String refundResult ;
	/**
	 * 更新结果（S：成功，F：失败）
	 */
	private String updateResult ;
	/**
	 * 备注（失败原因）
	 */
	private String remark ;
	/**
	 * 批次详情编号
	 * @return
	 */
	private Long batchDetailNo ;
	
	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
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

	public Timestamp getChannelCreateTime() {
		return channelCreateTime;
	}

	public void setChannelCreateTime(Timestamp channelCreateTime) {
		this.channelCreateTime = channelCreateTime;
	}

	public Long getChannelOrderNo() {
		return channelOrderNo;
	}

	public void setChannelOrderNo(Long channelOrderNo) {
		this.channelOrderNo = channelOrderNo;
	}

	public String getRefundResult() {
		return refundResult;
	}

	public void setRefundResult(String refundResult) {
		this.refundResult = refundResult;
	}

	public String getUpdateResult() {
		return updateResult;
	}

	public void setUpdateResult(String updateResult) {
		this.updateResult = updateResult;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getBatchDetailNo() {
		return batchDetailNo;
	}

	public void setBatchDetailNo(Long batchDetailNo) {
		this.batchDetailNo = batchDetailNo;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE) ;
	}
	
}
