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
import com.pay.poss.client.ChannelClientService;
import com.pay.poss.client.GatewayOrderQueryService;
import com.pay.poss.dto.PaymentChannelItemDto;
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
 * @version 1.0 Copyright 2006-2012 ipayLinks Corporation. All rights reserved.
 *          Date
 */
public class ChannelOrderQueryController extends MultiActionController {

	private String initView;
	private String listView;
	private String detailView;
	private ChannelClientService channelClientService;
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
		PaymentChannelItemDto paymentChannelItem = new PaymentChannelItemDto();
		List paymentChannelItems = channelClientService
				.queryChannelItem(paymentChannelItem);
		return new ModelAndView(initView).addObject("paymentChannelItems",
				paymentChannelItems);
	}

	/**
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Page page = PageUtils.getPage(request);
		Map paraMap = request(request,page);
		Map resultMap = gatewayOrderQueryService.queryChannelOrder(paraMap);
		List<Map> channelOrders = (List<Map>) resultMap.get("result");
		
		Map pageMap = (Map) resultMap.get("page");
		page = MapUtil.map2Object(Page.class, pageMap);
		return new ModelAndView(listView).addObject("list", channelOrders)
				.addObject("page", page);

	}
	
	
	
	public Map request(HttpServletRequest request,Page page){
		
		String orgCode = StringUtil
				.null2String(request.getParameter("orgCode"));
		String paymentOrderNo = StringUtil.null2String(request
				.getParameter("paymentOrderNo"));
		String channelOrderNo = StringUtil.null2String(request
				.getParameter("channelOrderNo"));
		String status = StringUtil.null2String(request.getParameter("status"));
		String startTime = StringUtil.null2String(request
				.getParameter("startTime"));
		String endTime = StringUtil
				.null2String(request.getParameter("endTime"));
		String reconciliationFlg = StringUtil.null2String(request.getParameter("reconciliationFlg"));
		String authorisation = StringUtil.null2String(request.getParameter("authorisation")) ;
		String partnerId = StringUtil.null2String(request.getParameter("partnerId")) ;
		String tradeOrderNo = StringUtil.null2String(request.getParameter("tradeOrderNo")) ;
		
		Map paraMap = new HashMap();

		paraMap.put("reconciliationFlg", reconciliationFlg);
		paraMap.put("paymentOrderNo", paymentOrderNo);
		paraMap.put("channelOrderNo", channelOrderNo);
		paraMap.put("status", status);
		paraMap.put("beginTime", startTime);
		paraMap.put("endTime", endTime);
		paraMap.put("orgCode", orgCode);
		paraMap.put("authorisation", authorisation) ;
		paraMap.put("partnerId", partnerId) ;
		paraMap.put("tradeOrderNo", tradeOrderNo) ;
		if(page!=null){
			paraMap.put("page", page);
		}
		
		return paraMap;
	}
	
	/**
	 
	 * 
	 *下载渠道订单
	 * */
	public ModelAndView downloadChannel(HttpServletRequest request,HttpServletResponse response){
		Map paraMap = request(request,null);
		Map resultMap = gatewayOrderQueryService.queryChannelOrder(paraMap);
		List<Map> channelOrders = (List<Map>) resultMap.get("result");
			for (Map map : channelOrders) {
			  String orgCode=(String) map.get("orgCode");
				if(orgCode.equals("0000000")){
					map.put("orgCode", "测试通道");
				}else
				if(orgCode.equals("10076001")){
					map.put("orgCode", "中银卡司");
				}else if(orgCode.equals("10075001")){
					map.put("orgCode", "CREDORAX");
				}else if(orgCode.equals("10003001")){
					map.put("orgCode", "中国银行");
				}else if(orgCode.equals("10002001")){
					map.put("orgCode", "农业银行");
				}
				Long amount=Long.valueOf(map.get("amount")+"") ;
				if(amount!=null){
					BigDecimal divide = new BigDecimal(amount).divide(new BigDecimal(1000));
					map.put("amount",divide);
				}
				Long payAmount=0L;
				if(map.get("payAmount")!=null){
					 payAmount= Long.valueOf(map.get("payAmount")+"") ;
				}
				if(payAmount!=null){
					BigDecimal divide2 = new BigDecimal(payAmount).divide(new BigDecimal(1000));
					map.put("payAmount", divide2);
				}
				Integer status=(Integer) map.get("status");
			    if(status.equals(0)){
			    	map.put("status", "进行中");	
			    }else  if(status.equals(1)){
			    	map.put("status", "成功");
			    }else if(status.equals(2)){
			    	map.put("status", "失败");
			    }
			   Long creDate= (Long) map.get("createDate");
			   Long comDate= (Long) map.get("completeDate");
			   if(creDate!=null){
				   Date date = new Date(creDate);
				   SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				   map.put("createDate", sd.format(date));
			   }
			   if(comDate!=null){
				   Date dates = new Date(comDate);
				   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				   map.put("completeDate", sdf.format(dates));
			   }
			   Integer reconciliationFlg = (Integer) map.get("reconciliationFlg");
			   if(reconciliationFlg.equals(1)){
				map.put("reconciliationFlg", "已对账");
			   }else if(reconciliationFlg.equals(0)){
				   map.put("reconciliationFlg", "未对账");  
			   }
			}
		
		try {
			String[] headers = new String[] { "渠道订单号", "银行机构", "支付订单号", "二级商户号",
					"授权码","会员号","网关订单号","交易金额","交易币种","支付金额","支付币种","支付汇率","状态",
					"创建时间","完成时间","是否对账","结算汇率","返回码","返回消息"};
			String[] fields = new String[] { "channelOrderNo", "orgCode", "paymentOrderNo",
					"merchantNo", "authorisation","partnerId","tradeOrderNo","amount","currencyCode","payAmount","transferCurrencyCode","transferRate",
					"status","createDate","completeDate","reconciliationFlg","settlementRate","errorCode","errorMsg"};
			Workbook grid = SimpleExcelGenerator.generateGrid("渠道订单管理",
					headers, fields, channelOrders);
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

	public void setChannelClientService(
			ChannelClientService channelClientService) {
		this.channelClientService = channelClientService;
	}

	
}
