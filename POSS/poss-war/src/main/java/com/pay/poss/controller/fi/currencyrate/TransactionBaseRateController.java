package com.pay.poss.controller.fi.currencyrate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.client.CurrencyRateService;
import com.pay.poss.dto.CurrencyExchangeRate;
import com.pay.poss.security.util.SessionUserHolderUtil;
import com.pay.util.CurrencyCodeEnum;
import com.pay.util.ExcelUtils;
import com.pay.util.MapUtil;
import com.pay.util.StringUtil;

/**
 * 清算基础汇率
 * 
 * @Description
 * @file SettlementBaseRateController
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright 2006-2012 hnapayay Corporation. All rights reserved.
 *          Date
 */
public class TransactionBaseRateController extends CurrencyRateAbstractController {
	private final Log logger = LogFactory.getLog(getClass());
	private String queryInit;
	private String addView;
	private String recordList;
	private CurrencyRateService currencyRateService;

	public void setQueryInit(String queryInit) {
		this.queryInit = queryInit;
	}

	public void setRecordList(String recordList) {
		this.recordList = recordList;
	}

	public void setAddView(String addView) {
		this.addView = addView;
	}

	public void setCurrencyRateService(CurrencyRateService currencyRateService) {
		this.currencyRateService = currencyRateService;
	}

	/**
	 * 默认查询页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		CurrencyCodeEnum[] currencys = CurrencyCodeEnum.values();
		return new ModelAndView(queryInit).addObject("currencys", currencys);
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView toAdd(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CurrencyCodeEnum[] currencys = CurrencyCodeEnum.values();
		return new ModelAndView(addView).addObject("currencys", currencys);
	}

	/**
	 * 添加
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView add(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest
				.getFile("file");
		String targetCurrency = request.getParameter("targetCurrency");
		String currencyUnit = request.getParameter("currencyUnit");

		Workbook book = Workbook.getWorkbook(file.getInputStream());

		List<CurrencyExchangeRate> list = ExcelUtils.getListByReadShell(
				book.getSheet(0), 1, 0, 7, new String[] { "currency",
						"currencyDesc","targetCurrency","exchangeRate", "reverseExchangeRate",
						"effectDate", "expireDate" },
				CurrencyExchangeRate.class);

		String operator = SessionUserHolderUtil.getLoginId();

		Map<String, String> resultMap = currencyRateService.transactionBaseRateAdd(
				list, currencyUnit, targetCurrency, operator);
		CurrencyCodeEnum[] currencys = CurrencyCodeEnum.values();

		if (logger.isInfoEnabled()) {
			logger.info("response:" + resultMap);
		}

		return new ModelAndView(queryInit, resultMap).addObject("currencys",
				currencys);

	}

	/**
	 * 查询网站信息列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		CurrencyCodeEnum[] currencys = CurrencyCodeEnum.values();
		String currency = StringUtil.null2String(request
				.getParameter("currency"));
		String targetCurrency = StringUtil.null2String(request
				.getParameter("targetCurrency"));
		String effectDate = StringUtil.null2String(request
				.getParameter("effectDate"));
		String expireDate = StringUtil.null2String(request
				.getParameter("expireDate"));
		String status = StringUtil.null2String(request
				.getParameter("status"));
		
		Page page = PageUtils.getPage(request);
		
		Map<String, Object> paraMap = new HashMap();
		paraMap.put("currency", currency);
		paraMap.put("targetCurrency", targetCurrency);
		paraMap.put("effectDate", effectDate);
		paraMap.put("expireDate", expireDate);
		paraMap.put("status",status);
		paraMap.put("page", page);
		
		Map<String,Object> rateMap = currencyRateService.transactionBaseRateQuery(paraMap);
		List<Map> returnList=null;
		String responseCode = (String) rateMap.get("responseCode");
		String responseDesc = (String) rateMap.get("responseDesc");
		returnList = (List<Map>) rateMap.get("list");
			
		Map pageMap = (Map) rateMap.get("page");
		page = MapUtil.map2Object(Page.class, pageMap);
		
		return new ModelAndView(recordList).addObject("rateList", returnList)
				.addObject("currencys", currencys).addObject("page",page);

	}

}
