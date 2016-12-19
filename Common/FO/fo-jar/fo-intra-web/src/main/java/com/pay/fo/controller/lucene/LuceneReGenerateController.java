/**
 *  File: LuceneGenerateController.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-8-22   terry     Create
 *
 */
package com.pay.fo.controller.lucene;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.Header;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.lucene.service.LuceneService;
import com.pay.util.HttpClientUtil;
import com.pay.util.MD5Util;

/**
 * 
 */
public class LuceneReGenerateController extends MultiActionController {

	private Log logger = LogFactory.getLog(LuceneReGenerateController.class);
	private LuceneService luceneService;
	private String toView;
	private String resultView;
	private String website_address;
	private String poss_address;
	private String lucene_key;

	public void setToView(String toView) {
		this.toView = toView;
	}

	public void setResultView(String resultView) {
		this.resultView = resultView;
	}

	public void setPoss_address(String possAddress) {
		poss_address = possAddress;
	}

	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView(toView);
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView rebuildWebsite(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String type = request.getParameter("type");
		String[] address = null;
		if (null != website_address) {
			address = website_address.split(",");
		}

		String signMsg = MD5Util.md5Hex(new SimpleDateFormat("yyyy-MM-dd")
				.format(new Date())
				+ lucene_key);

		if (null != address) {
			for (int i = 0; i < address.length; i++) {
				byte[] result = HttpClientUtil.executeWithHeader(address[i]
						+ "?type=" + type, new Header("User-Agent", signMsg));
			}
		}

		return new ModelAndView(resultView).addObject("errorMsg", "更新成功！");
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView rebuildPoss(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String type = request.getParameter("type");

		if ("specia".equalsIgnoreCase(type)) {
			luceneService.buildIndex("中国银行");
		} else {
			luceneService.buildIndex("");
		}
		return new ModelAndView(resultView).addObject("errorMsg", "更新成功！");
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView rebuild(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		luceneService.buildIndex(null);
		return null;
	}

	public void setWebsite_address(String websiteAddress) {
		website_address = websiteAddress;
	}

	public void setLucene_key(String luceneKey) {
		lucene_key = luceneKey;
	}

	public void setLuceneService(LuceneService luceneService) {
		this.luceneService = luceneService;
	}

}
