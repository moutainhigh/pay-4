/**
 *  File: RefundDetailInfoDTO.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-20    jason_wang      Changes
 *  
 *
 */
package com.pay.poss.refund.model;

import java.math.BigDecimal;
import java.util.Date;

import com.pay.inf.model.BaseObject;

/**
 * @author Jason_wang
 *
 */
public class RefundDetailInfoDTO extends BaseObject {
	
	private static final long serialVersionUID = 4180022026396457378L;
	
	private Long orderKy;//订单号(主键)
    private String memberCode;//会员号
    private String memberName;//会员姓名
    private String memberType;//申请人会员类型。默认个人会员
    private String memberAcc;//账户号（登录名）
    private Long memberAccType;//帐户类型。默认人民币账户
    private Date applyTime;//充退申请时间
    private BigDecimal applyAmount;//充退申请金额，以分为单位
    private String applyReason;//充退申请理由。
    private String applyFrom;//申请发起系统标识，目前有APP和POSS
    private Long orderStatus;//充退受理状态
							    /*0：充退处理中
							    1：充退成功
							    2：充退失败 */
    private Long workorderKy;//主键(工单号)
    private Long refundMKy;//主充退订单流水号
    private String workflowKy;//工作流实例ID
    private String batchNum;//批次号。自动跑批后将批次号更新到此字段，默认值为“SYS_0”
    private Long batchStatus;//批次状态
									    /*0：未出批次
									    1：已出批次
									    2：批次废除*/

    private Long refundStatus;//状态
							    /*0：工单初始
							    1：审核通过
							    2：审核拒绝
							    3：审核滞留
							    4：复核通过
							    5：复核拒绝
							    6：清算拒绝
							    7：完成*/
    private String accTypeStr;	//账户类型名称
    
	private String statusStr;	//状态描述
	
	private String payeeName; //退款商户名称
	
	
	
	public String getPayeeName() {
		return payeeName;
	}

	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	public String getAccTypeStr() {
		if(null == memberAccType) return "";
		if(10 == memberAccType.longValue()){
			return "人民币";
		}else{
			return memberAccType.toString();
		}
	}

	public void setAccTypeStr(String accTypeStr) {
		this.accTypeStr = accTypeStr;
	}

	public String getStatusStr() {
		if(null == refundStatus) return "";
		long temp = refundStatus.longValue();
		if(0 == temp){
			return "工单初始";
		}else if(1 == temp){
			return "审核通过";
		}else if(2 == temp){
			return "审核拒绝";
		}else if(3 == temp){
			return "审核滞留";
		}else if(4 == temp){
			return "复核通过";
		}else if(5 == temp){
			return "复核拒绝";
		}else if(6 == temp){
			return "清算拒绝";
		}else if(7 == temp){
			return "完成";
		}else{
			return refundStatus.toString();
		}
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}

	public Long getOrderKy() {
		return orderKy;
	}

	public void setOrderKy(Long orderKy) {
		this.orderKy = orderKy;
	}

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMemberType() {
		return memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	public String getMemberAcc() {
		return memberAcc;
	}

	public void setMemberAcc(String memberAcc) {
		this.memberAcc = memberAcc;
	}

	public Long getMemberAccType() {
		return memberAccType;
	}

	public void setMemberAccType(Long memberAccType) {
		this.memberAccType = memberAccType;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public BigDecimal getApplyAmount() {
		return applyAmount;
	}

	public void setApplyAmount(BigDecimal applyAmount) {
		this.applyAmount = applyAmount;
	}

	public String getApplyReason() {
		return applyReason;
	}

	public void setApplyReason(String applyReason) {
		this.applyReason = applyReason;
	}

	public String getApplyFrom() {
		return applyFrom;
	}

	public void setApplyFrom(String applyFrom) {
		this.applyFrom = applyFrom;
	}

	public Long getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Long orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Long getWorkorderKy() {
		return workorderKy;
	}

	public void setWorkorderKy(Long workorderKy) {
		this.workorderKy = workorderKy;
	}

	public Long getRefundMKy() {
		return refundMKy;
	}

	public void setRefundMKy(Long refundMKy) {
		this.refundMKy = refundMKy;
	}

	public String getWorkflowKy() {
		return workflowKy;
	}

	public void setWorkflowKy(String workflowKy) {
		this.workflowKy = workflowKy;
	}

	public String getBatchNum() {
		return batchNum;
	}

	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}

	public Long getBatchStatus() {
		return batchStatus;
	}

	public void setBatchStatus(Long batchStatus) {
		this.batchStatus = batchStatus;
	}

	public Long getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(Long refundStatus) {
		this.refundStatus = refundStatus;
	}
}
