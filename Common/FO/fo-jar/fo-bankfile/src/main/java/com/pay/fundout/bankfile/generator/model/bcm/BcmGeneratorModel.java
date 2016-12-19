/**
 *  <p>File: IcbcGeneratorModel.java</p>
 *  <p>Description:</p>
 *  <p>Copyright: © 2004-2013 pay.com . All rights reserved.版权所有</p>
 *	<p>Company: </p>
 *  @author zengli
 *  @version 1.0  
 */
package com.pay.fundout.bankfile.generator.model.bcm;

import com.pay.fundout.bankfile.generator.model.FileDetailMode;

/**
 * <p>
 * 交通银行
 * </p>
 * 
 * @author zengli
 * @since 2011-6-13
 * @see
 */
public class BcmGeneratorModel extends FileDetailMode {

	private static final long serialVersionUID = 2160642139392773769L;

	private String serialNo; // 记录顺序号
	private String isBcm; // 是否交行账号

	private String withTheCity; // 是否同城

	private String bcmERPCode; // ERP序员

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getIsBcm() {
		return isBcm;
	}

	public void setIsBcm(String isBcm) {
		this.isBcm = isBcm;
	}

	public String getWithTheCity() {
		return withTheCity;
	}

	public void setWithTheCity(String withTheCity) {
		this.withTheCity = withTheCity;
	}

	public String getBcmERPCode() {
		return bcmERPCode;
	}

	public void setBcmERPCode(String bcmERPCode) {
		this.bcmERPCode = bcmERPCode;
	}
}
