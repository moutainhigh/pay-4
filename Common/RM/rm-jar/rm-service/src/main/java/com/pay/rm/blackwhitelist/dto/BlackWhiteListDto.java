package com.pay.rm.blackwhitelist.dto;

import java.util.Date;

import com.pay.inf.dto.MutableDto;

@SuppressWarnings("serial")
public class BlackWhiteListDto implements MutableDto {
	
	private Long id;
	private String memberCode;
	private Integer businessTypeId; // 业务类型id (100 FO 200 FI 300 MA 400 APP)
	private Integer listType; // 名单类型1白名单2黑名单
	private Integer status; // 标志,1有效,0无效
	private Date createDate; // 创建时间
	private Date updateDate;

	private Integer partType;//全匹配 1 
	private String content;// 修改时间

	private Integer pageStartRow;// 起始行
	private Integer pageEndRow;// 结束行
	private String value1;
	private String value2;
	private Long approvalId;//审批工单ID
	private String operator;
	private Integer approvalType; //新加 1 
	private String approvalUser;
	private String remark; // 风险订单识别

	public Integer getApprovalType() {
		return approvalType;
	}
	public void setApprovalType(Integer approvalType) {
		this.approvalType = approvalType;
	}
	public String getApprovalUser() {
		return approvalUser;
	}
	public void setApprovalUser(String approvalUser) {
		this.approvalUser = approvalUser;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public Long getApprovalId() {
		return approvalId;
	}
	public void setApprovalId(Long approvalId) {
		this.approvalId = approvalId;
	}
	public String getValue1() {
		return value1;
	}
	public void setValue1(String value1) {
		this.value1 = value1;
	}
	public String getValue2() {
		return value2;
	}
	public void setValue2(String value2) {
		this.value2 = value2;
	}
	public Integer getPageStartRow() {
		return pageStartRow;
	}
	public void setPageStartRow(Integer pageStartRow) {
		this.pageStartRow = pageStartRow;
	}
	public Integer getPageEndRow() {
		return pageEndRow;
	}
	public void setPageEndRow(Integer pageEndRow) {
		this.pageEndRow = pageEndRow;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMemberCode() {
		if (null != memberCode && !"".equals(memberCode)) {
			String str = memberCode.trim();
			return str;
		} else {
			return memberCode;
		}
	}
	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}
	public Integer getBusinessTypeId() {
		return businessTypeId;
	}
	public void setBusinessTypeId(Integer businessTypeId) {
		this.businessTypeId = businessTypeId;
	}
	public Integer getListType() {
		return listType;
	}
	public void setListType(Integer listType) {
		this.listType = listType;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
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
	public Integer getPartType() {
		return partType;
	}
	public void setPartType(Integer partType) {
		this.partType = partType;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "BlackWhiteListDto [id=" + id + ", memberCode=" + memberCode
				+ ", businessTypeId=" + businessTypeId + ", listType="
				+ listType + ", status=" + status + ", createDate="
				+ createDate + ", updateDate=" + updateDate + ", partType="
				+ partType + ", content=" + content + ", pageStartRow="
				+ pageStartRow + ", pageEndRow=" + pageEndRow + ", value1="
				+ value1 + ", value2=" + value2 + ", approvalId=" + approvalId
				+ ", operator=" + operator + ", approvalType=" + approvalType
				+ ", approvalUser=" + approvalUser + ", remark=" + remark + "]";
	}
}
