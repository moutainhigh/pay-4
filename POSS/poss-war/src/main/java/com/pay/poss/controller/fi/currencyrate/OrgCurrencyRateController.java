package com.pay.poss.controller.fi.currencyrate;

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

import com.pay.poss.client.ChannelClientService;
import com.pay.poss.client.CurrencyRateService;
import com.pay.poss.dto.CurrencyExchangeRate;
import com.pay.poss.dto.PaymentChannelItemDto;
import com.pay.poss.security.util.SessionUserHolderUtil;
import com.pay.util.CurrencyCodeEnum;
import com.pay.util.ExcelUtils;
import com.pay.util.StringUtil;

/**
 * 网站管理poss 后台
 * 
 * @Description
 * @file SitesetController
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright 2006-2012 hnapayay Corporation. All rights reserved.
 *          Date
 */
public class OrgCurrencyRateController extends CurrencyRateAbstractController {

	private final Log logger = LogFactory.getLog(getClass());
	private String queryInit;
	private String addView;
	private String recordList;
	private CurrencyRateService currencyRateService;
	private ChannelClientService channelClientService;

	public void setQueryInit(String queryInit) {
		this.queryInit = queryInit;
	}

	public void setRecordList(String recordList) {
		this.recordList = recordList;
	}

	public void setAddView(String addView) {
		this.addView = addView;
	}

	public void setChannelClientService(
			ChannelClientService channelClientService) {
		this.channelClientService = channelClientService;
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

		PaymentChannelItemDto paymentChannelItem = new PaymentChannelItemDto();
		List orgList = channelClientService
				.queryChannelItem(paymentChannelItem);

		CurrencyCodeEnum[] currencys = CurrencyCodeEnum.values();
		return new ModelAndView(queryInit).addObject("currencys", currencys)
				.addObject("orgList", orgList);
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
		PaymentChannelItemDto paymentChannelItem = new PaymentChannelItemDto();
		List orgList = channelClientService
				.queryChannelItem(paymentChannelItem);
		return new ModelAndView(addView).addObject("currencys", currencys)
				.addObject("orgList", orgList);
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

		String orgCode = request.getParameter("orgCode");

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest
				.getFile("file");
		String targetCurrency = request.getParameter("targetCurrency");
		String currencyUnit = request.getParameter("currencyUnit");

		Workbook book = Workbook.getWorkbook(file.getInputStream());

		List<CurrencyExchangeRate> list = ExcelUtils.getListByReadShell(
				book.getSheet(0), 1, 0, 6, new String[] { "currency",
						"currencyDesc", "exchangeRate", "reverseExchangeRate",
						"effectDate", "expireDate" },
				CurrencyExchangeRate.class);

		String operator = SessionUserHolderUtil.getLoginId();

		Map<String, String> resultMap = currencyRateService.orgCurrencyRateAdd(
				orgCode, list, currencyUnit, targetCurrency, operator);
		CurrencyCodeEnum[] currencys = CurrencyCodeEnum.values();

		if (logger.isInfoEnabled()) {
			logger.info("response:" + resultMap);
		}

		PaymentChannelItemDto paymentChannelItem = new PaymentChannelItemDto();
		List orgList = channelClientService
				.queryChannelItem(paymentChannelItem);
		return new ModelAndView(queryInit, resultMap).addObject("currencys",
				currencys).addObject("orgList", orgList);

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
		String orgCode = StringUtil
				.null2String(request.getParameter("orgCode"));
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
		
		List<Map> rateList = currencyRateService.orgCurrencyRateQuery(orgCode,
				currency, targetCurrency, effectDate, expireDate,status);
		return new ModelAndView(recordList).addObject("rateList", rateList)
				.addObject("currencys", currencys);

	}

}
