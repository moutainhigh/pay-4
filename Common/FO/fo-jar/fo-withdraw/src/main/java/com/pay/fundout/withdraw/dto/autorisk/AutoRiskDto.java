package com.pay.fundout.withdraw.dto.autorisk;

import java.util.Date;

public class AutoRiskDto {

	/*
	 * 记录号
	 */
	private Long recordNo;
	
	/*
	 * 出款订单号
	 */
	private Long orderNo;
	
	/*
	 * 会员号
	 */
	private Long memberCode;
	
	/*
	 * 会员类型，1表示个人，2表示商户
	 */
	private Integer memberType;
	
	/*
	 * 会员名称 
	 */
	private String memberName;
	
	/*
	 * 规则号
	 */
	private String ruleCode;
	
	/*
	 * 状态，0表示待审核，1表示审核通过
	 */
	private String status;
	
	/*
	 * 创建日期
	 */
	private Date createDate;
	
	/*
	 * 修改日期
	 */
	private Date updateDate;
	
	/*
	 * 备注
	 */
	private String remark;
	
	/*
	 * 审核备注
	 */
	private String checkRemark;
	
	/*
	 * 操作员
	 */
	private String operator;
	
	/*
	 * 创建时间起始查询时间段
	 */
	private String beginCreateDate;
	
	/*
	 * 创建时间结束查询时间段
	 */
	private String endCreateDate;
	
	/*
	 * 审核时间起始查询时间段
	 */
	private String beginUpdateDate;
	
	/*
	 * 审核时间结束查询时间段
	 */
	private String endUpdateDate;

	public Long getRecordNo() {
		return recordNo;
	}

	public void setRecordNo(Long recordNo) {
		this.recordNo = recordNo;
	}

	public Long getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}

	public Long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}

	public Integer getMemberType() {
		return memberType;
	}

	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getRuleCode() {
		return ruleCode;
	}

	public void setRuleCode(String ruleCode) {
		this.ruleCode = ruleCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCheckRemark() {
		return checkRemark;
	}

	public void setCheckRemark(String checkRemark) {
		this.checkRemark = checkRemark;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getBeginCreateDate() {
		return beginCreateDate;
	}

	public void setBeginCreateDate(String beginCreateDate) {
		this.beginCreateDate = beginCreateDate;
	}

	public String getEndCreateDate() {
		return endCreateDate;
	}

	public void setEndCreateDate(String endCreateDate) {
		this.endCreateDate = endCreateDate;
	}

	public String getBeginUpdateDate() {
		return beginUpdateDate;
	}

	public void setBeginUpdateDate(String beginUpdateDate) {
		this.beginUpdateDate = beginUpdateDate;
	}

	public String getEndUpdateDate() {
		return endUpdateDate;
	}

	public void setEndUpdateDate(String endUpdateDate) {
		this.endUpdateDate = endUpdateDate;
	}
	
}
