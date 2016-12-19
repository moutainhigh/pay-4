package com.pay.app.model;

import java.util.Date;

/**
 * 会员订阅
 * 
 * @author jim_chen
 * @version 2010-10-8
 */
public class SubscribeNotify implements Model{
	
	private Long id; // 订阅主键
	private Long memberCode;// 订阅会员
	private Long type;// 订阅类型
	private String context;// 订阅内容
	private Long noticeMode;// 通知方式
	private Long status;// 状态：0未通知 1已通知
	private Date creationDate;// 通知时间
	private Date updationDate;//更新时间

	public Date getUpdationDate() {
    	return updationDate;
    }

	public void setUpdationDate(Date updationDate) {
    	this.updationDate = updationDate;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public Long getNoticeMode() {
		return noticeMode;
	}

	public void setNoticeMode(Long noticeMode) {
		this.noticeMode = noticeMode;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Override
    public void setPrimaryKey(Long id) {
	   setId(id);	    
    }
}
