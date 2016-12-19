package com.pay.base.dto;


/**
 * 用于支付链统计查询dto
 * @author DDR
 * Date 2011-09-21
 */
public class PayChainPaymentParamDto {
	
	private String payChainNumber;
	private int pageNo;
	private int pageSize;
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
	 * @return the pageNo
	 */
	public int getPageNo() {
		return pageNo;
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
		return pageSize;
	}
	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	
	

}
