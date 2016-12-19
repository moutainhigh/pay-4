/**
 * 
 */
package com.pay.poss.paychainmanager.dto;

import java.util.Date;

import com.pay.poss.paychainmanager.enums.EffectiveTypeEnum;
import com.pay.poss.paychainmanager.util.EffectiveDateUtil;


/**
 * @Description 
 * @project 	ma-manager
 * @file 		PayChainManagerDto.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2011-9-20			tianqing_wang	Create
 */
public class PayChainManagerDto {
	
	private String  zhName;                      //商户名称
	private String  strCreateDate;				 //页面展示收款链接生成时间
	private String  strOverdueDate;				 //页面展示收款链接过期时间
	
	private Date  createDate;				 	//生成时间
	private Date  overdueDate;					 //过期时间
	
	private Long id;							 //id
	private String  payChainNumber;				 //收款链接编号
	private String  receiptDescription;			 //收款项目
	private Integer total; 						 //已经支付笔数
	private String  strTotalAmount;				 //页面展示已支付总金额
	
	private String 	strStatus;					 //页面展示收款链接状态
	private String  status;
	
	private Integer pageStartRow;// 起始行
	private Integer pageEndRow;// 结束行
	private String startDate;	//查询时间起始段
	private String endDate;		//查询时间结束段
	
	private String operate;    //操作状态
	
	private String tel;
	private String paychainUrl;
	private String address;//公司地址
	private Integer regions;
	private Integer city;
	private Integer zip;
	
	private Integer effectiveDate;
	private String 	paychainName;	//支付链名称
	
	public String getPaychainName() {
		return paychainName;
	}

	public void setPaychainName(String paychainName) {
		this.paychainName = paychainName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getRegions() {
		return regions;
	}

	public void setRegions(Integer regions) {
		this.regions = regions;
	}

	public Integer getCity() {
		return city;
	}

	public void setCity(Integer city) {
		this.city = city;
	}

	public Integer getZip() {
		return zip;
	}

	public void setZip(Integer zip) {
		this.zip = zip;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPaychainUrl() {
		return "https://www.pay.com/website/" + paychainUrl;
	}

	public void setPaychainUrl(String paychainUrl) {
		this.paychainUrl = paychainUrl;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getOperate() {
		return operate;
	}

	public void setOperate(String operate) {
		this.operate = operate;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Integer getPageStartRow() {
		return pageStartRow;
	}

	public void setPageStartRow(Integer pageStartRow) {
		this.pageStartRow = pageStartRow;
	}

	public Integer getPageEndRow() {
		return pageEndRow;
	}

	public void setPageEndRow(Integer pageEndRow) {
		this.pageEndRow = pageEndRow;
	}

	public String getStrStatus() {
		return strStatus;
	}

	public void setStrStatus(String strStatus) {
		this.strStatus = strStatus;
	}

	public String getZhName() {
		return zhName;
	}

	public void setZhName(String zhName) {
		this.zhName = zhName;
	}

	public String getStrCreateDate() {
		return strCreateDate;
	}

	public void setStrCreateDate(String strCreateDate) {
		this.strCreateDate = strCreateDate;
	}

	public String getPayChainNumber() {
		return payChainNumber;
	}

	public void setPayChainNumber(String payChainNumber) {
		this.payChainNumber = payChainNumber;
	}

	public String getReceiptDescription() {
		return receiptDescription;
	}

	public void setReceiptDescription(String receiptDescription) {
		this.receiptDescription = receiptDescription;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public String getStrTotalAmount() {
		return strTotalAmount;
	}

	public void setStrTotalAmount(String strTotalAmount) {
		this.strTotalAmount = strTotalAmount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getOverdueDate() {
		return overdueDate;
	}

	public void setOverdueDate(Date overdueDate) {
		this.overdueDate = overdueDate;
	}

	public String getStrOverdueDate() {
		return strOverdueDate;
	}

	public void setStrOverdueDate(String strOverdueDate) {
		this.strOverdueDate = strOverdueDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getEffectiveDate() {
		return effectiveDate;
	}

	public String getEffectiveDateName() {
		return EffectiveTypeEnum.getEffectiveEnum(effectiveDate).getMemo();
	}

	public void setEffectiveDate(Integer effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public boolean isExpired() {
		Date date = new Date();
		if (date.after(overdueDate)) {
			return true;
		}
		return false;
	}

	public int getDisplayEffectiveType() {
		int i = EffectiveTypeEnum.TEN_YEAR.getType() + 1;

		for (EffectiveTypeEnum typeEnum : EffectiveTypeEnum.values()) {

			if (!EffectiveDateUtil.isEffectiveTypeExpired(typeEnum, createDate)) {
				if (typeEnum.getType() < i ) {
					i = typeEnum.getType();
				}
			}
		}

		return i;

	}

}
