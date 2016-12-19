/**
 * 
 */
package com.pay.acc.comm;


/**
 * desc:
 * @author DaiDeRong
 * 2011-11-22 下午5:08:05
 */
public enum BindMobleSmsType {
	
	
	BIND_SMS(Resource.APP_BIND_MOBILE_SMS,CheckCodeOriginEnum.BIND_MOBILE_SMS),
	MODIFY_BIND_SMS(Resource.MODIFY_BIND_MOBILE_SMS,CheckCodeOriginEnum.MODIFY_MOBILE_SMS),
	UNBIND_SMS(Resource.UNBIND_MOBILE_SMS,CheckCodeOriginEnum.UNBIND_MOBILE_SMS),
	BACKUP_CERT(Resource.BACKUP_CERT_SMS,CheckCodeOriginEnum.BACKUP_CERT_SMS);
	
	private Long type;
	private CheckCodeOriginEnum checkCodeOriginEnum;
	


	/**
	 * @return the type
	 */
	public Long getType() {
		return type;
	}




	/**
	 * @param type the type to set
	 */
	public void setType(Long type) {
		this.type = type;
	}


	


	/**
	 * @return the checkCodeOriginEnum
	 */
	public CheckCodeOriginEnum getCheckCodeOriginEnum() {
		return checkCodeOriginEnum;
	}




	/**
	 * @param checkCodeOriginEnum the checkCodeOriginEnum to set
	 */
	public void setCheckCodeOriginEnum(CheckCodeOriginEnum checkCodeOriginEnum) {
		this.checkCodeOriginEnum = checkCodeOriginEnum;
	}




	private BindMobleSmsType(Long type,CheckCodeOriginEnum checkCodeOriginEnum) {
		this.type = type;
		this.checkCodeOriginEnum = checkCodeOriginEnum;
	}


	
}
