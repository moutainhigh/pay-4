package com.pay.base.dto;

import java.util.Date;

import com.pay.util.DateUtil;

/**
 * 用于支付链统计查询列表时传参数到数据库的dto
 * @author DDR
 * Date 2011-09-21
 */
public class PayChainStatParamDto {
	
	private String startTime;
	private String endTime;
	private String payChainNumber;
	private String status;
	private int pageNo;
	private int pageSize;
	private String datePattern;
	private Long memberCode;
//	private Date createBegin;
//	private Date createEnd;
	/**
	 * @return the createBegin
	 */
	public Date getCreateBegin() {
		if(startTime!=null && startTime.trim().length()>0 ){
			try{
				return DateUtil.parse(datePattern,startTime);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
		
	}
	/**
	 * @return the createEnd
	 */
	public Date getCreateEnd() {
		if(endTime!=null && endTime.trim().length()>0){
			try{
				return DateUtil.parse(datePattern,endTime);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	/**
	 * @return the payChainNumber
	 */
	public String getPayChainNumber() {
		return payChainNumber;
	}
	/**
	 * @param payChainNumber the payChainNumber to set
	 */
	public void setPayChainNumber(String payChainNumber) {
		this.payChainNumber = payChainNumber;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the pageNo
	 */
	public int getPageNo() {
		return pageNo<= 0 ? 1 :pageNo ;
	}
	/**
	 * @param pageNo the pageNo to set
	 */
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize == 0 ? 10 : pageSize;
	}
	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	/**
	 * @return the startTime
	 */
	public String getStartTime() {
		return startTime;
	}
	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	/**
	 * @return the endTime
	 */
	public String getEndTime() {
		return endTime;
	}
	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
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
	
	
	
	
	
	
	
	

}
