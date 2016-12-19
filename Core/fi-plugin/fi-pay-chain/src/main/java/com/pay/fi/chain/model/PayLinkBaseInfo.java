/**
 * 
 */
package com.pay.fi.chain.model;

import java.sql.Timestamp;

/**
 * 支付链基本信息[售后联系方式｜购物条款]
 * @author PengJiangbo
 *
 */
public class PayLinkBaseInfo {

	private Long infoId ;
	private Long memberCode ;
	private String shoptermsName ;
	private String shoptermsUrl ;
	private String contact ;
	
	private Timestamp createTime ;
	private Timestamp updateTime ;
	
	public Long getInfoId() {
		return infoId;
	}
	public void setInfoId(Long infoId) {
		this.infoId = infoId;
	}
	public Long getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}
	public String getShoptermsName() {
		return shoptermsName;
	}
	public void setShoptermsName(String shoptermsName) {
		this.shoptermsName = shoptermsName;
	}
	public String getShoptermsUrl() {
		return shoptermsUrl;
	}
	public void setShoptermsUrl(String shoptermsUrl) {
		this.shoptermsUrl = shoptermsUrl;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	@Override
	public String toString() {
		return "PayLinkBaseInfo [infoId=" + infoId + ", memberCode="
				+ memberCode + ", shoptermsName=" + shoptermsName
				+ ", shoptermsUrl=" + shoptermsUrl + ", contact=" + contact
				+ ", createTime=" + createTime + ", updateTime=" + updateTime
				+ "]";
	}
	
}
