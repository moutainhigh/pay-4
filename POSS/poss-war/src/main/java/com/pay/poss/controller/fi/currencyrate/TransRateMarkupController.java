package com.pay.poss.controller.fi.currencyrate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

import com.pay.acc.exception.MaMemberQueryException;
import com.pay.acc.service.member.MemberQueryService;
import com.pay.acc.service.member.dto.MemberBaseInfoBO;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.client.CurrencyRateService;
import com.pay.poss.security.util.SessionUserHolderUtil;
import com.pay.util.CurrencyCodeEnum;
import com.pay.util.MapUtil;
import com.pay.util.NumberUtil;
import com.pay.util.StringUtil;

/**
 * 交易汇率markup
 * 
 * @Description
 * @file TransRateMarkupController
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright 2006-2012 hnapayay Corporation. All rights reserved.
 *          Date
 */
public class TransRateMarkupController extends CurrencyRateAbstractController {
	private final Log logger = LogFactory.getLog(getClass());
	private String queryInit;
	private String addView;
	private String recordList;
	private CurrencyRateService currencyRateService;
	private MemberQueryService memberQueryService;

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
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView edit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CurrencyCodeEnum[] currencys = CurrencyCodeEnum.values();
		
		String id = StringUtil.null2String(request
				.getParameter("id"));
		
		Page page = PageUtils.getPage(request);
		Map<String, Object> paraMap = new HashMap();
		paraMap.put("id",id);
		
		Map<String,Object> rateMap = currencyRateService.transRateMarkupQuery(paraMap);
		List<Map> returnList=null;
		returnList = (List<Map>) rateMap.get("list");
		Map<String,String> map = new HashMap<String, String>();
		if(returnList!=null&&returnList.size()>0){
			map = returnList.get(0);
		}
		
