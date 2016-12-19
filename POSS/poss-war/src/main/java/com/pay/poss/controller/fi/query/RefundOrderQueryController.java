package com.pay.poss.controller.fi.query;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.client.GatewayOrderQueryService;
import com.pay.util.MapUtil;
import com.pay.util.SimpleExcelGenerator;
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
public class RefundOrderQueryController extends MultiActionController {

	private String initView;
	private String listView;
	private String detailView;
	private GatewayOrderQueryService gatewayOrderQueryService;

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
		return new ModelAndView(initView);
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
		Page page = PageUtils.getPage(request);
		Map paraMap = this.request(request, page);
		Map resultMap = gatewayOrderQueryService.queryRefundOrder(paraMap);
		List<Map> refundOrders = (List<Map>) resultMap.get("result");
		Map pageMap = (Map) resultMap.get("page");
		
		//logger.info("refundOrders" + refundOrders);
		page = MapUtil.map2Object(Page.class, pageMap);
		return new ModelAndView(listView).addObject("list", refundOrders )
				.addObject("page", page);

	}
	
	public ModelAndView downloadRefundOrder(HttpServletRequest request,HttpServletResponse response){
			Map paraMap= this.request(request, null);
			Map resultMap = gatewayOrderQueryService.queryRefundOrder(paraMap);
			List<Map> refundOrders = (List<Map>) resultMap.get("result");
			for (Map map : refundOrders) {
				Long refundAmount=0L;
				if(map.get("refundAmount")!=null){
					refundAmount=Long.valueOf(map.get("refundAmount")+"") ;
				}
				map.put("refundAmount", new BigDecimal(refundAmount).divide(new BigDecimal(1000)));
				 String refundType= (String) map.get("refundType");
				 if(refundType.equals("1")){
				map.put("refundType","全部退款");
				 }else if(refundType.equals("2")){
					 map.put("refundType","部分退款");	 
				 }else if(refundType.equals("3")){
					 map.put("refundType","比例退款");
				 }
				String status=(String) map.get("status");
				if(status.equals("0")){
					map.put("status","进行中");	
				}else if(status.equals("1")){
					map.put("status","进行中");	
				}else if(status.equals("2")){
					map.put("status","成功");	
				}else if(status.equals("3")){
					map.put("status","失败");	
				}else if(status.equals("4")){
					map.put("status","超时");	
				}else if(status.equals("5")){
					map.put("status","人工处理");	
				}else if(status.equals("6")){
					map.put("status","失败");	
				}
				
				Integer reconciliationFlg=(Integer) map.get("reconciliationFlg");
				//注意 这里是整数 
				if(reconciliationFlg != null){
					int reconciliationFlgValue = reconciliationFlg.intValue();
					
					if(reconciliationFlgValue==0){
						map.put("reconciliationFlg","未对账");	
					}else if(reconciliationFlgValue==1){
						map.put("reconciliationFlg","已对账");	
					}else if(reconciliationFlgValue==2){
						map.put("reconciliationFlg","对账失败");	
					}else if(reconciliationFlgValue==3){
						map.put("reconciliationFlg","调账");	
					}else {
						map.put("reconciliationFlg","未知");	
					}
				}
				
			    Long creDate= (Long) map.get("createDate");
				   Long comDate= (Long) map.get("complateDate");
				   if(creDate!=null){
					   Date date = new Date(creDate);
					   SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					   map.put("createDate", sd.format(date));
				   }
				   if(comDate!=null){
					   Date dates = new Date(comDate);
					   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					   map.put("complateDate", sdf.format(dates));
				   }
			}
			
		try {
			String[] headers = new String[] { "退款交易号", "网关交易号", "支付交易号", "商户会员号",
					"商户订单号","汇率","退款金额","币种","退款类型","状态","是否对账","创建时间","完成时间"};
			String[] fields = new String[] { "refundOrderNo", "tradeOrderNo", "paymentOrderNo",
					"partnerId", "partnerRefundOrderId","rate","refundAmount","currencyCode","refundType","status","reconciliationFlg","createDate","complateDate"};
			
			Workbook grid = SimpleExcelGenerator.generateGrid("退 款 订 单 管 理",
					headers, fields, refundOrders);
			Date myDate = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
			String dd = sdf.format(myDate);
			response.setContentType("application/force-download");
			response.setContentType("applicationnd.ms-excel;charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment;filename="
					+ dd + ".xls");
			grid.write(response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public Map request(HttpServletRequest request,Page page){
		String partnerId = StringUtil.null2String(request
				.getParameter("partnerId"));
		String paymentOrderNo = StringUtil.null2String(request
				.getParameter("paymentOrderNo"));
		String tradeOrderNo = StringUtil.null2String(request
				.getParameter("tradeOrderNo"));
		String refundOrderNo = StringUtil.null2String(request
				.getParameter("refundOrderNo"));
		String partnerRefundOrderId = StringUtil.null2String(request
				.getParameter("partnerRefundOrderId"));
		String status = StringUtil.null2String(request.getParameter("status"));
		String startTime = StringUtil.null2String(request
				.getParameter("startTime"));
		String endTime = StringUtil
				.null2String(request.getParameter("endTime"));
		String compStartTime = StringUtil.null2String(request
				.getParameter("compStartTime"));
		String compEndTime = StringUtil
				.null2String(request.getParameter("compEndTime"));
		String reconciliationFlg = StringUtil.null2String(request.getParameter("reconciliationFlg"));
		String bankCode= StringUtil.null2String(request.getParameter("bankCode"));
		String ctvReturnNo= StringUtil.null2String(request.getParameter("ctvReturnNo"));
		
		Map paraMap = new HashMap();
		paraMap.put("paymentOrderNo", paymentOrderNo);
		paraMap.put("tradeOrderNo", tradeOrderNo);
		paraMap.put("partnerId", partnerId);
		paraMap.put("refundOrderNo", refundOrderNo);
		paraMap.put("partnerRefundOrderId", partnerRefundOrderId);
		paraMap.put("status", status);
		paraMap.put("beginTime", startTime);
		paraMap.put("endTime", endTime);
		paraMap.put("compStartTime", compStartTime);
		paraMap.put("compEndTime", compEndTime);
		paraMap.put("reconciliationFlg",reconciliationFlg);
		paraMap.put("bankCode", bankCode);
		paraMap.put("ctvReturnNo", ctvReturnNo);
		
		if(page!=null){
			paraMap.put("page", page);
		}
		return paraMap;
	}
	

	public void setInitView(String initView) {
		this.initView = initView;
	}

	public void setListView(String listView) {
		this.listView = listView;
	}

	public void setDetailView(String detailView) {
		this.detailView = detailView;
	}

	public void setGatewayOrderQueryService(
			GatewayOrderQueryService gatewayOrderQueryService) {
		this.gatewayOrderQueryService = gatewayOrderQueryService;
	}

}
