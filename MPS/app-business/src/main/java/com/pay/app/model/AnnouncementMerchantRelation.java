/**
 * 
 */
package com.pay.app.model;

import java.sql.Timestamp;

/**
 * @author PengJiangbo
 *
 */
public class AnnouncementMerchantRelation {

	private Long memberCode ;
	
	private Long announcementId ;
	
	private Timestamp createTime ;

	/**
	 * @return the memberCode
	 */
	public Long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}

	/**
	 * @return the announcementId
	 */
	public Long getAnnouncementId() {
		return announcementId;
	}

	public void setAnnouncementId(Long announcementId) {
		this.announcementId = announcementId;
	}

	/**
	 * @return the createTime
	 */
	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AnnouncementMerchantRelation [memberCode=" + memberCode
				+ ", announcementId=" + announcementId + ", createTime="
				+ createTime + "]";
	}
	
	
}
