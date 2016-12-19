package com.pay.base.common.helper.matrixcard;

import java.io.Serializable;
import java.util.Date;


/**
 * 口令卡验证请求对象
 * @author jim_chen
 * @version 
 * 2010-9-15 
 */
public class MatrixCardVfyRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -336242152114508564L;

	public Long requestId;

	public Long transId;

	public int requestType;

	public int vfyType;
	
	//public String serialNo;

	public String requestIp;

	public Date requestDate;

	public Long requestMemberCode;

	public Long requestUid;

	public Long getRequestId() {
		return requestId;
	}

	public void setRequestId(Long requestId) {
		this.requestId = requestId;
	}

	public int getRequestType() {
		return requestType;
	}

	public void setRequestType(int requestType) {
		this.requestType = requestType;
	}

//	public String getSerialNo() {
//		return serialNo;
//	}
//
//	public void setSerialNo(String serialNo) {
//		this.serialNo = serialNo;
//	}

	public String getRequestIp() {
		return requestIp;
	}

	public void setRequestIp(String requestIp) {
		this.requestIp = requestIp;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public Long getRequestMemberCode() {
		return requestMemberCode;
	}

	public void setRequestMemberCode(Long requestMemberCode) {
		this.requestMemberCode = requestMemberCode;
	}

	public Long getRequestUid() {
		return requestUid;
	}

	public void setRequestUid(Long requestUid) {
		this.requestUid = requestUid;
	}

	/**
	 * @return the transId
	 */
	public Long getTransId() {
		return transId;
	}

	/**
	 * @param transId
	 *            the transId to set
	 */
	public void setTransId(Long transId) {
		this.transId = transId;
	}

	/**
	 * @return the vfyType
	 */
	public int getVfyType() {
		return vfyType;
	}

	/**
	 * @param vfyType the vfyType to set
	 */
	public void setVfyType(int vfyType) {
		this.vfyType = vfyType;
	}

}
