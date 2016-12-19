/**
 *  File: LuceneSearchService.java
 *  Description:
 *  Copyright Corporation. All rights reserved.
 *  Date      Author      Changes
 *  Jan 7, 2012   ch-ma     Create
 *
 */
package com.pay.lucene.service;

import java.util.List;

import com.pay.lucene.dto.SearchParamInfoDTO;
import com.pay.lucene.dto.SearchResultInfoDTO;
import com.pay.poss.base.exception.PossException;

/**
 * 
 */
public interface LuceneSearchService {

	/**
	 * 获取联行号
	 * 
	 * @param params
	 * @return
	 * @throws PossException
	 */
	List<SearchResultInfoDTO> searchBankUnionCode(
			final SearchParamInfoDTO params);
}
