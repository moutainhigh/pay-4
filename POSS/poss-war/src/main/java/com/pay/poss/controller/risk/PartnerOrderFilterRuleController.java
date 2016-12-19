package com.pay.poss.controller.risk;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.acc.exception.MaMemberQueryException;
import com.pay.acc.service.member.MemberQueryService;
import com.pay.acc.service.member.dto.MemberBaseInfoBO;
import com.pay.cardbin.model.CardBinInfo;
import com.pay.cardbin.service.CardBinInfoService;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.inf.model.IpCountryInfo;
import com.pay.inf.service.IpCountryInfoService;
import com.pay.poss.security.util.SessionUserHolderUtil;
import com.pay.rm.orderfilter.dto.OrderFilterRuleDTO;
import com.pay.rm.orderfilter.service.OrderFilterService;
import com.pay.util.CurrencyCodeEnum;
import com.pay.util.StringUtil;

/**
 * 商户订单过滤规则管理
 * 
 * @Description
 * @file PartnerOrderFilterRuleController
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright 2006-2012 hnapayay Corporation. All rights reserved.
 *          Date
 */
public class PartnerOrderFilterRuleController extends MultiActionController{
	private final Log logger = LogFactory.getLog(getClass());
	private String queryInit;
	private String addView;
	private String recordList;
	private MemberQueryService memberQueryService;
	private OrderFilterService orderFilterService;
	private IpCountryInfoService  ipCountryInfoService;
	private CardBinInfoService cardBinInfoService;

	public void setQueryInit(String queryInit) {
		this.queryInit = queryInit;
	}

	public void setRecordList(String recordList) {
		this.recordList = recordList;
	}

	public void setAddView(String addView) {
		this.addView = addView;
	}
	
