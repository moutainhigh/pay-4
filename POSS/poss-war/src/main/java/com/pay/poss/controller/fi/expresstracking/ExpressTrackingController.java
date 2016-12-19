package com.pay.poss.controller.fi.expresstracking;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.client.ExpressTrackingService;
import com.pay.poss.dto.ExpressTracking;
import com.pay.poss.security.util.SessionUserHolderUtil;
import com.pay.util.MapUtil;
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
public class ExpressTrackingController extends MultiActionController {

	private final Log logger = LogFactory.getLog(getClass());
	private String queryInit;
	private String auditView;
	private String recordList;
	private String reviewed;
	private ExpressTrackingService expressTrackingService;

	public void setQueryInit(String queryInit) {
		this.queryInit = queryInit;
	}
	
	public void setReviewed(String reviewed) {
		this.reviewed = reviewed;
	}


	public void setAuditView(String auditView) {
		this.auditView = auditView;
	}

	public void setRecordList(String recordList) {
		this.recordList = recordList;
	}

	public void setExpressTrackingService(
			ExpressTrackingService expressTrackingService) {
		this.expressTrackingService = expressTrackingService;
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
			HttpServletResponse response, ExpressTracking expressTracking) throws Exception {
		String uploadBeginTime = expressTracking.getUploadBeginTime();
		String uploadEndTime = expressTracking.getUploadEndTime();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		if(StringUtils.isNotEmpty(uploadEndTime)){ //日期装换格式 转成DATE类型
			Date uploadEndDate = sdf.parse(uploadEndTime);//上传时间结束
			expressTracking.setUploadEndDate(uploadEndDate);
		}
		if(StringUtils.isNotEmpty(uploadBeginTime)){ 
			Date uploadBeginDate = sdf.parse(uploadBeginTime);//上传时间开始
			expressTracking.setUploadBeginDate(uploadBeginDate);
		}
		return new ModelAndView(queryInit).addObject("expressTracking", expressTracking);
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

		String targetCurrency = request.getParameter("targetCurrency");
		String currencyUnit = request.getParameter("currencyUnit");

		String operator = SessionUserHolderUtil.getLoginId();
		List<ExpressTracking> list = new ArrayList<ExpressTracking>();
		Map<String, String> resultMap = expressTrackingService
				.expressTrackingUpdate(list, operator);

		if (logger.isInfoEnabled()) {
			logger.info("response:" + resultMap);
		}

		return new ModelAndView(queryInit, resultMap);

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
			HttpServletResponse response, ExpressTracking expressTracking)
			throws Exception {
		Page page = PageUtils.getPage(request);
			expressTracking.setPage(page);
		Map result = expressTrackingService
				.expressTrackingQuery(expressTracking);
		List<Map> returnMap=(List<Map>) result.get("list");	
		Map pageMap = (Map) result.get("page");
		page = MapUtil.map2Object(Page.class, pageMap);
		return new ModelAndView(recordList).addObject("list", returnMap).addObject("page", page);
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView toAudit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ModelAndView view = new ModelAndView(auditView);
		String tradeOrderNo = request.getParameter("tradeOrderNo");
		String trackingNo = request.getParameter("trackingNo");
		String expressCom = request.getParameter("expressCom");
		String queryUrl = request.getParameter("queryUrl");
		String orderId = request.getParameter("orderId");

		view.addObject("tradeOrderNo", tradeOrderNo);
		view.addObject("trackingNo", trackingNo);
		view.addObject("expressCom", expressCom);
		view.addObject("queryUrl", queryUrl);
		view.addObject("orderId", orderId);

		return view;
	}

	public ModelAndView checkExpress(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String tradeOrderNo = StringUtil.null2String(request
				.getParameter("tradeOrderNo"));// 交易号
		String remark = StringUtil.null2String(request
				.getParameter("remark"));// 审核原因
		String status = StringUtil.null2String(request.getParameter("status"));// 审查结果（0：通过，1：审查不通过）；

		String orderId = StringUtil.null2String(request
				.getParameter("orderId"));
		List<ExpressTracking> expressTrackings = new ArrayList<ExpressTracking>();

		ExpressTracking expressTracking = new ExpressTracking();
		if (StringUtils.isNotBlank(tradeOrderNo)) {
			expressTracking.setTradeOrderNo(Long.valueOf(tradeOrderNo));
		}
		if (StringUtils.isNotBlank(remark)) {
			expressTracking.setRemark(remark);
		}
		expressTracking.setOrderId(orderId);
		expressTracking.setStatus(status);
		String operator = SessionUserHolderUtil.getLoginId();
		expressTracking.setOperator(operator);
		expressTrackings.add(expressTracking);
		expressTrackingService
				.expressTrackingUpdate(expressTrackings, operator);
		return null;
	}
	
	/**
	 * 批量审核运单页面
	 * @author delin.dong
	 * @date 2016年6月4日 16:50:14
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public ModelAndView bacthReviewed(HttpServletRequest request,HttpServletResponse response, ExpressTracking expressTracking) throws UnsupportedEncodingException{
		 request.setCharacterEncoding("UTF-8");
		 String expressCom=request.getParameter("expressCom");
		 expressTracking.setExpressCom(expressCom);
		String ids=request.getParameter("ids");
		String[] split = ids.split(",");
		return new ModelAndView(reviewed).addObject("ids", ids).addObject("count", split.length).addObject("expressTracking", expressTracking);
	}
	
	/**
	 * 批量审核运单
	 * @author delin.dong
	 * @date 2016年6月4日17:22:55
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	public ModelAndView updateBacthSiteSetStatus(HttpServletRequest request,HttpServletResponse response) throws IOException{
			String ids = request.getParameter("ids");
			String status=request.getParameter("status1");
			String remark=request.getParameter("remark");
			String operator = SessionUserHolderUtil.getLoginId();	
			List<ExpressTracking> expressTrackings = new ArrayList<ExpressTracking>();
			String[] split = ids.split(",");
			for (String id: split) {
				ExpressTracking expressTracking = new ExpressTracking();
				expressTracking.setStatus(status);
				if (StringUtils.isNotBlank(remark)) {
					expressTracking.setRemark(remark);
				}
				expressTracking.setOperator(operator);
				expressTracking.setId(Long.valueOf(id));
				expressTrackings.add(expressTracking);
			}
		Map<String,String> map=expressTrackingService.expressTrackingUpdate(expressTrackings, operator);
		String responseCode= map.get("responseCode");
		if(responseCode.equals("0000")){
			response.getWriter().print(1);
		}
		return null;
	}
	
	/***
	 * 工作面板中需要统计待审核运单的数量
	 * @date 2016年6月8日13:54:56
	 * @author delin.dong
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	public ModelAndView workPanels(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map<String,String> map=new HashMap<String,String>();
		map.put("status", "1");
		Map pendingMap=expressTrackingService.countExpByStatus(map);
		String pendingCount=String.valueOf(pendingMap.get("pendingCount"));
		response.getWriter().print(pendingCount);
		return  null;
	}
}
