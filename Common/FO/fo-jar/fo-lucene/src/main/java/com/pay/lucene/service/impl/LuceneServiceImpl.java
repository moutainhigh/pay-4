/**
 *  File: LuceneServiceImpl.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-12-21      Jason_wang      Changes
 *  
 *
 */
package com.pay.lucene.service.impl;

import java.util.List;

import com.pay.lucene.dto.SearchParamInfoDTO;
import com.pay.lucene.dto.SearchResultInfoDTO;
import com.pay.lucene.service.LuceneGenerateService;
import com.pay.lucene.service.LuceneSearchService;
import com.pay.lucene.service.LuceneService;
import com.pay.poss.base.exception.PossException;

/**
 * @author Jason_wang
 * 
 */
public class LuceneServiceImpl implements LuceneService {

	private LuceneGenerateService generateService;
	private LuceneSearchService searchService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.lucene.service.LuceneService#buildIndex(java.lang.String)
	 */
	@Override
	public void buildIndex(final String bankName) throws Exception {

		generateService.buildIndex(bankName);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.lucene.service.LuceneService#searchUnionBankCodeInfo(java.
	 * lang.String, int)
	 */
	@Override
	public List<SearchResultInfoDTO> searchBankUnionCodes(
			SearchParamInfoDTO params) {

		return searchService.searchBankUnionCode(params);
	}

	/**
	 * 获取联行号
	 * 
	 * @param params
	 * @return
	 * @throws PossException
	 */
	@Override
	public String searchBankUnionCode(SearchParamInfoDTO params) {

		List<SearchResultInfoDTO> resultList = searchBankUnionCodes(params);
		if (null != resultList && !resultList.isEmpty()) {
			return resultList.get(0).getBankNo();
		} else {
			return null;
		}
	}

	@Override
	public List<SearchResultInfoDTO> searchUnionBankCodeInfo(
			SearchParamInfoDTO params) throws PossException {

		return searchBankUnionCodes(params);
	}

	public void setGenerateService(LuceneGenerateService generateService) {
		this.generateService = generateService;
	}

	public void setSearchService(LuceneSearchService searchService) {
		this.searchService = searchService;
	}

}
