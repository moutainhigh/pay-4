package com.pay.fundout.bankfile.generator.model.cnpy;

import com.pay.fundout.bankfile.generator.model.FileDetailMode;

/**		
 *  @author lIWEI
 *  @Date 2011-6-14
 *  @Description
 *  @Copyright Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 */
public class CnpyGeneratorModel extends FileDetailMode {
	private static final long serialVersionUID = 1L;

	private String date;
	private String type;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getType() {
		
		String typeDesc = "0" + getTradeType();
		return typeDesc;
	}

	public void setType(String type) {
		this.type = type;
	}
}
