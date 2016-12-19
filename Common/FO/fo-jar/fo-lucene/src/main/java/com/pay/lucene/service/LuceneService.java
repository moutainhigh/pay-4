/**
 *  File: LuceneService.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-12-20      Jason_wang      Changes
 *  
 *
 */
package com.pay.lucene.service;

import java.util.List;

import com.pay.lucene.dto.SearchParamInfoDTO;
import com.pay.lucene.dto.SearchResultInfoDTO;
import com.pay.poss.base.exception.PossException;

/**
 * @author Jason_wang
 * 
 */
public interface LuceneService {

	/**
	 * 创建索引
	 * 
	 * @param fileName
	 *            资源文件
	 * @param keywordFileName
	 *            关键字文件名称
	 * @throws PossException
	 */
	void buildIndex(final String bankName) throws Exception;

	/**
	 * 获取联行号
	 * 
	 * @param params
	 * @return
	 * @throws PossException
	 */
	List<SearchResultInfoDTO> searchBankUnionCodes(SearchParamInfoDTO params);

	/**
	 * 获取联行号
	 * 
	 * @param params
	 * @return
	 * @throws PossException
	 */
	String searchBankUnionCode(SearchParamInfoDTO params);

	/**
	 * 
	 * @param params
	 * @return
	 */
	List<SearchResultInfoDTO> searchUnionBankCodeInfo(SearchParamInfoDTO params)
			throws PossException;
}
