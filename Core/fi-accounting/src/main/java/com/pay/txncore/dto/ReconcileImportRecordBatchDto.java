/*
 * modify history 2016-08-12
 * nico.shao 增加了状态值 ，以及开始处理等几个字段
 */
package com.pay.txncore.dto;

import java.util.Date;

import com.pay.inf.model.BaseObject;
import com.pay.txncore.commons.ReconcileBatchStatusEnum;


public class ReconcileImportRecordBatchDto extends BaseObject{
	/***
	 * 批次号
	 */
	private String bacthNo;
	/**
	 * 申请笔数
	 */
	private Integer applyCount;
	/**
	 * 成功笔数
	 */
	private Integer successCount;
	/***
	 * 是否已对账
	 */
	private Integer status;
	/***
	 * 创建时间
	 */
	private Date createDate;
	/***
	 * 渠道
	 */
	private String orgCode;
	/***
	 * 文件名称
	 */
	private String fileName;
	/**
	 * 操作人
	 */
	private String operator;

	//add by nico.shao 2016-08-12

	private Date preProcessStartTime;		//预处理开始时间
	private Date preProcessEndTime;			//预处理结束时间
	private Integer preProcessSuccessNum;	//预处理成功次数
	private Date batchStartTime;		//批处理开始时间
	private Date batchEndTime;			//批处理结束时间
	private Integer batchProcessNum;	//预处理成功次数
	private Integer paymentOrderNum;	//支付订单数
	private Integer paymentOrderSuccess;	//支付订单成功数
	private Integer paymentOrderFailed;		//支付订单失败数
	private Integer refundOrderNum;		//退款订单数目
	private Integer refundOrderSuccess;	//支付订单成功数
	private Integer refundOrderFailed;		//支付订单失败数
	private String refundOrderRemark;		//remark
	
	//end 2016-08-12
	public void initValue(String batchNo,Integer status){
		setStatus(status);
		setBacthNo(batchNo);
		setSuccessCount(Integer.valueOf(0));
	
		setPaymentOrderFailed(Integer.valueOf(0));
		setPaymentOrderNum(Integer.valueOf(0));
		setPaymentOrderSuccess(Integer.valueOf(0));
		setRefundOrderNum(Integer.valueOf(0));
		setRefundOrderFailed(Integer.valueOf(0));
		setRefundOrderSuccess(Integer.valueOf(0));
	
	}
	

	public Date getPreProcessStartTime(){
		return this.preProcessStartTime;
	}
	public void setPreProcessStartTime(Date preProcessStartTime){
		this.preProcessStartTime = preProcessStartTime;
	}
	
	public Date getPreProcessEndTime(){
		return this.preProcessEndTime;
	}
	
	public void setPreProcessEndTime(Date preProcessEndTime) {
		this.preProcessEndTime = preProcessEndTime;
	}
	
	public Integer preProcessSuccessNum(){
		return this.preProcessSuccessNum;
	}
	
	public void setPreProcessSuccessNum(Integer preProcessSuccessNum){
		this.preProcessSuccessNum = preProcessSuccessNum;
	}
	
	public Date getBatchStartTime(){
		return this.batchStartTime;
	}
	
	public void setBatchStartTime(Date batchStartTime){
		this.batchStartTime = batchStartTime;
	}
	
	public Date getBatchEndTime(){
		return this.batchEndTime;
	}
	
	public void setBatchEndTime(Date batchEndTime){
		this.batchEndTime = batchEndTime;
	}
	
	public Integer getBatchProcessNum(){
		return this.batchProcessNum;
	}
	
	public void setBatchProcessNum(Integer batchProcessNum){
		this.batchProcessNum = batchProcessNum;
	}
	
	public Integer getPaymentOrderNum(){
		return this.paymentOrderNum;
	}
	
	public void setPaymentOrderNum(Integer paymentOrderNum){
		this.paymentOrderNum = paymentOrderNum; 
	}
	
	public Integer getPaymentOrderSuccess(){
		return this.paymentOrderSuccess;
	}
	public void setPaymentOrderSuccess(Integer paymentOrderSuccess) {
		this.paymentOrderSuccess = paymentOrderSuccess;
	}
	
	public Integer getPaymentOrderFailed(){
		return this.paymentOrderFailed;
	}
	
	public void setPaymentOrderFailed(Integer paymentOrderFailed){
		this.paymentOrderFailed = paymentOrderFailed;
	}
	
	public Integer getRefundOrderNum(){
		return this.refundOrderNum;
	}
	
	public void setRefundOrderNum(Integer refundOrderNum){
		this.refundOrderNum = refundOrderNum;
	}	
	
	public Integer getRefundOrderSuccess(){
		return this.refundOrderSuccess;
	}
	
	public void setRefundOrderSuccess(Integer refundOrderSuccess){
		this.refundOrderSuccess = refundOrderSuccess;
	}
	
	public Integer getRefundOrderFailed(){
		return this.refundOrderFailed;
	}
	
	public void setRefundOrderFailed(Integer refundOrderFailed){
		this.refundOrderFailed = refundOrderFailed;
	}
	
	public String getRefundOrderRemark(){
		return this.refundOrderRemark;
	}
	
	public void setRefundOrderRemark(String refundOrderRemark){
		this.refundOrderRemark = refundOrderRemark;
	}
	
	
	public void setBacthNo(String bacthNo) {
		this.bacthNo = bacthNo;
	}

	public Integer getApplyCount() {
		return applyCount;
	}

	public void setApplyCount(Integer applyCount) {
		this.applyCount = applyCount;
	}

	public Integer getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(Integer successCount) {
		this.successCount = successCount;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getBacthNo() {
		return bacthNo;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	@Override
	public String toString(){
		return "ReconcileImportRecordBatchDto [bacthNo=" + bacthNo + ", createDate="
				+ createDate + ", applyCount=" + applyCount
				+ ", successCount=" + successCount + ", status=" + status
				+ ", orgCode=" + orgCode + ", fileName="
				+ fileName + ", operator=" + operator + ", preProcessStartTime="
				+ preProcessStartTime + ", preProcessEndTime=" + preProcessEndTime 
				+ ", preProcessSuccessNum=" + preProcessSuccessNum + ", batchStartTime=" + batchStartTime
				+ ", batchEndTime=" + batchEndTime + ", batchProcessNum="
				+ batchProcessNum + ", paymentOrderNum=" + paymentOrderNum + ", paymentOrderSuccess="
				+ paymentOrderSuccess + ", paymentOrderFailed=" + paymentOrderFailed 
				+", refundOrderSuccess=" + refundOrderSuccess + ", refundOrderFailed=" 
				+ refundOrderFailed + ", refundOrderRemark=" + refundOrderRemark
				+ "]";
	}		
}
