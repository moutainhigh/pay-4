package com.pay.rm.blacklistcheck.dto;

import java.util.Date;

public class BlackChecklistDTO {
	private Long id;
	private String ids;
	private String memberCode;
	private Integer businessTypeId; // 本条记录由哪种类型引起 IP/EMAIL/SHIP/BILL' (1 cardno 2 EMAIL 3 IP  8 SHIP 9 BILL )
	
	private Integer status; // 0- 创建 1 - 提交初审 2 - 删除/已处理
	private Date createDate; // 创建时间
	private Date updateDate;//修改时间

	
	private String content;// 内容
	private String tradeOrderNos;// 所有网关订单号
	public String getTradeOrderNos() {
		return tradeOrderNos;
	}

	public void setTradeOrderNos(String tradeOrderNos) {
		this.tradeOrderNos = tradeOrderNos;
	}

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


	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
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


	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	

}
