package com.pay.base.model.matrixcard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.pay.app.model.Model;


/**
 * @author jim_chen
 * @version 
 * 2010-9-15 
 */
public class MatrixCard implements Model, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7527324908711227886L;
	
	private Long id;
	private String serialNo;
	private String matrixData;
	private Date creationDate;
	private Date bindDate;
	private Long bindUid;
	private Long bindMemberCode;
	private String bindIp;
	private Date unBindDate;
	private String unBindIp;
	private String unBindOperator;
	private int status;
	private int userTime;//使用次数
	
	public int getUserTime() {
    	return userTime;
    }

	public void setUserTime(int userTime) {
    	this.userTime = userTime;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getMatrixData() {
		return matrixData;
	}

	public void setMatrixData(String matrixData) {
		this.matrixData = matrixData;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getBindDate() {
		return bindDate;
	}

	public void setBindDate(Date bindDate) {
		this.bindDate = bindDate;
	}

	public Long getBindUid() {
		return bindUid;
	}

	public void setBindUid(Long bindUid) {
		this.bindUid = bindUid;
	}

	public Long getBindMemberCode() {
		return bindMemberCode;
	}

	public void setBindMemberCode(Long bindMemberCode) {
		this.bindMemberCode = bindMemberCode;
	}

	public String getBindIp() {
		return bindIp;
	}

	public void setBindIp(String bindIp) {
		this.bindIp = bindIp;
	}

	public Date getUnBindDate() {
		return unBindDate;
	}

	public void setUnBindDate(Date unBindDate) {
		this.unBindDate = unBindDate;
	}

	public String getUnBindIp() {
		return unBindIp;
	}
	
	public String getUnBindOperator() {
		return unBindOperator;
	}

	public void setUnBindOperator(String unBindOperator) {
		this.unBindOperator = unBindOperator;
	}

	public void setUnBindIp(String unBindIp) {
		this.unBindIp = unBindIp;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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
