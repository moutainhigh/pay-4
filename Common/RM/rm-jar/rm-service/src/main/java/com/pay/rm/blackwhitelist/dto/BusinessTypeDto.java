package com.pay.rm.blackwhitelist.dto;

import java.util.Date;

import com.pay.inf.dto.MutableDto;

@SuppressWarnings("serial")
public class BusinessTypeDto implements MutableDto {
	private Integer businessTypeId; // 业务类型id (100 FO 200 FI 300 MA 400 APP)
	private String businessName; // 业务描述
	private Integer status; // 有效性标志1有效0无效
	private Integer businessType; // 业务类型：1, FO 2, FI 3,MA 4,APP
	private Date createTime;
	private Date updateTime;
	private String remark;
	private String value1;
	private String value2;

	private Integer pageStartRow;// 起始行
	private Integer pageEndRow;// 结束行

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

	public Integer getBusinessTypeId() {
		return businessTypeId;
	}

	public void setBusinessTypeId(Integer businessTypeId) {
		this.businessTypeId = businessTypeId;
	}

	public String getBusinessName() {
		if (null != businessName && !"".equals(businessName)) {
			String str = businessName.trim();
			return str;
		} else {
			return businessName;
		}
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getBusinessType() {
		return businessType;
	}

	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

}
