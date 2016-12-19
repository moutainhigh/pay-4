package com.pay.poss.controller.fo.credit;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.client.OrderCreditCoreService;
import com.pay.util.MapUtil;
import com.pay.util.StringUtil;

/**
 * 订单授信Controller
 * @author Jiangbo.Peng
 *
 */

public class OrderCreditController extends MultiActionController {

	private String index;
	private String orderCreditList;
	private String orderCreditDetailIndex ;
	private String orderCreditDetailList ;
	private String orderCreditSingleDetail ;
	
	private OrderCreditCoreService orderCreditCoreService ;
	
	/**
	 * 到达融资订单查询首页
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView(index);
	}

	/****
	 * 融资订单查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView orderCreditQuery(HttpServletRequest request,
			HttpServletResponse response) {
		String partnerId = StringUtil.null2String(request.getParameter("partnerId")) ;
		String creditId = request.getParameter("creditId");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String creditType = request.getParameter("creditType");
		String partnerName = request.getParameter("partnerName") ;
		Page page = PageUtils.getPage(request);
		Map<String, Object> paraMap = new HashMap<String, Object>() ;
		paraMap.put("creditId", creditId) ;
		paraMap.put("applyStartDate", startTime) ;
		paraMap.put("applyEndDate", endTime) ;
		paraMap.put("creditType", creditType) ;
		paraMap.put("partnerId", partnerId) ;
		paraMap.put("partnerName", partnerName) ;
		paraMap.put("page", page) ;
		
		Map resultMap = this.orderCreditCoreService.orderCreditOrderQuery(paraMap) ;
		List<Map> listMap = (List<Map>) resultMap.get("result") ;
		if(CollectionUtils.isNotEmpty(listMap)){
			for(Map m : listMap){
				if(null !=m.get("applyDate")){
					m.put("applyDate", this.Object2DateStr(m.get("applyDate"))) ;
				}
			}
		}
		Map pageMap = (Map) resultMap.get("page") ;
		page = MapUtil.map2Object(Page.class, pageMap) ;
		
		return new ModelAndView(this.orderCreditList).addObject("page", page)
				.addObject("orderCredits", listMap);
		
	}
	
	/**
	 * 
	 * @param obj
	 * @return
	 */
	private String Object2DateStr(Object obj){
		String dateStr = "" ;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;
		if(null != obj){
			dateStr = sdf.format(obj) ;
		}
		return dateStr ;
	}
	
	/**
	 * 到达融资详情页面
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toOrderCreditDetailView(HttpServletRequest request, HttpServletResponse response){
		return new ModelAndView(this.orderCreditDetailIndex) ;
	}
	
	/**
	 * 融资订单详情列表
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView orderCreditDetailList(HttpServletRequest request, HttpServletResponse response){
		
		String singDetail = StringUtil.null2String(request.getParameter("singDetail")) ;
		String creditId = StringUtil.null2String(request.getParameter("creditId")) ;
		String tradeOrderId = StringUtil.null2String(request.getParameter("tradeOrderId")) ;
		String partnerName = StringUtil.null2String(request.getParameter("partnerName")) ;
		String creditDetailId = StringUtil.null2String(request.getParameter("creditDetailId")) ;
		String partnerId = StringUtil.null2String(request.getParameter("partnerId")) ;
		String applyStartDate = StringUtil.null2String(request.getParameter("applyStartDate")) ;
		String applyEndDate = StringUtil.null2String(request.getParameter("applyEndDate")) ;
		
		Map<String, Object> paraMap = new HashMap<String, Object>() ;
		paraMap.put("creditId", creditId) ;
		paraMap.put("tradeOrderId", tradeOrderId) ;
		paraMap.put("partnerName", partnerName) ;
		paraMap.put("creditDetailId", creditDetailId) ;
		paraMap.put("partnerId", partnerId) ;
		paraMap.put("applyStartDate", applyStartDate) ;
		paraMap.put("applyEndDate", applyEndDate) ;
		Page page = null ;
		if(StringUtils.isNotEmpty(singDetail)){
			String pageNo = request.getParameter("pageNo") ;
			page = PageUtils.getPage(request);
			page.setPageNo(pageNo);
		}else{
			page = PageUtils.getPage(request);
		}
		paraMap.put("page", page) ;
		
		Map resultMap = this.orderCreditCoreService.orderCreditOrderDetailQuery(paraMap) ;
		List<Map> listMap = (List<Map>) resultMap.get("result") ;
		/*if(CollectionUtils.isNotEmpty(listMap)){
			for(Map m : listMap){
				if(null != m.get("settlementDate")){
					m.put("settlementDate", this.Object2DateStr(m.get("settlementDate"))) ;
				}
				if(null != m.get("accountDate")){
					m.put("accountDate", this.Object2DateStr(m.get("accountDate"))) ;
				}
				if(null != m.get("applyDate")){
					m.put("applyDate", this.Object2DateStr(m.get("applyDate"))) ;
				}
			}
		}*/
		Map pageMap = (Map) resultMap.get("page") ;
		page = MapUtil.map2Object(Page.class, pageMap) ;
		if(StringUtils.isEmpty(singDetail)){
			return new ModelAndView(this.orderCreditDetailList)
			.addObject("orderCreditDetails", listMap)
			.addObject("page", page);
		}else{
			return new ModelAndView(this.orderCreditSingleDetail)
			.addObject("orderCreditDetails", listMap)
			.addObject("page", page);
		}
		
	}
	
	/**
	 * @param index the index to set
	 */
	public void setIndex(String index) {
		this.index = index;
	}

	/**
	 * @param orderCreditList the orderCreditList to set
	 */
	public void setOrderCreditList(String orderCreditList) {
		this.orderCreditList = orderCreditList;
	}

	/**
	 * @param orderCreditCoreService the orderCreditCoreService to set
	 */
	public void setOrderCreditCoreService(
			OrderCreditCoreService orderCreditCoreService) {
		this.orderCreditCoreService = orderCreditCoreService;
	}

	/**
	 * @param orderCreditDetailIndex the orderCreditDetailIndex to set
	 */
	public void setOrderCreditDetailIndex(String orderCreditDetailIndex) {
		this.orderCreditDetailIndex = orderCreditDetailIndex;
	}

	/**
	 * @param orderCreditDetailList the orderCreditDetailList to set
	 */
	public void setOrderCreditDetailList(String orderCreditDetailList) {
		this.orderCreditDetailList = orderCreditDetailList;
	}

	/**
	 * @param orderCreditSingleDetail the orderCreditSingleDetail to set
	 */
	public void setOrderCreditSingleDetail(String orderCreditSingleDetail) {
		this.orderCreditSingleDetail = orderCreditSingleDetail;
	}
	
	
}
