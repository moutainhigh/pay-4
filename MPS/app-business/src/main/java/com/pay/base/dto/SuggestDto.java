package com.pay.base.dto;

import java.util.Date;

/**
 * @author Jeffrey_teng
 * @version
 * @data 2010-11-10
 */

public class SuggestDto {
    private Long suggestId; //主键
    private String title;   //标题或钓鱼网站地址
    private String content; //内容
    private String email;   //邮箱地址
    private String mobile;  //手机号码
    private Date createAt;  //创建时间
    private Date dealAt;    //处理时间（预留）
    private Integer suggestType;  //类型 (1-投诉 2-建议 3-举报)
    private Integer dealStatus;   //处理状态（0-未处理 1-已处理）
    private String dealDesc;      //处理结果
    
    
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