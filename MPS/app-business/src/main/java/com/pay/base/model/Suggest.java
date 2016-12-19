package com.pay.base.model;

import java.util.Date;

/**
 * @author Jeffrey_teng
 * @version
 * @data 2010-11-10
 */

public class Suggest {
    private Long suggestId;
    private String title;
    private String content;
    private String email;
    private String mobile;
    private Date createAt;
    private Date dealAt;
    private Integer suggestType;
    private Integer dealStatus;
    private String dealDesc;
	public Long getSuggestId() {
		return suggestId;
	}
	public void setSuggestId(Long suggestId) {
		this.suggestId = suggestId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Date getCreateAt() {
		return createAt;
	}
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	public Date getDealAt() {
		return dealAt;
	}
	public void setDealAt(Date dealAt) {
		this.dealAt = dealAt;
	}
	public Integer getSuggestType() {
		return suggestType;
	}
	public void setSuggestType(Integer suggestType) {
		this.suggestType = suggestType;
	}
	public Integer getDealStatus() {
		return dealStatus;
	}
	public void setDealStatus(Integer dealStatus) {
		this.dealStatus = dealStatus;
	}
	public String getDealDesc() {
		return dealDesc;
	}
	public void setDealDesc(String dealDesc) {
		this.dealDesc = dealDesc;
	}
    
    
}