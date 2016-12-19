package com.pay.app.common.pagination;

/**
 *  File: PagingParamSetter.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-18   Terry_maa    Create
 *
 */
public interface PagingParamSetter {
	/** 
	 * This method generates query string using the given query criteria, the
	 * query string looks like 'a=1&b=2&c=3'.
	 * 
	 * @param queryCriteria
	 *            query criteria for generating the query string
	 * @return a query string used for paging
	 */
	public String generateQueryString(Object queryCriteria);
}