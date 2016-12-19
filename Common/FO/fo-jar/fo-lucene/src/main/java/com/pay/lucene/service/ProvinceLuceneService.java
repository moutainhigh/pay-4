/**
 *  File: ProvinceLuceneService.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-14   terry     Create
 *
 */
package com.pay.lucene.service;

import com.pay.lucene.dto.SearchResultInfoDTO;

/**
 * 地区索引 如：上海市->上海
 */
public interface ProvinceLuceneService {

	/**
	 * 
	 */
	public void buildProvinceIndex();

	/**
	 * 
	 * @param provinceName
	 */
	public SearchResultInfoDTO searchProvince(final String provinceName);
}
