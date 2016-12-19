package com.pay.app.dto;

import java.util.Date;

import com.pay.inf.dto.MutableDto;

@SuppressWarnings("serial")
public class SubscribeTypeDto extends BaseDTO implements MutableDto {
	
	private Long notifyId;// 订阅ID
	private String name;// 订阅类型名称
	private String description;// 订阅类型描述
	private Date createDate;// 创建时间
	private Date updateDate;// 更新时间

	private Long noticeMode;// 通知类型

	public Long getNoticeMode() {
		return noticeMode;
	}

	public void setNoticeMode(Long noticeMode) {
		this.noticeMode = noticeMode;
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

	public Long getNotifyId() {
		return notifyId;
	}

	public void setNotifyId(Long notifyId) {
		this.notifyId = notifyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
