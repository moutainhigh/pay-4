/**
 *  File: SearchParamInfoDTO.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-12-31      Jason_wang      Changes
 *  
 *
 */
package com.pay.lucene.dto;

import com.pay.lucene.common.util.LuceneConstants;
import com.pay.inf.model.BaseObject;
import com.pay.util.StringUtil;

/**
 * @author Jason_wang
 * 
 */
public class SearchParamInfoDTO extends BaseObject {

	private static final long serialVersionUID = -8957635581493688475L;
	public static int NORMAL = 1;
	public static int SPECIFIC = 2;
	// 银行名称
	private String bankName;
	// 地区名称
	private String provinceName;
	// 城市名称
	private String cityName;
	// 关键字
	private String keyWord;
	// 联行号类型：1-普通，2-专属
	private Integer type;

	private String bankKaihu;

	private Integer resultSize = new Integer(
			LuceneConstants.DEFAULT_RESULT_SIZE);

	public Integer getResultSize() {
		return resultSize;
	}

	public void setResultSize(Integer resultSize) {
		this.resultSize = resultSize;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getBankKaihu() {
		return bankKaihu;
	}

	public void setBankKaihu(String bankKaihu) {
		this.bankKaihu = bankKaihu;
	}

	public String toString() {
		StringBuffer temp = new StringBuffer();
		temp.append("SearchParamInfoDTO[");
		temp.append("bankName=" + bankName);
		temp.append("provinceName=" + provinceName);
		temp.append("cityName=" + cityName);
		temp.append("type=" + type);
		temp.append("resultSize=" + resultSize);
		temp.append("bankKaihu=" + bankKaihu);
		return temp.toString();
	}

	public boolean isEmpty() {

		return StringUtil.isEmpty(bankName) && StringUtil.isEmpty(provinceName)
				&& StringUtil.isEmpty(cityName) && null == type;
	}
}
