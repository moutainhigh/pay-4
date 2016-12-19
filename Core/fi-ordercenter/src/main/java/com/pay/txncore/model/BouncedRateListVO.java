package com.pay.txncore.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 拒付欺诈报表数据
 *  File: BouncedRateListVO.java
 *  Description:
 *  Copyright 2016-2030 IPAYLINKS Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2016年7月28日   mmzhang     Create
 *
 */
public class BouncedRateListVO implements Serializable {

	/**
	 * Date      Author      Changes
	 * 2016年7月28日   mmzhang     Create
	 */
	private static final long serialVersionUID = -8334622188274958429L;


private Long id;    			        // '主键'
private String partnerid;    		  	// '会员号'
private String partnername;    	  		// '会员名称'
private String cardorg;    			  // '卡类型'
private Date createdate;    			// '生成日期'
private String bounceddate;    			// '拒付日期'
private BigDecimal bouncedcount;    			// '拒付笔数'
private BigDecimal totalcount;    			// '总笔数'
private BigDecimal lasttotalcount;     			// '上月总笔数'
private BigDecimal bouncedrate;    	  		// '拒付率'
private BigDecimal fraudamount;    	  		// '欺诈金额'
private BigDecimal totalamount;    	  		// '总金额'
private BigDecimal lasttotalamount;     			// '上月总金额'
private BigDecimal fraudrate;    		  	// '欺诈率'


public String getPartnerid() {
	return partnerid;
}
public void setPartnerid(String partnerid) {
	this.partnerid = partnerid;
}
public String getPartnername() {
	return partnername;
}
public void setPartnername(String partnername) {
	this.partnername = partnername;
}
public String getCardorg() {
	return cardorg;
}
public void setCardorg(String cardorg) {
	this.cardorg = cardorg;
}
public Date getCreatedate() {
	return createdate;
}
public void setCreatedate(Date createdate) {
	this.createdate = createdate;
}
public BigDecimal getBouncedcount() {
	return bouncedcount;
}
public void setBouncedcount(BigDecimal bouncedcount) {
	this.bouncedcount = bouncedcount;
}
public BigDecimal getTotalcount() {
	return totalcount;
}
public void setTotalcount(BigDecimal totalcount) {
	this.totalcount = totalcount;
}
public BigDecimal getLasttotalcount() {
	return lasttotalcount;
}
public void setLasttotalcount(BigDecimal lasttotalcount) {
	this.lasttotalcount = lasttotalcount;
}
public BigDecimal getBouncedrate() {
	return bouncedrate;
}
public void setBouncedrate(BigDecimal bouncedrate) {
	this.bouncedrate = bouncedrate;
}
public BigDecimal getFraudamount() {
	return fraudamount;
}
public void setFraudamount(BigDecimal fraudamount) {
	this.fraudamount = fraudamount;
}
public BigDecimal getTotalamount() {
	return totalamount;
}
public void setTotalamount(BigDecimal totalamount) {
	this.totalamount = totalamount;
}
public BigDecimal getLasttotalamount() {
	return lasttotalamount;
}
public void setLasttotalamount(BigDecimal lasttotalamount) {
	this.lasttotalamount = lasttotalamount;
}
public BigDecimal getFraudrate() {
	return fraudrate;
}
public void setFraudrate(BigDecimal fraudrate) {
	this.fraudrate = fraudrate;
}
public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
public String getBounceddate() {
	return bounceddate;
}
public void setBounceddate(String bounceddate) {
	this.bounceddate = bounceddate;
}

}