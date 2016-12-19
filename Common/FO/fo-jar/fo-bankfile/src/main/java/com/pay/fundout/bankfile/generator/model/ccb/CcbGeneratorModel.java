/**
 *  <p>File: AbcGeneratorModel.java</p>
 *  <p>Description:</p>
 *  <p>Copyright: © 2004-2013 pay.com . All rights reserved.版权所有</p>
 *	<p>Company: </p>
 *  @author zengli
 *  @version 1.0  
 */
package com.pay.fundout.bankfile.generator.model.ccb;

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
public class CcbGeneratorModel extends FileDetailMode {

	private static final long serialVersionUID = -6235247386934232934L;

	private String serialNo; // 记录顺序号
	
	private String isOtherBank;

	public String getIsOtherBank() {
		return isOtherBank;
	}

	public void setIsOtherBank(String isOtherBank) {
		this.isOtherBank = isOtherBank;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

}
