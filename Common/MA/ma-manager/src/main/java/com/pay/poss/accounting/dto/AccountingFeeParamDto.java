/**
 * 
 */
package com.pay.poss.accounting.dto;

import java.util.Date;

import com.pay.util.DateUtil;


/**
 * 传参数的dto
 * @Description 
 * @project 	ma-manager
 * @file 		AccountingFeeParamDto.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Date				Author			Changes
 * 2012-3-2			DDR				Create
 */
public class AccountingFeeParamDto {
	
	private String beginDate;
	private String endDate;
	private String datePattern = "yyyy-MM-dd";
	private Long memberCode;
	private String acctName;
	private Integer dealType;
	private String orderNo;
//	private Date createBegin;
//	private Date createEnd;
	/**
	 * @return the createBegin
	 */
	public Date getBegin() {
		if(beginDate!=null && beginDate.trim().length()>0 ){
			try{
				return DateUtil.parse(datePattern,beginDate);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
		
	}
	/**
	 * @return the createEnd
	 */
	public Date getEnd() {
		if(endDate!=null && endDate.trim().length()>0){
			try{
				return DateUtil.parse(datePattern,endDate);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	
	
	
	public String getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	/**
	 * @return the datePattern
	 */
	public String getDatePattern() {
		return datePattern;
	}
	/**
	 * @param datePattern the datePattern to set
	 */
	public void setDatePattern(String datePattern) {
		this.datePattern = datePattern;
	}
	/**
	 * @return the memberCode
	 */
	public Long getMemberCode() {
		return memberCode;
	}
	/**
	 * @param memberCode the memberCode to set
	 */
	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}
	public String getAcctName() {
		return acctName;
	}
	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}
	public Integer getDealType() {
		return dealType;
	}
	public void setDealType(Integer dealType) {
		this.dealType = dealType;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	
	
	
	

}
