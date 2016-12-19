 /** @Description 
 * @project 	order-center-manager
 * @file 		OrderCenterEntrieDTO.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-11-9		Henry.Zeng			Create 
*/
package com.pay.poss.service.ordercenter.dto.entrie;

import java.math.BigDecimal;
import java.util.Date;

import com.pay.inf.model.BaseObject;

/**
 * <p></p>
 * @author Henry.Zeng
 * @since 2010-11-9
 * @see 
 */
public class OrderCenterEntrieDTO extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 凭证号码
	 */
	private String certificateNo;
	/**
	 * 账号
	 */
	private String accoutCode;
	/**
	 * 借贷标志
	 */
	private String drMark;
	
	/**
	 * 金额
	 */
	private BigDecimal amount;
	
	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 状态
	 * 1-已记账，0-未记账
	 */
	private String status;
	/**
	 * 分录号
	 */
	private String entrieNo;
	
	public String getCertificateNo() {
		return certificateNo;
	}
	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}
	public String getAccoutCode() {
		return accoutCode;
	}
	public void setAccoutCode(String accoutCode) {
		this.accoutCode = accoutCode;
	}
	public String getDrMark() {
		return drMark;
	}
	public void setDrMark(String drMark) {
		this.drMark = drMark;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getEntrieNo() {
		return entrieNo;
	}
	public void setEntrieNo(String entrieNo) {
		this.entrieNo = entrieNo;
	}

}
