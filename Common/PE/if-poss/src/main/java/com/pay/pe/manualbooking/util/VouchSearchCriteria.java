package com.pay.pe.manualbooking.util;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * 记帐申请查询条件类
 */
public class VouchSearchCriteria {
	/**
	 * 申请号
	 */
	private String vouchSeq;
	
	/**
	 * 凭证号
	 */
	private String vouchCode;
	
	/**
	 * 状态
	 */
	private Integer vouchStatus;
	
	/**
	 * 开始日期
	 */
	private Date dateFrom;
	
	/**
	 * 结束日期
	 */
	private Date dateTo;
	
	
	/**
	 * 起始金额
	 */
	private BigDecimal amountFrom;
	
	/**
	 * 终止金额
	 */
	private BigDecimal amountTo;
	
	/*
	 * 摘要
	 */
	private String remark;


	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 页数
	 */
	private Integer page;
	
	public VouchSearchCriteria() { }
	
	public VouchSearchCriteria(String vouchSeq, String vouchCode,
			Integer vouchStatus, Date dateFrom, Date dateTo,String remark,
			Integer pageNum) {
		this.vouchSeq = vouchSeq;
		
		this.vouchCode = vouchCode;
		this.vouchStatus = vouchStatus;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.remark=remark;
		this.page = pageNum;
	}

	public BigDecimal getAmountFrom() {
		return amountFrom;
	}

	public void setAmountFrom(BigDecimal amountFrom) {
		this.amountFrom = amountFrom;
	}

	public BigDecimal getAmountTo() {
		return amountTo;
	}

	public void setAmountTo(BigDecimal amountTo) {
		this.amountTo = amountTo;
	}
	
	public String getDateFromString() {
		String dateFromString = "";
		if (dateFrom != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			dateFromString = sdf.format(dateFrom);
		} 
		return dateFromString;
	}
		
	public String getDateToString() {
		String dateToString = "";
		if (dateTo != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			dateToString = sdf.format(dateTo);
		}
		return dateToString;
	}
	
	public String getAmountFromToString(){
		if (amountFrom != null) {
			return amountFrom.toString();
		}
		return null;
	}
	
	public String getAmountToString(){
		if (amountTo != null) {
			return amountTo.toString();
		}
		return null;
	}
	
	public String getVouchSeq() {
		return vouchSeq;
	}

	public void setVouchSeq(String vouchSeq) {
		this.vouchSeq = vouchSeq;
	}

	public String getVouchCode() {
		return vouchCode;
	}

	public void setVouchCode(String vouchCode) {
		this.vouchCode = vouchCode;
	}

	public Integer getVouchStatus() {
		return vouchStatus;
	}

	public void setVouchStatus(Integer vouchStatus) {
		this.vouchStatus = vouchStatus;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer pageNum) {
		this.page = pageNum;
	}

	
	
	
}
