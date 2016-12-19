/**
 *  File: AutoFundoutCommon.java
 *  Description:
 *  Copyright 2010 -2010 Corporation. All rights reserved.
 *  2010-12-11     darv      Changes
 *  
 *
 */
package com.pay.app.controller.fo.autofundout;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;

/**
 * @author darv
 * 
 */
public class AutoFundoutCommon implements Serializable {
	private static final long serialVersionUID = 4521549092733947284L;
	private Long sequenceid;
	private Long retainedAmount;
	private String bankAccCode;
	private String remark;
	private Integer status;
	private Integer memberType;
	private String bankName;
	private java.util.Date updateDate;
	private String bankCode;
	private String createUser;
	private Integer busiType;
	private Integer autoType;
	private Long memberCode;
	private String updateUser;
	private Date createDate;
	private Integer timeType;
	private Integer settleFlag;
	private String timeSource;
	private String[] timeSources;
	private Long baseAmount;
	private Long configId;
	private Long typeId;
	private String[] weeks = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
	private DecimalFormat format = new DecimalFormat("0.00");
	
	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public Long getSequenceid() {
		return sequenceid;
	}

	public void setSequenceid(Long sequenceid) {
		this.sequenceid = sequenceid;
	}

	public Long getRetainedAmount() {
		return retainedAmount;
	}

	public void setRetainedAmount(Long retainedAmount) {
		this.retainedAmount = retainedAmount;
	}

	public String getBankAccCode() {
		return bankAccCode;
	}

	public void setBankAccCode(String bankAccCode) {
		this.bankAccCode = bankAccCode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getMemberType() {
		return memberType;
	}

	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public java.util.Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(java.util.Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Integer getBusiType() {
		return busiType;
	}

	public void setBusiType(Integer busiType) {
		this.busiType = busiType;
	}

	public Integer getAutoType() {
		return autoType;
	}

	public void setAutoType(Integer autoType) {
		this.autoType = autoType;
	}

	public Long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getTimeType() {
		return timeType;
	}

	public void setTimeType(Integer timeType) {
		this.timeType = timeType;
	}

	public String getTimeSource() {
		return timeSource;
	}

	public void setTimeSource(String timeSource) {
		this.timeSource = timeSource;
	}

	public Long getBaseAmount() {
		return baseAmount;
	}

	public void setBaseAmount(Long baseAmount) {
		this.baseAmount = baseAmount;
	}

	public Long getConfigId() {
		return configId;
	}

	public void setConfigId(Long configId) {
		this.configId = configId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getTimeDesc() {
		StringBuffer buff = new StringBuffer();
		if(autoType != null && timeType != null ){
			if (1 == autoType && 2 == timeType) {
				for (int i = 0; timeSource != null && i < timeSource.length(); i++) {
					if (timeSource.charAt(i) == '1') {
						buff.append(weeks[i]);
						buff.append("&nbsp;&nbsp;&nbsp;");
					}
				}
			} else {
				if (1 == timeType) {
					buff.append(timeSource);
					buff.append("天");
				} else if (3 == timeType) {
					buff.append("月");
					buff.append(timeSource);
					buff.append("号");
				}
			}
		}
		return buff.toString();
	}

	public String getRetainedAmountDesc() {
		if (retainedAmount == null) {
			return "0.00";
		} else {
			return format.format(retainedAmount / 1000.0);
		}
	}

	public String getBaseAmountDesc() {
		if (baseAmount == null) {
			return "0.00";
		} else {
			return format.format(baseAmount / 1000.0);
		}
	}

	public Integer getSettleFlag() {
		return settleFlag;
	}

	public void setSettleFlag(Integer settleFlag) {
		this.settleFlag = settleFlag;
	}

	public String[] getTimeSources() {
		return timeSources;
	}

	public void setTimeSources(String[] timeSources) {
		this.timeSources = timeSources;
	}
	
	
	
	
}
