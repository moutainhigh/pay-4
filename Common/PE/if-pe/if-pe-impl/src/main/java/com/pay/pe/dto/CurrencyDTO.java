package com.pay.pe.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.pay.inf.dto.MutableDto;

/**
 * 货币
 * @author peiyu.yang
 *
 */
public class CurrencyDTO implements MutableDto{
	private static final long serialVersionUID = -6044722378167939318L;
	private Long sequenceId;
	private String currencyNum;
	private String currencyCode;
	private String currencyName;
	private String flag;//1：支付交易  2：表示兑换  3：其他
	private String status;
	private Date createDate;
	
	private static List<String> pk = new ArrayList<String>();
	static {
		pk.add("sequenceId");
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
	
	public Object getPrimaryKey() {
		Object[] result = new Object[] { sequenceId };
		return result;
	}
	
	public void setPrimaryKey(Object key) {
		if (null != key) {
			Object[] obj = (Object[]) key;
			setSequenceId((Long) obj[0]);
		}
	}
	
	public List getPrimaryKeyFields() {
		return pk;
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
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	@Override
	public String toString() {
		return "CurrencyDTO [sequenceId=" + sequenceId + ", currencyNum="
				+ currencyNum + ", currencyCode=" + currencyCode
				+ ", currencyName=" + currencyName + ", flag=" + flag
				+ ", status=" + status + ", createDate=" + createDate + "]";
	}
}