		return new ModelAndView(addView).addObject("currencys", currencys)
				.addObject("markup",map);
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView updateStatus(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CurrencyCodeEnum[] currencys = CurrencyCodeEnum.values();
		String id= request.getParameter("id");
		String status = request.getParameter("status");
		String memberCode = request.getParameter("memberCode");
		String priority = request.getParameter("priority");
		
		if("1".equals(status)){
			Map<String, Object> paraMap = new HashMap<String,Object>();
			paraMap.put("priority",priority);
			paraMap.put("status","1");
			paraMap.put("memberCode",memberCode);
			
			Map<String,Object> rateMap = currencyRateService.transRateMarkupQuery(paraMap);
			List<Map> returnList=null;
			returnList = (List<Map>) rateMap.get("list");
			
			if(returnList!=null&&!returnList.isEmpty()){
				return new ModelAndView(queryInit, null)
				.addObject("currencys",currencys)
				.addObject("responseCode","0001")
				.addObject("responseDesc","相同优先级的规则已存在！无法将该规则设置成生效状态");
			}
			
		}

		Map<String,String> map = new HashMap<String, String>();
		map.put("id",id);
		map.put("status",status);
		
		Map<String,Object> resultMap = currencyRateService.transRateMarkupUpdate(map);
		
		return new ModelAndView(queryInit, resultMap)
		.addObject("currencys",currencys);
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
		CurrencyCodeEnum[] currencys = CurrencyCodeEnum.values();
		String operator = SessionUserHolderUtil.getLoginId();
		Map<String,String> map0 = new HashMap<String, String>();
		
		Map map = request.getParameterMap();
		String [] currencys_ = (String[]) map.get("currency");
		String [] targetCurrencys = (String[]) map.get("targetCurrency");
		String [] cardOrgs = (String[]) map.get("cardOrg");
		String [] cardCountrys = (String[]) map.get("cardCountry");
		String [] cardCurrencyCodes = (String[]) map.get("cardCurrencyCode");
		
		if(currencys_==null||currencys_.length==0){
			map0.put("currency","*");
		}else{
			map0.put("currency",this.getString(currencys_));
		}
		if(targetCurrencys==null||targetCurrencys.length==0){
			map0.put("targetCurrency","*");
		}else{
			map0.put("targetCurrency",this.getString(targetCurrencys));
		}
		if(cardOrgs==null||cardOrgs.length==0){
			map0.put("cardOrg","*");
		}else{
			map0.put("cardOrg",this.getString(cardOrgs));
		}
		if(cardCountrys==null||cardCountrys.length==0){
			map0.put("cardCountry","*");
		}else{
			map0.put("cardCountry",this.getString(cardCountrys));
		}
		if(cardCurrencyCodes==null||cardCurrencyCodes.length==0){
			map0.put("cardCurrencyCode","*");
		}else{
			map0.put("cardCurrencyCode",this.getString(cardCurrencyCodes));
		}
		
		String startPoint = StringUtil.null2String(request
				.getParameter("startPoint"));
		String endPoint = StringUtil.null2String(request
				.getParameter("endPoint"));
		String markup = StringUtil.null2String(request
				.getParameter("markup"));
		String memberCode = StringUtil.null2String(request
				.getParameter("memberCode"));
		String startAmount = StringUtil.null2String(request
				.getParameter("startAmount"));
		String endAmount = StringUtil.null2String(request
				.getParameter("endAmount"));
		String priority =  StringUtil.null2String(request
				.getParameter("priority"));
		String id = StringUtil.null2String(request
				.getParameter("id"));
		
		Long partnerId = 0L;
		try{
			partnerId = Long.valueOf(memberCode.trim());
		}catch(Exception e){
			logger.info("err: "+e.getMessage());
			return new ModelAndView(addView, null)
			.addObject("currencys",currencys)
			.addObject("markup",map0)
			.addObject("responseDesc","会员号输入不正确！");
		}
		
		map0.put("startPoint",startPoint);
		map0.put("endPoint",endPoint);
		map0.put("startAmount",startAmount);
		map0.put("endAmount",endAmount);
		map0.put("markup",markup);
		map0.put("priority",priority);
		map0.put("memberCode",memberCode.trim());
		
		if(StringUtil.isEmpty(startPoint)
				||StringUtil.isEmpty(endPoint)
				||!this.isNumber(startPoint)
				||!this.isNumber(endPoint)
				||(Double.valueOf(startPoint)>Double.valueOf(endPoint))){
			return new ModelAndView(addView, null)
			.addObject("currencys",currencys)
			.addObject("markup",map0)
			.addObject("responseDesc","时间起始点或截止点输入不正确！");
		}
		if(StringUtil.isEmpty(startAmount)
				||StringUtil.isEmpty(endAmount)
				||!this.isNumber(startAmount)
				||!this.isNumber(endAmount)
				||(Double.valueOf(startAmount)>Double.valueOf(endAmount))){
			return new ModelAndView(addView, null)
			.addObject("currencys",currencys)
			.addObject("markup",map0)
			.addObject("responseDesc","起始金额或截止金额输入不正确");
		}
		
		if(StringUtil.isEmpty(markup)
				||!this.isZfNumber(markup)){
			return new ModelAndView(addView, null)
			.addObject("currencys",currencys)
			.addObject("markup",map0)
			.addObject("responseDesc","请输入正确的Markup");
		}
		if(StringUtil.isEmpty(memberCode)){
			return new ModelAndView(addView, null)
			.addObject("currencys",currencys)
			.addObject("markup",map0)
			.addObject("responseDesc","请输入正确的会员号");
		}
		
		if(StringUtil.isEmpty(priority)
				||!this.isNumber(priority)){
			return new ModelAndView(addView, null)
			.addObject("currencys",currencys)
			.addObject("markup",map0)
			.addObject("responseDesc","请输入正确有先值");
		}
		

		
		//--检查会员号是否存在
		if(partnerId.longValue()!=0){
			MemberBaseInfoBO memberBaseInfoBO = null;
			try{
			memberBaseInfoBO = memberQueryService
					.queryMemberBaseInfoByMemberCode(Long.valueOf(partnerId));
			}catch(Exception e){
				e.printStackTrace();
				return new ModelAndView(addView, null)
				.addObject("currencys",currencys)
				.addObject("markup",map0)
				.addObject("responseDesc","商户会员号不存在！");
			}
			
			if(memberBaseInfoBO==null){
				return new ModelAndView(addView, null)
				.addObject("currencys",currencys)
				.addObject("markup",map0)
				.addObject("responseDesc","商户会员号不存在！");
			}	
		}

		//------检查该会员是不是创建相同的有先级的规则
		Map<String, Object> paraMap = new HashMap<String,Object>();
		paraMap.put("priority",priority);
		paraMap.put("status","1");
		paraMap.put("memberCode",memberCode);
		
		Map<String,Object> rateMap = currencyRateService.transRateMarkupQuery(paraMap);
		List<Map> returnList=null;
		returnList = (List<Map>) rateMap.get("list");

		if(returnList!=null&&returnList.size()>0){
			Map tmp = returnList.get(0);
			String idTmp = String.valueOf(tmp.get("id"));
			if(!idTmp.equals(id)){//如果更新或者创建的时候，如果发现修改的结果与现有的生效的规则优先级重复则创建或更新失败
				return new ModelAndView(addView, null)
				.addObject("currencys",currencys)
				.addObject("markup",map0)
				.addObject("responseDesc","相同优先级的规则已存在！");
			}
		}
		///------
		map0.put("operator",operator);
		if (logger.isInfoEnabled()) {
			logger.info("response:" + null);
		}

		if(!StringUtil.isEmpty(id)){
			map0.put("id",id);
			Map<String,Object> resultMap = currencyRateService.transRateMarkupUpdate(map0);
			return new ModelAndView(queryInit, resultMap).addObject("currencys",
					currencys);
		}
		
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		list.add(map0);
		
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("operator",operator);
		params.put("list",list);
		
		Map<String,Object> resultMap = currencyRateService.transRateMarkupAdd(params);

		return new ModelAndView(queryInit, resultMap).addObject("currencys",
				currencys);
	}
	
