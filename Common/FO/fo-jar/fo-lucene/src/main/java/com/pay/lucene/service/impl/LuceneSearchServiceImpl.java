/**
 *  File: LuceneSearchServiceImpl.java
 *  Description:
 *  Copyright Corporation. All rights reserved.
 *  Date      Author      Changes
 *  Jan 7, 2012   ch-ma     Create
 *
 */
package com.pay.lucene.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.util.Version;

import com.chenlb.mmseg4j.analysis.ComplexAnalyzer;
import com.pay.lucene.common.util.FileTransfUtil;
import com.pay.lucene.common.util.IndexSearcherFactory;
import com.pay.lucene.common.util.LuceneConstants;
import com.pay.lucene.dto.SearchParamInfoDTO;
import com.pay.lucene.dto.SearchResultInfoDTO;
import com.pay.lucene.service.LuceneSearchService;
import com.pay.util.StringUtil;

/**
 * 
 */
public class LuceneSearchServiceImpl implements LuceneSearchService {

	private Log logger = LogFactory.getLog(this.getClass());
	private String defaultIndexFilePath;
	private Map<String, String> indexFilePaths;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.lucene.service.LuceneSearchService#searchBankUnionCode(com
	 * .pay.lucene.dto.SearchParamInfoDTO)
	 */
	@Override
	public List<SearchResultInfoDTO> searchBankUnionCode(
			SearchParamInfoDTO params) {

		List<SearchResultInfoDTO> result = new ArrayList<SearchResultInfoDTO>();

		if (null == params || params.isEmpty()) {
			logger.info("params is empty");
			return result;
		}

		Integer type = params.getType();
		String bankName = params.getBankName();
		IndexSearcher searcher = null;
		String indexFilePath = defaultIndexFilePath;
		String keyword = params.getKeyWord();
		boolean isHaveKeyword = StringUtils.isNotEmpty(keyword);

		try {

			if (null != type && type == 2) {
				String value = indexFilePaths.get(bankName);
				if (null != value) {
					indexFilePath = value;
					searcher = IndexSearcherFactory
							.getSpecialIndexSearcherInstance(indexFilePath,
									true);
				}
			} else {
				searcher = IndexSearcherFactory.getIndexSearcherInstance(
						indexFilePath, true);
			}

			Query query = getQuery(params);
			// 结果条数
			int size = getSearchSize(params);

			TopDocs hits = searcher.search(query, size);
			result = parseHits(searcher, hits, size, isHaveKeyword, keyword);
			
			if (result.isEmpty()) {
				params.setBankKaihu("");
				//params.setKeyWord("分行");
				query = getQuery(params);
				hits = searcher.search(query, size);
				result = parseHits(searcher, hits, size, isHaveKeyword, "分行营业部");
			}
			
			if (result.isEmpty()) {
				params.setBankKaihu("");
				//params.setKeyWord("分行");
				query = getQuery(params);
				hits = searcher.search(query, size);
				result = parseHits(searcher, hits, size, isHaveKeyword, "分行");
			}

			if (result.isEmpty()) {
				params.setBankKaihu("");
				query = getQuery(params);
				hits = searcher.search(query, size);
				result = parseHits(searcher, hits, size, isHaveKeyword, keyword);
			}

			return result;
		} catch (CorruptIndexException e) {
			logger.error("contruct Query error:", e);
		} catch (IOException e) {
			logger.error("contruct Query error:", e);
		} catch (ParseException e) {
			logger.error("contruct Query error:", e);
		} catch (Exception e) {
			logger.error("contruct Query error:", e);
		}
		return result;
	}

	public void setDefaultIndexFilePath(String defaultIndexFilePath) {
		this.defaultIndexFilePath = defaultIndexFilePath;
	}

	public void setIndexFilePaths(Map<String, String> indexFilePaths) {
		this.indexFilePaths = indexFilePaths;
	}

	private List<SearchResultInfoDTO> parseHits(IndexSearcher searcher,
			TopDocs hits, int size, boolean isHaveKeyword, String keyWord)
			throws CorruptIndexException, IOException {
		List<SearchResultInfoDTO> result = new ArrayList<SearchResultInfoDTO>();
		int len = hits.scoreDocs.length;

		Document doc = null;
		SearchResultInfoDTO temp = null;
		String address = "".intern();
		int j = 0;
		for (int i = 0; i < len; i++) {
			doc = searcher.doc(hits.scoreDocs[i].doc);
			address = doc.get("bankKaihu");
			if (isHaveKeyword) {
				if (exists(address, keyWord) && j <= size) {
					temp = new SearchResultInfoDTO();
					temp.setBankName(doc.get("bankKaihu"));
					temp.setBankNo(doc.get("bankNo"));
					result.add(temp);
					j++;
				} else {
					continue;
				}
			} else {
				if (j <= size) {
					temp = new SearchResultInfoDTO();
					temp.setBankName(doc.get("bankKaihu"));
					temp.setBankNo(doc.get("bankNo"));
					result.add(temp);
					j++;
				} else {
					break;
				}
			}

		}
		return result;
	}

	private int getSearchSize(SearchParamInfoDTO params) {

		int size = LuceneConstants.DEFAULT_RESULT_SIZE;
		if (null != params.getResultSize()
				&& 0 != params.getResultSize().intValue()) {
			size = params.getResultSize().intValue();
		}

		int tempSize = 0;
		if (StringUtils.isNotEmpty(params.getKeyWord())) {
			tempSize = 1000;
		} else {
			tempSize = size;
		}
		return tempSize;
	}

	private Query getQuery(SearchParamInfoDTO params) throws ParseException {

		String bankName = params.getBankName().trim();
		String provinceName = FileTransfUtil.replaceStr(FileTransfUtil
				.replaceStr(params.getProvinceName().trim(),
						LuceneConstants.ADDRESS_PROVINCE),
				LuceneConstants.ADDRESS_CITY)
				+ "*";
		String cityName = params.getCityName().trim();
		String bankKaihu = params.getBankKaihu();

		String[] keywords = null;
		String[] fields = null;
		BooleanClause.Occur[] classes = null;

		keywords = new String[] { bankName, provinceName, cityName };
		fields = new String[] { "bankName", "proviceField", "cityField" };
		classes = new BooleanClause.Occur[] { BooleanClause.Occur.MUST,
				BooleanClause.Occur.MUST, BooleanClause.Occur.MUST };

		if (!StringUtil.isEmpty(bankKaihu)) {
			keywords = new String[] { bankName, provinceName, cityName,
					bankKaihu };
			fields = new String[] { "bankName", "proviceField", "cityField",
					"bankKaihu" };
			classes = new BooleanClause.Occur[] { BooleanClause.Occur.MUST,
					BooleanClause.Occur.MUST, BooleanClause.Occur.MUST,
					BooleanClause.Occur.MUST };
		}

		Analyzer analyzer = new ComplexAnalyzer();
		Query query = MultiFieldQueryParser.parse(Version.LUCENE_30, keywords,
				fields, classes, analyzer);
		return query;
	}

	private boolean exists(String resource, String searchStr) {
		return resource.indexOf(searchStr) >= 0 ? true : false;
	}
}