	public void setOrderFilterService(OrderFilterService orderFilterService) {
		this.orderFilterService = orderFilterService;
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
	public ModelAndView delete(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CurrencyCodeEnum[] currencys = CurrencyCodeEnum.values();
		String id= request.getParameter("id");
		Long idL=0L;
		idL = Long.valueOf(id);
		
		boolean result = orderFilterService.delete(idL);
		String desc="请求处理中";
		if(result){
			desc="删除成功！";
		}else{
			desc="删除失败！";
		}
		
		return new ModelAndView(queryInit, null).addObject("currencys",currencys)
				   .addObject("responseDesc",desc);
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
		OrderFilterRuleDTO ruleDTO = new OrderFilterRuleDTO();

		String startDate = StringUtil.null2String(request
				.getParameter("startDate"));
		String memberCode = StringUtil.null2String(request
				.getParameter("memberCode"));
		String startAmount = StringUtil.null2String(request
				.getParameter("startAmount"));
		String endAmount = StringUtil.null2String(request
				.getParameter("endAmount"));
		String endDate =  StringUtil.null2String(request
				.getParameter("endDate"));
		String id = StringUtil.null2String(request
				.getParameter("id"));
		String ipCountryCode = StringUtil.null2String(request
				.getParameter("ipCountryCode"));
		String cardType = StringUtil.null2String(request
				.getParameter("cardType"));
		String cardCountryCode = StringUtil.null2String(request
				.getParameter("cardCountryCode"));
		String cardCurrencyCode = StringUtil.null2String(request
				.getParameter("cardCurrencyCode"));
		
		if(!StringUtil.isEmpty(cardCurrencyCode)){
			ruleDTO.setCardCurrencyCode(cardCurrencyCode);
		}else{
			ruleDTO.setCardCurrencyCode("*");
		}
		if(!StringUtil.isEmpty(cardCountryCode)){
			ruleDTO.setCardCountryCode(cardCountryCode);
		}else{
			ruleDTO.setCardCountryCode("*");
		}
		if(!StringUtil.isEmpty(cardType)){
			ruleDTO.setCardType(cardType);
		}else{
			ruleDTO.setCardType("*");
		}
		if(!StringUtil.isEmpty(ipCountryCode)){
			ruleDTO.setIpCountryCode(ipCountryCode);
		}else{
			ruleDTO.setIpCountryCode("*");
		}
		if(!StringUtil.isEmpty(id)){
			map0.put("id",id);
			ruleDTO.setId(Long.valueOf(id));
		}
		if(!StringUtil.isEmpty(operator)){
			ruleDTO.setOperator(operator);
		}
		
		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		if(!StringUtil.isEmpty(startDate)){
			ruleDTO.setStartDate(formatter.parse(startDate));
			ruleDTO.setStartTime(startDate);
		}else{
			startDate="2016-05-01 00:00:00";
			ruleDTO.setStartDate(formatter.parse(startDate));
		}
        if(!StringUtil.isEmpty(endDate)){
			ruleDTO.setEndDate(formatter.parse(endDate));
			ruleDTO.setEndTime(endDate);
		}else{
			endDate="2116-05-01 00:00:00";
			ruleDTO.setEndDate(formatter.parse(endDate));
		}
        
        if(ruleDTO.getStartDate().compareTo(ruleDTO.getEndDate())>=0){
        	ruleDTO = this.updateRuleDTO(ruleDTO);
			return new ModelAndView(addView, null)
			.addObject("currencys",currencys)
			.addObject("orderFilterRule",ruleDTO)
			.addObject("responseDesc","时间范围不正确！");
        }
        
        if(!StringUtil.isEmpty(startAmount)&&this.isNumber(startAmount)){
        	ruleDTO.setStartAmount(Double.valueOf(startAmount));
        }else{
        	if(StringUtil.isEmpty(startAmount)){
        		ruleDTO.setStartAmount(0.0);
        	}else if(!this.isNumber(startAmount)){
        		ruleDTO = this.updateRuleDTO(ruleDTO);
        		return new ModelAndView(addView, null)
    			.addObject("currencys",currencys)
    			.addObject("orderFilterRule",ruleDTO)
    			.addObject("responseDesc","起始金额输入不正确");
        	}
        }
        
        if(!StringUtil.isEmpty(endAmount)&&this.isNumber(endAmount)){
        	ruleDTO.setEndAmount(Double.valueOf(endAmount));
        }else{
        	if(StringUtil.isEmpty(endAmount)){
        		ruleDTO.setEndAmount(1000000.0);
        	}else if(!this.isNumber(endAmount)){
        		ruleDTO = this.updateRuleDTO(ruleDTO);
        		return new ModelAndView(addView, null)
    			.addObject("currencys",currencys)
    			.addObject("orderFilterRule",ruleDTO)
    			.addObject("responseDesc","截止金额输入不正确");
        	}
        }
        
        if(ruleDTO.getEndAmount().longValue()
        		       <=ruleDTO.getStartAmount().longValue()){
        	ruleDTO = this.updateRuleDTO(ruleDTO);
        	return new ModelAndView(addView, null)
			.addObject("currencys",currencys)
			.addObject("orderFilterRule",ruleDTO)
			.addObject("responseDesc","截止金额不能小于或等于起始金额");
        }
		
		if(StringUtil.isEmpty(memberCode)){
			ruleDTO = this.updateRuleDTO(ruleDTO);
			return new ModelAndView(addView, null)
			.addObject("currencys",currencys)
			.addObject("orderFilterRule",ruleDTO)
			.addObject("responseDesc","请输入正确的会员号");
		}else{
			Long partnerId = 0L;
			try{
				partnerId = Long.valueOf(memberCode.trim());
			}catch(Exception e){
				logger.info("err: "+e.getMessage());
				ruleDTO = this.updateRuleDTO(ruleDTO);
				return new ModelAndView(addView, null)
				.addObject("currencys",currencys)
				.addObject("orderFilterRule",ruleDTO)
				.addObject("responseDesc","会员号输入不正确！");
			}
			if(partnerId.longValue()!=0){
				//--检查会员号是否存在
				MemberBaseInfoBO memberBaseInfoBO = memberQueryService
						.queryMemberBaseInfoByMemberCode(Long.valueOf(partnerId));
				if(memberBaseInfoBO==null){
					ruleDTO = this.updateRuleDTO(ruleDTO);
					return new ModelAndView(addView, null)
					.addObject("currencys",currencys)
					.addObject("orderFilterRule",ruleDTO)
					.addObject("responseDesc","商户会员号不存在！");
				}
			}
			ruleDTO.setMemberCode(memberCode);
		}
		
		if(!StringUtil.isEmpty(id)){
			ruleDTO.setUpdateDate(new Date());
			orderFilterService.updateOrderFilterRule(ruleDTO);
		}else{
			ruleDTO.setCreateDate(new Date());
			orderFilterService.createOrderFilterRule(ruleDTO);
		}
		return new ModelAndView(queryInit, null).addObject("currencys",
				currencys);
	}
	
	private OrderFilterRuleDTO updateRuleDTO(OrderFilterRuleDTO ruleDTO){
		if(ruleDTO!=null){
			if("*".equals(ruleDTO.getCardCountryCode())){
				ruleDTO.setCardCountryCode(null);
			}
			if("*".equals(ruleDTO.getCardCurrencyCode())){
				ruleDTO.setCardCurrencyCode(null);
			}
			if("*".equals(ruleDTO.getCardType())){
				ruleDTO.setCardType(null);
			}
			if("*".equals(ruleDTO.getIpCountryCode())){
				ruleDTO.setIpCountryCode(null);
			}
		}
		
		return ruleDTO;
	}
	
	// 检查字符串是否为数字
	public  boolean isNumber(String str) {
		if (str == null || str == "")
			return false;
		String regex="^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,6})?$"; //这个只能验证数字是正的
		Pattern pattern = Pattern.compile(regex); 
		Matcher match = pattern.matcher(str);
		if (match.matches() == false) {
			return false;
		} else {
			return true;
		}
	}
	
	// 检查字符串是否为数字
	public  boolean isZfNumber(String str) {
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
	
	/**
	 * 检查数据是否存在
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public ModelAndView checkData(HttpServletRequest request,
			                                HttpServletResponse response) 
			                                		{
		String ipCountryCode = StringUtil.null2String(request
				.getParameter("ipCountryCode"));
		String cardType = StringUtil.null2String(request
				.getParameter("cardType"));
		String cardCountryCode = StringUtil.null2String(request
				.getParameter("cardCountryCode"));
		String memberCode = StringUtil.null2String(request
				.getParameter("memberCode"));
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out=null;
		try {
			out = response.getWriter();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		Map<String,Object> params = new HashMap<String, Object>();
		
		if(!StringUtil.isEmpty(memberCode)){
			Long partnerId = 0L;
			try{
				partnerId = Long.valueOf(memberCode);
			}catch(Exception e){
				out.write("会员号格式不正确!");
			}
			if(partnerId.longValue()!=0){
				try{
				//--检查会员号是否存在
				MemberBaseInfoBO memberBaseInfoBO = memberQueryService
						.queryMemberBaseInfoByMemberCode(Long.valueOf(partnerId));
				  if(memberBaseInfoBO==null){
					  out.write("会员号不存在!");
				  }
				}catch(MaMemberQueryException e){
					out.write(e.getMessage());
				}
			}
		}
		
		if(!StringUtil.isEmpty(ipCountryCode)){
			params.put("countryCode3",ipCountryCode);
			IpCountryInfo info = ipCountryInfoService.getCountryInfo(params);
			if(info==null){
				out.write("没有该国家的IP信息！");
			}
		}
		
		if(!StringUtil.isEmpty(cardType)){
			params.put("cardClass",cardType);
			List<CardBinInfo> list = cardBinInfoService.getCardBinInfos(params);
			if(list==null||list.isEmpty()){
				out.write("卡BIN信息库中没有该类型的信息。");
			}
		}
		
		if(!StringUtil.isEmpty(cardCountryCode)){
			params.put("countryCode3",cardCountryCode);
			List<CardBinInfo> list = cardBinInfoService.getCardBinInfos(params);
			if(list==null||list.isEmpty()){
				out.write("该卡属国在卡BIN库中没有！");
			}
		}
		
		out.flush();
		out.close();
		return null;
		
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

		String memberCode = StringUtil.null2String(request
				.getParameter("memberCode"));
		String startTime = StringUtil.null2String(request
				.getParameter("startTime"));
		String endTime = StringUtil.null2String(request
				.getParameter("endTime"));
		
		Page page = PageUtils.getPage(request);
		
		OrderFilterRuleDTO ruleDTO = new OrderFilterRuleDTO();
		
		if(!StringUtil.isEmpty(startTime)){
			ruleDTO.setStartTime(startTime);
		}
		if(!StringUtil.isEmpty(endTime)){
			ruleDTO.setEndTime(endTime);
		}
		if(!StringUtil.isEmpty(memberCode)){
			ruleDTO.setMemberCode(memberCode);
		}
		
		List<OrderFilterRuleDTO> list = orderFilterService.findByCriteria(ruleDTO, page);
		
		return new ModelAndView(recordList).addObject("oderFilterRuleList", list)
				.addObject("currencys", currencys).addObject("page",page);
	}

	public void setMemberQueryService(MemberQueryService memberQueryService) {
		this.memberQueryService = memberQueryService;
	}

	public void setIpCountryInfoService(IpCountryInfoService ipCountryInfoService) {
		this.ipCountryInfoService = ipCountryInfoService;
	}

	public void setCardBinInfoService(CardBinInfoService cardBinInfoService) {
		this.cardBinInfoService = cardBinInfoService;
	}
}
