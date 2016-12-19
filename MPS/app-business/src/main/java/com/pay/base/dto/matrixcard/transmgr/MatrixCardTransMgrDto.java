package com.pay.base.dto.matrixcard.transmgr;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.pay.inf.dto.MutableDto;


/**
 * @author jim_chen
 * @version 
 * 2010-9-15 
 */
public class MatrixCardTransMgrDto implements MutableDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6271537961374511056L;
	
	private Long id;
	private int transType;
	private Long memberCode;
	private Long u_id;
	private Date creationDate;
	private String ip;
	private String sessionId;
	private int status;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getTransType() {
		return transType;
	}
	public void setTransType(int transType) {
		this.transType = transType;
	}
	public Long getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}
	public Long getU_id() {
		return u_id;
	}
	public void setU_id(Long u_id) {
		this.u_id = u_id;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getSessionId() {
		return sessionId;
	}
	
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
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
