package com.pay.fundout.bankfile.generator.model.boc;

import com.pay.fundout.bankfile.generator.model.FileDetailMode;

/**
 * @author Sandy
 * @Date 2011-6-21
 * @Description 中国银行出款文件Model
 * @Copyright Copyright © 2004-2013 pay.com. All rights reserved.
 */
public class BocGeneratorModel extends FileDetailMode {

	private static final long serialVersionUID = -3252091391903884223L;

	/** 客户业务编号 length<=16 **/
	private String custBusiSerialNo;

	/** 付款方联行号 4 开头5位**/
	private String payerUnionBankCode;
	
	/** 出款到中行：1，出款到他行：0**/
	private String isToBoc;
	
	private String expectDate;

	public String getExpectDate() {
		return expectDate;
	}

	public void setExpectDate(String expectDate) {
		this.expectDate = expectDate;
	}

	public String getIsToBoc() {
		return isToBoc;
	}

	public void setIsToBoc(String isToBoc) {
		this.isToBoc = isToBoc;
	}

	public String getCustBusiSerialNo() {
		return custBusiSerialNo;
	}

	public void setCustBusiSerialNo(String custBusiSerialNo) {
		this.custBusiSerialNo = custBusiSerialNo;
	}

	public String getPayerUnionBankCode() {
		return payerUnionBankCode;
	}

	public void setPayerUnionBankCode(String payerUnionBankCode) {
		this.payerUnionBankCode = payerUnionBankCode;
	}

}
