package com.pay.rm.blackwhitelist.dto;

public class BlackWhiteListDtoDownload {

	private String businessTypeId; // 业务类型id (100 FO 200 FI 300 MA 400 APP)
	private String listType; // 名单类型1白名单2黑名单
	private String status; // 标志,1有效,0无效
	private String partType;
	private String content;// 修改时间
	public String getBusinessTypeId() {
		return businessTypeId;
	}
	public void setBusinessTypeId(String businessTypeId) {
		this.businessTypeId = businessTypeId;
	}
	public String getListType() {
		return listType;
	}
	public void setListType(String listType) {
		this.listType = listType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPartType() {
		return partType;
	}
	public void setPartType(String partType) {
		this.partType = partType;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
