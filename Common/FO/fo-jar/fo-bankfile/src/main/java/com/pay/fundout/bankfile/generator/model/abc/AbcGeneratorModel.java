/**
 *  <p>File: AbcGeneratorModel.java</p>
 *  <p>Description:</p>
 *  <p>Copyright: © 2004-2013 pay.com . All rights reserved.版权所有</p>
 *	<p>Company: </p>
 *  @author zengli
 *  @version 1.0  
 */
package com.pay.fundout.bankfile.generator.model.abc;

import com.pay.fundout.bankfile.generator.model.FileDetailMode;

/**
 * 
 * <p>
 * 农业银行
 * </p>
 * 
 * @author wucan
 * @since 2011-6-15
 * @see
 */
public class AbcGeneratorModel extends FileDetailMode {

	private static final long serialVersionUID = -6235247386934232934L;

	private String serialNo; // 记录顺序号
	private String payType; // 结算种类
	private String currFlag; // 钞汇种类

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getCurrFlag() {
		return currFlag;
	}

	public void setCurrFlag(String currFlag) {
		this.currFlag = currFlag;
	}

}
