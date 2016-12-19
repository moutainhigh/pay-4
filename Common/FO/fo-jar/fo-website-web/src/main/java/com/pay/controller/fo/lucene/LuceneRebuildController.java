/**
 *  File: LuceneRebuildController.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-6   terry     Create
 *
 */
package com.pay.controller.fo.lucene;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.pay.lucene.service.LuceneService;
import com.pay.util.MD5Util;

/**
 * 
 */
public class LuceneRebuildController extends AbstractController {

	private LuceneService luceneService;
	private String lucene_key;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.AbstractController#handleRequestInternal
	 * (javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String signMsg = MD5Util.md5Hex(new SimpleDateFormat("yyyy-MM-dd")
				.format(new Date())
				+ lucene_key);
		
		String type = request.getParameter("type");

		String referer = request.getHeader("User-Agent");//
		if (signMsg.equals(referer)) {
			
			if ("specia".equalsIgnoreCase(type)) {
				luceneService.buildIndex("中国银行");
			} else {
				luceneService.buildIndex(null);
			}
			response.getWriter().print("ok");
		}

		return null;
	}

	public void setLuceneService(LuceneService luceneService) {
		this.luceneService = luceneService;
	}

	public void setLucene_key(String luceneKey) {
		lucene_key = luceneKey;
	}

}
