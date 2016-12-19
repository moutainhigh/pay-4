/**
 * 
 */
package com.pay.app.dto;

/**
 * 商户注册的结果DTO
 * @author 戴德荣
 *
 */
public class ExternalRegisterResultDto {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String errorCode; 	//		注册失败错误代码
	private String errorMsg	;	//注册失败错误信息
	private Boolean resultFlag;	//	是否注册成功
	private String memberCode;	//		如果注册成功则返回会员号（商户唯一标识）
	private String signMsg	;	//	RSA加密摘要的密文;
	
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public Boolean getResultFlag() {
		return resultFlag;
	}
	public void setResultFlag(Boolean resultFlag) {
		this.resultFlag = resultFlag;
	}
	public String getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}
	/**
	 * @return the signMsg
	 */
	public String getSignMsg() {
		return signMsg;
	}
	/**
	 * @param signMsg the signMsg to set
	 */
	public void setSignMsg(String signMsg) {
		this.signMsg = signMsg;
	}

	
	
	
	

}
