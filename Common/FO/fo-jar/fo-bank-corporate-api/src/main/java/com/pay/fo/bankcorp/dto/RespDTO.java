/**
 * 
 */
package com.pay.fo.bankcorp.dto;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author new
 *
 */
public class RespDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2999607166486918100L;
	
	/**
	 * 交易代码
	 */
	private String transCode;

	/**
	 * 返回代码
	 */
	private String rtnCode;
	/**
	 * 返回代码描述
	 */
	private String rtnInfo;

	public String getRtnCode() {
		return rtnCode;
	}

	public void setRtnCode(String rtnCode) {
		this.rtnCode = rtnCode;
	}

	public String getRtnInfo() {
		return rtnInfo;
	}

	public void setRtnInfo(String rtnInfo) {
		this.rtnInfo = rtnInfo;
	}

	public String getTransCode() {
		return transCode;
	}

	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public boolean equals(final Object other) {
		return EqualsBuilder.reflectionEquals(this, other);
	}

	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
	

}
