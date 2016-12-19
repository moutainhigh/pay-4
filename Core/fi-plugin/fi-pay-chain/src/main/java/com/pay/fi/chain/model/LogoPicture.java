/**
 * 
 */
package com.pay.fi.chain.model;

import java.sql.Timestamp;

/**
 * @author PengJiangbo
 *
 */
public class LogoPicture {
	
	/** logo图片ID */
	private Long pictureId ;
	/** 商户号 */
	private Long memberCode ;
	/** 图片名称 */
	private String pictureName ;
	/** 图片url */
	private String picturePath ;
	/** 创建时间 */
	private Timestamp createTime ;
	/** 商户网址 */
	private String merchantSite ;
	
	public Long getPictureId() {
		return pictureId;
	}
	public void setPictureId(Long pictureId) {
		this.pictureId = pictureId;
	}
	public Long getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}
	public String getPictureName() {
		return pictureName;
	}
	public void setPictureName(String pictureName) {
		this.pictureName = pictureName;
	}
	
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getMerchantSite() {
		return merchantSite;
	}
	public void setMerchantSite(String merchantSite) {
		this.merchantSite = merchantSite;
	}
	public String getPicturePath() {
		return picturePath;
	}
	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}
	@Override
	public String toString() {
		return "LogoPicture [pictureId=" + pictureId + ", memberCode="
				+ memberCode + ", pictureName=" + pictureName
				+ ", picturePath=" + picturePath + ", createTime=" + createTime
				+ ", merchantSite=" + merchantSite + "]";
	}
	
}
