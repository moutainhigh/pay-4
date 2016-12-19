package com.pay.poss.controller.fi.trade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.poss.client.MerchantTradeCountQueryService;
/***
 * 交易报表
 * @author delin.dong
 *
 */
public class MerchantTradeCountQueryController extends MultiActionController{

	private String tradeCount;
	
	private String tradeCountList;
	
	private String tradeInfoCount;
	
	private String tradeInfoCountList;

	private MerchantTradeCountQueryService merchantTradeCountQueryService;
	
	public void setMerchantTradeCountQueryService(
			MerchantTradeCountQueryService merchantTradeCountQueryService) {
		this.merchantTradeCountQueryService = merchantTradeCountQueryService;
	}

	public void setTradeCountList(String tradeCountList) {
		this.tradeCountList = tradeCountList;
	}

	public void setTradeCount(String tradeCount) {
		this.tradeCount = tradeCount;
	}

	public void setTradeInfoCount(String tradeInfoCount) {
		this.tradeInfoCount = tradeInfoCount;
	}
	
	public void setTradeInfoCountList(String tradeInfoCountList) {
		this.tradeInfoCountList = tradeInfoCountList;
	}

	public ModelAndView tradeCount(HttpServletRequest request,HttpServletResponse response){
		return new ModelAndView(tradeCount);
	}
	
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response){
		return  new ModelAndView(tradeInfoCount);
	}
	/**
	 * 交易报表统计
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView queryTradeCount(HttpServletRequest request,HttpServletResponse response){
		Map param= request(request);
		//param.put("type", "1");
		Map resultMap=merchantTradeCountQueryService.queryTradeCount(param);
		List<Map> tradeOrders = (List<Map>) resultMap.get("result");
		return new ModelAndView(tradeCountList).addObject("tradeOrders", tradeOrders);
	}
	
	/***
	 * 交易消息报表统计
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView queryTradeInfoCount(HttpServletRequest request,HttpServletResponse response){
		Map param= request(request);
		param.put("type", "2");
		Map resultMap=merchantTradeCountQueryService.queryTradeCount(param);
		List<Map> tradeOrders = (List<Map>) resultMap.get("result");
		return new ModelAndView(tradeInfoCountList).addObject("tradeOrders", tradeOrders);
	}
	
	/***
	 * 参数封装
	 * @param request
	 * @return
	 */
	public Map request(HttpServletRequest request){
		String startDate=request.getParameter("startDate");
		String endDate=request.getParameter("endDate");
	//	String partnerId=request.getParameter("partnerId");
		Map<String,String> param=new HashMap<String, String>();
		param.put("startTime", startDate);
		param.put("endTime", endDate);
		//param.put("partnerId", partnerId);
		return param;
	}
	
	
}
