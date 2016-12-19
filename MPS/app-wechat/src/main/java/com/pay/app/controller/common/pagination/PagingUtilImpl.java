package com.pay.app.controller.common.pagination;

import javax.servlet.http.HttpServletRequest;

import com.pay.app.common.pagination.PagingParamSetter;
import com.pay.app.controller.common.PagingUtil;

/**
 *  File: PagingUtilImpl.java
 *  Description:
 *  Copyright © 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-13   terry_ma     Create
 *
 */
public class PagingUtilImpl implements PagingUtil {
	private static final int DEFAULT_PAGE_SIZE = 10;

	/**
	 * Create paging info using the Http request and query criteria
	 * 
	 * @param request
	 * @param contextRealPath
	 * @param queryCriteria
	 *            bean wraps the query criteria
	 * @param paramSetting
	 *            Util class for generating the query string
	 * @return
	 */
	public VPage createPageInfo(final HttpServletRequest request,
			final String contextRealPath, final Object queryCriteria,
			final PagingParamSetter paramSetting) {
		VPage vpage = new VPage();
		PageHelper pageHelper = new PageHelper(request);
		int page = ParamUtil.getInt(request, "page", 1);
		int pageSize = pageHelper.doCustomPerPage(DEFAULT_PAGE_SIZE);
		vpage.setPageSize(pageSize);
		vpage.setTargetPage(page);

		String queryString = paramSetting.generateQueryString(queryCriteria);
		vpage.setParamName(queryString);

		vpage.setRealPath(contextRealPath);

		return vpage;

	}

	/**
	 * Reset the page info after navigate to the previous/next page.
	 * 
	 * @param request
	 * @param pageInfo
	 */
	public void resetPageInfo(HttpServletRequest request, VPage pageInfo) {
		PageHelper pageHelper = new PageHelper(request);
		pageInfo.setView(pageHelper.getPageBreakStr(pageInfo));
	}

}