	// 检查字符串是否为正数字
	public boolean isNumber(String str) {
		if (str == null || str == "")
			return false;
		Pattern pattern = Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,6})?$"); 
		Matcher match = pattern.matcher(str);
		if (match.matches() == false) {
			return false;
		} else {
			return true;
		}
	}
	
	// 检查字符串是否为数字正负
	public boolean isZfNumber(String str) {
		if (str == null || str == "")
			return false;
		String regex = "^(-?[1-9]\\d*\\.?\\d*)|(-?0\\.\\d*[1-9])|(-?[0])|(-?[0]\\.\\d*)$";
		Pattern pattern = Pattern.compile(regex); 
		Matcher match = pattern.matcher(str);
		if (match.matches() == false) {
			return false;
		} else {
			return true;
		}
	}
	
	private String getString(String [] array){
		StringBuffer sb = new StringBuffer("");
		for(String str:array){
			if(sb.length()==0){
				sb.append(str);
			}else{
				sb.append(",").append(str);
			}
		}
		return sb.toString();
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
		String createDate = StringUtil.null2String(request
				.getParameter("createDate"));
		String status = StringUtil.null2String(request
				.getParameter("status"));
		String memberCode = StringUtil.null2String(request
				.getParameter("memberCode"));
		String cardOrg = StringUtil.null2String(request
				.getParameter("cardOrg"));
		String cardCountry = StringUtil.null2String(request
				.getParameter("cardCountry"));
		String cardCurrencyCode = StringUtil.null2String(request
				.getParameter("cardCurrencyCode"));
		String id = StringUtil.null2String(request
				.getParameter("id"));
		String startTime = StringUtil.null2String(request
				.getParameter("startTime"));
		
		Page page = PageUtils.getPage(request);
		
		Map<String, Object> paraMap = new HashMap();
		paraMap.put("currency", currency);
		paraMap.put("targetCurrency", targetCurrency);
		paraMap.put("createDate", createDate);
		paraMap.put("id",id);
		paraMap.put("status",status);
		paraMap.put("cardCountry",cardCountry);
		paraMap.put("cardCurrencyCode",cardCurrencyCode);
		paraMap.put("cardOrg",cardOrg);
		paraMap.put("page", page);
		paraMap.put("memberCode", memberCode);
		paraMap.put("startTime",startTime);
		
		
		Map<String,Object> rateMap = currencyRateService.transRateMarkupQuery(paraMap);
		List<Map> returnList=null;
		String responseCode = (String) rateMap.get("responseCode");
		String responseDesc = (String) rateMap.get("responseDesc");
		returnList = (List<Map>) rateMap.get("list");
			
		Map pageMap = (Map) rateMap.get("page");
		page = MapUtil.map2Object(Page.class, pageMap);
		
		return new ModelAndView(recordList).addObject("markupList", returnList)
				.addObject("currencys", currencys).addObject("page",page);
	}

	public void setMemberQueryService(MemberQueryService memberQueryService) {
		this.memberQueryService = memberQueryService;
	}
}
