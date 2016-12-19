/**
 *  <p>File: CmbGeneratorModel.java</p>
 *  <p>Description:</p>
 *  <p>Copyright: © 2004-2013 pay.com . All rights reserved.版权所有</p>
 *	<p>Company: </p>
 *  @author zengli
 *  @version 1.0  
 */
package com.pay.fundout.bankfile.generator.model.cmbc;

import com.pay.fundout.bankfile.generator.model.FileDetailMode;

/**
 * <p>生成招行文件Model</p>
 * @author zengli
 * @since 2011-5-17
 * @see 
 */
public class CmbcGeneratorModel extends FileDetailMode{
	private String serialNo; // 记录顺序号
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	private static final long serialVersionUID = 1L;
}
