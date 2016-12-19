/**
 * 
 */
package com.pay.fi.chain.util;

/**
 * @author PengJiangbo
 *
 */
public enum CommonErrorCodeEnum {

	//----------------------上传相关----------------------------------//
	UPLOAD_FAILE("U001","上传失败"),
	UPLOAD_TYPE_FAILE("U002","图片格式不正确"),
	UPLOAD_SIZE_FAILE("U003","图片大小不能超过"),
	UPLOAD_FILE_EMPTY("U004","请选择您要上传的图片"),
	UPLOAD_NUM_TO_MAX("U005","一次性只能上传四张图片"),
	UPLOAD_PIC_IS_NOT_EXSITS("U006","图片不存在"),
	UPLOAD_PIC_REMOVE_FAILE("U007","图片删除失败") ;
	
	private final String errorCode;
	private final String message;
	
	/**
	 * @param errorCode
	 * @param message
	 */
	private CommonErrorCodeEnum(String errorCode, String message) {
		this.errorCode = errorCode;
		this.message = message;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public String getMessage() {
		return message;
	}
	
	
	
}
