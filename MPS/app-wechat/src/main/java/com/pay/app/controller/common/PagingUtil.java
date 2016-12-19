package com.pay.app.controller.common;

import javax.servlet.http.HttpServletRequest;

import com.pay.app.common.pagination.PagingParamSetter;
import com.pay.app.controller.common.pagination.VPage;

/**
 *  File: PagingUtil.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-13   terry_ma     Create
 *
 */
public interface PagingUtil {
	/**
	 * Create paging info using the Http request and query criteria
	 * 
	 * @param request
	 * @param queryCriteria
	 *            bean wraps the query criteria
	 * @param paramSetting
	 *            Util class for generating the query string
	 * @return
	 */
	public VPage createPageInfo(HttpServletRequest request,
			String contextRealPath, Object queryCriteria,
			PagingParamSetter paramSetting);

	/**
	 * Reset the page info after navigate to the previous/next page.
	 * 
	 * @param request
	 * @param pageInfo
	 */
	public void resetPageInfo(HttpServletRequest request, VPage pageInfo);
}