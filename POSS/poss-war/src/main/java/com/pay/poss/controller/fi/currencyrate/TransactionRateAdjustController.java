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
import com.pay.poss.dto.CurrencyRateAdjust;
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
public class TransactionRateAdjustController extends CurrencyRateAbstractController {
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
		Workbook book = Workbook.getWorkbook(file.getInputStream());
		
		@SuppressWarnings("unchecked")
		List<CurrencyRateAdjust> list = ExcelUtils.getListByReadShell(
				book.getSheet(0), 1, 0, 9, new String[]{"memberCode",
						"currency","targetCurrency","adjustRate","cardOrg",
						"startPoint","endPoint","leastTransAmount", "ltaCurrencyCode"},
						CurrencyRateAdjust.class);
		
		CurrencyCodeEnum[] currencys = CurrencyCodeEnum.values();
		
		String valid="";
		for(CurrencyRateAdjust cra:list){
			if(StringUtil.isEmpty(cra.getCurrency())){
				valid = "记录中有原币种为空的请况，请检查！";
				break;
			}
			if(StringUtil.isEmpty(cra.getTargetCurrency())){
				valid = "记录中有目标币种为空的请况，请检查！";
				break;
			}
			if(StringUtil.isEmpty(cra.getAdjustRate())){
				valid = "记录中有汇率调整为空的请况，请检查！";
				break;
			}
			if(StringUtil.isEmpty(cra.getCardOrg())){
				valid = "记录中有卡组织为空的请况，请检查！";
				break;
			}
			if(StringUtil.isEmpty(cra.getStartPoint())){
				valid = "记录中有起始点为空的请况，请检查！";
				break;
			}
			if(StringUtil.isEmpty(cra.getEndPoint())){
				valid = "记录中有截止点为空的请况，请检查！";
				break;
			}
			if(StringUtil.isEmpty(cra.getLeastTransAmount())){
				valid = "记录中有最低交易金额为空的请况，请检查！";
				break;
			}
		}
		
		if(!StringUtil.isEmpty(valid)){
            Map<String,String> responseMap = new HashMap<String, String>();
            responseMap.put("responseMsg",valid);
			return new ModelAndView(queryInit,responseMap).addObject("currencys",
					currencys);
		}
		
		String operator = SessionUserHolderUtil.getLoginId();
		Map<String, String> resultMap = currencyRateService.transactionRateAdjustAdd(list,operator);
		
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
		String memberCode = StringUtil.null2String(request
				.getParameter("memberCode"));
		String cardOrg = StringUtil.null2String(request
				.getParameter("cardOrg"));
		
		Page page = PageUtils.getPage(request);
		
		Map<String, Object> paraMap = new HashMap();
		paraMap.put("currency", currency);
		paraMap.put("targetCurrency", targetCurrency);
		paraMap.put("effectDate", effectDate);
		paraMap.put("expireDate", expireDate);
		paraMap.put("status",status);
		paraMap.put("page", page);
		paraMap.put("memberCode",memberCode);
		paraMap.put("cardOrg",cardOrg);
		
		Map<String,Object> rateMap = currencyRateService.transactionRateAdjustQuery(paraMap);
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
