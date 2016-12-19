/**
 *  <p>File: IcbcGeneratorModel.java</p>
 *  <p>Description:</p>
 *  <p>Copyright: © 2004-2013 pay.com . All rights reserved.版权所有</p>
 *	<p>Company: </p>
 *  @author zengli
 *  @version 1.0  
 */
package com.pay.fundout.bankfile.generator.model.icbc;

import com.pay.fundout.bankfile.generator.model.FileDetailMode;

/**
 * <p></p>
 * @author zengli
 * @since 2011-6-13
 * @see 
 */
public class IcbcGeneratorModel extends FileDetailMode {
	private static final long serialVersionUID = 1L;
	
	private String date ;
	
	private String busiNo;
	
	private String detailMark;
	
	private String baoxiaoNo;
	
	private String receiptNo;

	/**
	 * @return the receiptNo
	 */
	public String getReceiptNo() {
		return receiptNo;
	}

	/**
	 * @param receiptNo the receiptNo to set
	 */
	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	/**
	 * @return the baoxiaoNo
	 */
	public String getBaoxiaoNo() {
		return baoxiaoNo;
	}

	/**
	 * @param baoxiaoNo the baoxiaoNo to set
	 */
	public void setBaoxiaoNo(String baoxiaoNo) {
		this.baoxiaoNo = baoxiaoNo;
	}


	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return the busiNo
	 */
	public String getBusiNo() {
		return busiNo;
	}

	/**
	 * @param busiNo the busiNo to set
	 */
	public void setBusiNo(String busiNo) {
		this.busiNo = busiNo;
	}

	/**
	 * @return the detailMark
	 */
	public String getDetailMark() {
		return detailMark;
	}

	/**
	 * @param detailMark the detailMark to set
	 */
	public void setDetailMark(String detailMark) {
		this.detailMark = detailMark;
	}

	/**
	 * @return the areaCode
	 */
	public String getAreaCode() {
		return areaCode;
	}

	/**
	 * @param areaCode the areaCode to set
	 */
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	private String areaCode;
}
