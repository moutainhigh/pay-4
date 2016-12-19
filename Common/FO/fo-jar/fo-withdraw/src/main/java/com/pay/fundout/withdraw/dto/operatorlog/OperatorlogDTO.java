/**
 *  File: OperatorlogDTO.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-14      zliner      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.dto.operatorlog;

import java.io.Serializable;
import java.util.Date;

/**
 * 操作员日志对象
 * @author zliner
 *
 */
public class OperatorlogDTO implements Serializable {
	 	//序号
		private static final long serialVersionUID = 2513208914366047774L;
		private Long sequenceid;
	 	//创建时间
	    private Date creationDate;
	    //日志类型描述
	    private String logTypeDesc;
	    //备注，可以把后台操作员的操作内容写在里面
	    private String mark;
	    //日志类型
	    private Integer logType;
	    //操作员标识，可从session或登录信息中取
	    private String operator;
	    //操作业务对应的业务订单号
	    private String busiOrderId;
		public Long getSequenceid() {
			return sequenceid;
		}
		public void setSequenceid(Long sequenceid) {
			this.sequenceid = sequenceid;
		}
		public Date getCreationDate() {
			return creationDate;
		}
		public void setCreationDate(Date creationDate) {
			this.creationDate = creationDate;
		}
		public String getLogTypeDesc() {
			return logTypeDesc;
		}
		public void setLogTypeDesc(String logTypeDesc) {
			this.logTypeDesc = logTypeDesc;
		}
		public String getMark() {
			return mark;
		}
		public void setMark(String mark) {
			this.mark = mark;
		}
		public Integer getLogType() {
			return logType;
		}
		public void setLogType(Integer logType) {
			this.logType = logType;
		}
		public String getOperator() {
			return operator;
		}
		public void setOperator(String operator) {
			this.operator = operator;
		}
		public String getBusiOrderId() {
			return busiOrderId;
		}
		public void setBusiOrderId(String busiOrderId) {
			this.busiOrderId = busiOrderId;
		}
}
