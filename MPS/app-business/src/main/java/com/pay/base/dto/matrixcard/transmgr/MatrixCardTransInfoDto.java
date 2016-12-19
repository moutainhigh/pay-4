package com.pay.base.dto.matrixcard.transmgr;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.pay.inf.dto.MutableDto;

/**
 * @author jim_chen
 * @version 2010-9-15
 */
public class MatrixCardTransInfoDto implements MutableDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8888813089600144738L;

	private Long id;
	private Long transId;
	private int transType;
	private String ip;
	private Long u_id;
	private Long memberCode;
	private String sessionId;
	private Long mcId;
	private String serialNo;
	private String validateCode;
	private int wrongTime;
	private Date lastValidateTime;
	private int valStatus;
	private Date creationDate;

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

	public int getTransType() {
		return transType;
	}

	public void setTransType(int transType) {
		this.transType = transType;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Long getU_id() {
		return u_id;
	}

	public void setU_id(Long uId) {
		u_id = uId;
	}

	public Long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Long getMcId() {
		return mcId;
	}

	public void setMcId(Long mcId) {
		this.mcId = mcId;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getValidateCode() {
		return validateCode;
	}

	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}

	public int getWrongTime() {
		return wrongTime;
	}

	public void setWrongTime(int wrongTime) {
		this.wrongTime = wrongTime;
	}

	public Date getLastValidateTime() {
		return lastValidateTime;
	}

	public void setLastValidateTime(Date lastValidateTime) {
		this.lastValidateTime = lastValidateTime;
	}

	public int getValStatus() {
		return valStatus;
	}

	public void setValStatus(int valStatus) {
		this.valStatus = valStatus;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
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

	public void setPrimaryKey(Long key) {
		if (null != key) {
			Long obj = key;
			this.setId(obj);
		}
	}
}
