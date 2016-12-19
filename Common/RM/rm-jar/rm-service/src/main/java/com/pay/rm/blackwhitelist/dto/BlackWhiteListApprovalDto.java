package com.pay.rm.blackwhitelist.dto;

import java.util.Date;

import com.pay.inf.dto.MutableDto;

/**
 * 黑白名单审批
 * @author peiyu.yang
 * @since 2016年6月13日21:11:19
 */
@SuppressWarnings("serial")
public class BlackWhiteListApprovalDto implements MutableDto {
	
	private Long id;
	private String memberCode;
	private Integer businessTypeId; // 业务类型id (100 FO 200 FI 300 MA 400 APP)
	private Integer listType; // 名单类型1白名单2黑名单
	private Integer status; // 标志, -1:初审拒绝,0:待初审，1:待复审，2：复审核通过,
	private Date createDate; // 创建时间
	private Date updateDate;
	private Integer approvalType;//审批类型 1:新增审批，2：删除审批、3：修改审批

	private Integer partType;//匹配类型 ，1：全匹配，2：部分匹配 3：正则表达式，4：范围匹配
	private String content;//内容
	private String approvalUser;//审核人
	private Integer pageStartRow;// 起始行
	private Integer pageEndRow;// 结束行

	private String value1;
	private String value2;
	private String remark;
	private String operator;
	private Long blackWhiteListId;//黑白名单ID
	
	public Long getBlackWhiteListId() {
		return blackWhiteListId;
	}

	public void setBlackWhiteListId(Long blackWhiteListId) {
		this.blackWhiteListId = blackWhiteListId;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
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
		return "BlackWhiteListApprovalDto [id=" + id + ", memberCode="
				+ memberCode + ", businessTypeId=" + businessTypeId
				+ ", listType=" + listType + ", status=" + status
				+ ", createDate=" + createDate + ", updateDate=" + updateDate
				+ ", approvalType=" + approvalType + ", partType=" + partType
				+ ", content=" + content + ", approvalUser=" + approvalUser
				+ ", pageStartRow=" + pageStartRow + ", pageEndRow="
				+ pageEndRow + ", value1=" + value1 + ", value2=" + value2
				+ ", remark=" + remark + ", operator=" + operator
				+ ", blackWhiteListId=" + blackWhiteListId + "]";
	}
}
