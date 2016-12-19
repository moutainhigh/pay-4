package com.pay.fundout.bankfile.generator.model.gdb;

import com.pay.fundout.bankfile.generator.model.FileDetailMode;

/**		
 *  @author lIWEI
 *  @Date 2011-7-4
 *  @Description 广发银行
 *  @Copyright Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 */
public class GdbGenModel extends FileDetailMode {
	private static final long serialVersionUID = 1708087340927378188L;
	
	private String serialNo; // 记录顺序号
	private String date;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

}
