/**
 *  <p>File: AbcGeneratorModel.java</p>
 *  <p>Description:</p>
 *  <p>Copyright: © 2004-2013 pay.com . All rights reserved.版权所有</p>
 *	<p>Company: </p>
 *  @author zengli
 *  @version 1.0  
 */
package com.pay.fundout.bankfile.generator.model.cib;

import com.pay.fundout.bankfile.generator.model.FileDetailMode;

/**
 * 
 * <p>
 * 兴业银行
 * </p>
 * 
 * @author liwei
 * @since 2013-12-17
 * @see
 */
public class CibGeneratorModel extends FileDetailMode {

	private static final long serialVersionUID = -6235247386934232934L;
	
	private String isSameBank; // 是否同行账号

	public String getIsSameBank() {
		return isSameBank;
	}

	public void setIsSameBank(String isSameBank) {
		this.isSameBank = isSameBank;
	}


}
