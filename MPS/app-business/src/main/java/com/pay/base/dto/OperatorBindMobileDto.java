/**
 * 
 */
package com.pay.base.dto;

/**
 * desc:操作员绑定手机 号
 * @author DaiDeRong
 * 2011-11-21 下午4:28:24
 */
public class OperatorBindMobileDto {

	private String operatorId;
	private String mobile;
	private String mobileConfrim;
	private String oldMobile;
	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * @return the mobileConfrim
	 */
	public String getMobileConfrim() {
		return mobileConfrim;
	}
	/**
	 * @param mobileConfrim the mobileConfrim to set
	 */
	public void setMobileConfrim(String mobileConfrim) {
		this.mobileConfrim = mobileConfrim;
	}
	/**
	 * @return the oldMobile
	 */
	public String getOldMobile() {
		return oldMobile;
	}
	/**
	 * @param oldMobile the oldMobile to set
	 */
	public void setOldMobile(String oldMobile) {
		this.oldMobile = oldMobile;
	}
	/**
	 * @return the operatorId
	 */
	public String getOperatorId() {
		return operatorId;
	}
	/**
	 * @param operatorId the operatorId to set
	 */
	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}
	
	
	
	
	
}
