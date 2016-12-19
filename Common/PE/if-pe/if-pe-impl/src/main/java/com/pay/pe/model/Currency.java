package com.pay.pe.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.pay.inf.model.Model;



public class Currency implements Model{
	private Long sequenceId;
	private String currencyNum;
	private String currencyCode;
	private String currencyName;
	private String flag;
	private String status;
	private Date createDate;
		
	private static List <String> pk = new ArrayList <String> ();
	static {
		pk.add("sequenceId");
	}

	
	public Object getPrimaryKey() {
		Object[] obj = new Object[]{sequenceId};
		return obj;
	}

	
	public List getPrimaryKeyFields() {
		return pk;
	}

	
	public void setPrimaryKey(Object key) {
		if (null != key) {
			Object[] obj = (Object[])key;
			setSequenceId((Long)obj[0]);
		}
		
	}

	public Long getSequenceId() {
		return sequenceId;
	}

	public void setSequenceId(Long sequenceId) {
		this.sequenceId = sequenceId;
	}

	public String getCurrencyNum() {
		return currencyNum;
	}

	public void setCurrencyNum(String currencyNum) {
		this.currencyNum = currencyNum;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}


	public Date getCreateDate() {
		return createDate;
	}


	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}


	public String getFlag() {
		return flag;
	}


	public void setFlag(String flag) {
		this.flag = flag;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	@Override
	public String toString() {
		return "Currency [sequenceId=" + sequenceId + ", currencyNum="
				+ currencyNum + ", currencyCode=" + currencyCode
				+ ", currencyName=" + currencyName + ", flag=" + flag
				+ ", status=" + status + ", createDate=" + createDate + "]";
	}
}
