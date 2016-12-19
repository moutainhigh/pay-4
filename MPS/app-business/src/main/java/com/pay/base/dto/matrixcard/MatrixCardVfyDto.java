package com.pay.base.dto.matrixcard;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.pay.inf.dto.MutableDto;

/**
 * @author jim_chen
 * @version 
 * 2010-9-15
 */
public class MatrixCardVfyDto implements MutableDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3471565994894529508L;

	public Long id;
	public Long transId;
	public Long requestId;
	public int requestType;
	public String serialNo;
	public String requestIp;
	public Date requestDate;
	public Long requestMemberCode;
	public Long requestUid;
	public String token;
	public String xy0;
	public String value0;
	public String xy1;
	public String value1;
	public String xy2;
	public String value2;
	public Long vfyMemberCode;
	public Long vfyUid;
	public Date vfyDate;
	public String vfyIp;
	public Date retryTimes;
	public int status;
	public int vfyType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTransId() {
		return transId;
	}

	public void setTransId(Long transId) {
		this.transId = transId;
	}

	public Long getRequestId() {
		return requestId;
	}

	public void setRequestId(Long requestId) {
		this.requestId = requestId;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getXy0() {
		return xy0;
	}

	public void setXy0(String xy0) {
		this.xy0 = xy0;
	}

	public String getValue0() {
		return value0;
	}

	public void setValue0(String value0) {
		this.value0 = value0;
	}

	public String getXy1() {
		return xy1;
	}

	public void setXy1(String xy1) {
		this.xy1 = xy1;
	}

	public String getValue1() {
		return value1;
	}

	public void setValue1(String value1) {
		this.value1 = value1;
	}

	public String getXy2() {
		return xy2;
	}

	public void setXy2(String xy2) {
		this.xy2 = xy2;
	}

	public String getValue2() {
		return value2;
	}

	public void setValue2(String value2) {
		this.value2 = value2;
	}

	public Long getVfyMemberCode() {
		return vfyMemberCode;
	}

	public void setVfyMemberCode(Long vfyMemberCode) {
		this.vfyMemberCode = vfyMemberCode;
	}

	public Long getVfyUid() {
		return vfyUid;
	}

	public void setVfyUid(Long vfyUid) {
		this.vfyUid = vfyUid;
	}

	public Date getVfyDate() {
		return vfyDate;
	}

	public void setVfyDate(Date vfyDate) {
		this.vfyDate = vfyDate;
	}

	public String getVfyIp() {
		return vfyIp;
	}

	public void setVfyIp(String vfyIp) {
		this.vfyIp = vfyIp;
	}

	public Date getRetryTimes() {
		return retryTimes;
	}

	public void setRetryTimes(Date retryTimes) {
		this.retryTimes = retryTimes;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @return the requestType
	 */
	public int getRequestType() {
		return requestType;
	}

	/**
	 * @param requestType
	 *            the requestType to set
	 */
	public void setRequestType(int requestType) {
		this.requestType = requestType;
	}

	/**
	 * @return the vfyType
	 */
	public int getVfyType() {
		return vfyType;
	}

	/**
	 * @param vfyType
	 *            the vfyType to set
	 */
	public void setVfyType(int vfyType) {
		this.vfyType = vfyType;
	}

	private static List<String> pk = new ArrayList<String>();
	static {
		pk.add("id");
	}

	public Object getPrimaryKey() {
		Object[] result = new Object[] { this.getId() };
		return result;
	}

	public List<String> getPrimaryKeyFields() {
		return pk;
	}

	public void setPrimaryKey(Object key) {
		if (null != key) {
			Object[] obj = (Object[]) key;
			this.setId((Long) obj[0]);
		}

	}

}
