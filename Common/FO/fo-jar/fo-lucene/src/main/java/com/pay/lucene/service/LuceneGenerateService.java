/**
 *  File: LuceneGenerateService.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-8-22   terry     Create
 *
 */
package com.pay.lucene.service;

import com.pay.poss.base.exception.PossException;

/**
 * 
 */
public interface LuceneGenerateService {

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
}
