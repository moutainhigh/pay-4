/**
 *  <p>File: SdbGeneratorModel.java</p>
 *  <p>Description:</p>
 *  <p>Copyright: © 2004-2013 pay.com . All rights reserved.版权所有</p>
 *	<p>Company: </p>
 *  @author zengli 
 *  @version 1.0  
 */
package com.pay.fundout.bankfile.generator.model.sdb;

import com.pay.fundout.bankfile.generator.model.FileDetailMode;

/**
 * <p></p>
 * @author zengli
 * @since 2011-6-23
 * @see 
 */
public class SdbGeneratorModel extends FileDetailMode {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4525351584721263539L;
	/**
	 * @return the transferType
	 */
	public String getTransferType() {
		return transferType;
	}
	/**
	 * @param transferType the transferType to set
	 */
	public void setTransferType(String transferType) {
		this.transferType = transferType;
	}
	/**
	 * @return the transferMode
	 */
	public String getTransferMode() {
		return transferMode;
	}
	/**
	 * @param transferMode the transferMode to set
	 */
	public void setTransferMode(String transferMode) {
		this.transferMode = transferMode;
	}
	/**
	 * @return the ifYqPay
	 */
	public String getIfYqPay() {
		return ifYqPay;
	}
	/**
	 * @param ifYqPay the ifYqPay to set
	 */
	public void setIfYqPay(String ifYqPay) {
		this.ifYqPay = ifYqPay;
	}
	/**
	 * @return the yqPayDate
	 */
	public String getYqPayDate() {
		return yqPayDate;
	}
	/**
	 * @param yqPayDate the yqPayDate to set
	 */
	public void setYqPayDate(String yqPayDate) {
		this.yqPayDate = yqPayDate;
	}
	/**
	 * @return the currFlag
	 */
	public String getCurrFlag() {
		return currFlag;
	}
	/**
	 * @param currFlag the currFlag to set
	 */
	public void setCurrFlag(String currFlag) {
		this.currFlag = currFlag;
	}
	/**
	 * 转账类型
	 */
	private String transferType;
	/**
	 * 转账方式
	 */
	private String transferMode;
	/**
	 * 是否远期支付
	 */
	private String ifYqPay;
	/**
	 * 远期支付日期
	 */
	private String yqPayDate;
	/**
	 * 钞汇标志
	 */
	private String currFlag;
}
