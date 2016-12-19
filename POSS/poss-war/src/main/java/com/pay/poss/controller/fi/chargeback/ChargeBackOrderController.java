package com.pay.poss.controller.fi.chargeback;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.helper.PoiExcelHelper;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.client.ChargeBackService;
import com.pay.poss.client.GatewayOrderQueryService;
import com.pay.poss.controller.fi.dto.ChargeBackOrder;
import com.pay.poss.security.util.SessionUserHolderUtil;
import com.pay.util.CurrencyCodeEnum;
import com.pay.util.MapUtil;
import com.pay.util.StringUtil;

/**
 * 拒付订单管理
 * 
 * @Description
 * @file ChargeBackOrderController
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright 2006-2012 Corporation. All rights reserved. Date
 */
public class ChargeBackOrderController extends MultiActionController {

	final static String RECONCILE_EXCEL_DOWNLOAD_NAME = "拒付订单列表";
	private final Log logger = LogFactory.getLog(getClass());
	private String addView;
	private String auditView;
	private String resultView;
	private String queryInit;
	private String recordList;
	private ChargeBackService chargeBackService;
	private GatewayOrderQueryService gatewayOrderQueryService;

	public void setQueryInit(String queryInit) {
		this.queryInit = queryInit;
	}

	public void setRecordList(String recordList) {
		this.recordList = recordList;
	}

	public void setAuditView(String auditView) {
		this.auditView = auditView;
	}

	public void setResultView(String resultView) {
		this.resultView = resultView;
	}

	public void setAddView(String addView) {
		this.addView = addView;
	}

	public void setChargeBackService(ChargeBackService chargeBackService) {
		this.chargeBackService = chargeBackService;
	}

	public void setGatewayOrderQueryService(
			GatewayOrderQueryService gatewayOrderQueryService) {
		this.gatewayOrderQueryService = gatewayOrderQueryService;
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
		return new ModelAndView(queryInit);
	}

	/****
	 * 充退复核查询拒付交易 判断是否为拒付单
	 * 
	 * @param request
	 * @param response
	 */
	public void handerRefundReAuditBatch(HttpServletRequest request,
			HttpServletResponse response) {
		String handleDatas = request.getParameter("handleDatas");
		String handleStatus = request.getParameter("handlestatus");
		String[] split = handleDatas.split("＃＃");
		List<String> tradeOrderNos=new ArrayList<String>();
		for (int i = 0; i < split.length; i++) {
			String[] data=split[i].split(",");
			tradeOrderNos.add(data[data.length-1]);
		}
		Page page = PageUtils.getPage(request);
		List<Map> channelOrders=new ArrayList<Map>();
		for (String tradeOrderNo : tradeOrderNos) {
		ChargeBackOrder chargeBackCondition = new ChargeBackOrder();
		chargeBackCondition.setTradeOrderNo(Long.parseLong(tradeOrderNo));
		Map resultMap = chargeBackService.queryChargeBackOrder(
				chargeBackCondition, page);
		List<Map> channelOrder = (List<Map>) resultMap.get("result");
			channelOrders.addAll(channelOrder);
		}
		request.setAttribute("channleOrders", channelOrders);
		try {
			request.getRequestDispatcher(//跳转路径 到复核处理
					"./refund.reaudit.do?method=handerRefundReAuditBatch&handleStatus="
							+ handleStatus).forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	
	}

	/***
	 * 充退初审查询拒付交易 判断是否为拒付单
	 * 
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void handerRefundAuditBatch(HttpServletRequest request,
			HttpServletResponse response){
		String handleDatas = request.getParameter("handleDatas");
		String handleStatus = request.getParameter("handlestatus");
		String[] split = handleDatas.split("＃＃");
		List<String> tradeOrderNos=new ArrayList<String>();
		for (int i = 0; i < split.length; i++) {
			String[] data=split[i].split(",");
			tradeOrderNos.add(data[data.length-1]);
		}
		Page page = PageUtils.getPage(request);
		List<Map> channelOrders=new ArrayList<Map>();
		for (String tradeOrderNo : tradeOrderNos) {
			ChargeBackOrder chargeBackCondition = new ChargeBackOrder();
			chargeBackCondition.setTradeOrderNo(Long.parseLong(tradeOrderNo));
			Map resultMap = chargeBackService.queryChargeBackOrder(
					chargeBackCondition, page);
			List<Map> channelOrder = (List<Map>) resultMap.get("result");
			  channelOrders.addAll(channelOrder);
		}
		request.setAttribute("channleOrders", channelOrders);
		try {
			request.getRequestDispatcher(//跳转路径到 初审处理
					"./refund.manage.do?method=handerRefundAuditBatch&handleStatus="
							+ handleStatus).forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * 查询交易信息列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response, ChargeBackOrder chargeBackCondition)
			throws Exception {

		String tradeOrderNo = StringUtil.null2String(request
				.getParameter("tradeOrderNo"));
		String partnerId = StringUtil.null2String(request
				.getParameter("partnerId"));
		Map<String, Object> refusePaymentOrderCriteria = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(tradeOrderNo)) {
			refusePaymentOrderCriteria.put("tradeOrderNo", tradeOrderNo);
		}
		if (StringUtils.isNotBlank(partnerId)) {
			refusePaymentOrderCriteria.put("partnerId", partnerId);
		}

		Page page = PageUtils.getPage(request);

		Map resultMap = chargeBackService.queryChargeBackOrder(
				chargeBackCondition, page);

		List<Map> channelOrders = (List<Map>) resultMap.get("result");

		Map pageMap = (Map) resultMap.get("page");
		page = MapUtil.map2Object(Page.class, pageMap);
		return new ModelAndView(recordList).addObject("list", channelOrders)
				.addObject("page", page);
	}

	/**
	 * 拒付申请
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView addInit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		CurrencyCodeEnum[] currencyCodes = CurrencyCodeEnum.values();

		return new ModelAndView(addView).addObject("currencyCodes",
				currencyCodes);
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView auditInit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String orderId = StringUtil
				.null2String(request.getParameter("orderId"));

		Page page = PageUtils.getPage(request);
		ChargeBackOrder chargeBackOrder = new ChargeBackOrder();
		chargeBackOrder.setOrderId(Long.valueOf(orderId));
		Map map = chargeBackService.queryChargeBackOrder(chargeBackOrder, page);

		List<Map> channelOrders = (List<Map>) map.get("result");

		return new ModelAndView(auditView, channelOrders.get(0));
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView add(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String tradeOrderNo = StringUtil.null2String(request
				.getParameter("tradeOrderNo"));
		String cpType = StringUtil.null2String(request.getParameter("cpType"));
		String amountType = StringUtil.null2String(request
				.getParameter("amountType"));
		String currencyCode = StringUtil.null2String(request
				.getParameter("currencyCode"));
		String chargeBackAmount = StringUtil.null2String(request
				.getParameter("chargeBackAmount"));
		String chargeBackMsg = StringUtil.null2String(request
				.getParameter("chargeBackMsg"));
		String cpdDate = StringUtil
				.null2String(request.getParameter("cpdDate"));
		String oldRefNo = StringUtil.null2String(request
				.getParameter("oldRefNo"));
		String refNo = StringUtil.null2String(request.getParameter("refNo"));
		String operator = SessionUserHolderUtil.getLoginId();
		
		logger.info("tradeOrderNo:" + tradeOrderNo + ",cpType:" + cpType
				+ ",amountType:" + amountType + ",chargeBackAmount:"
				+ chargeBackAmount);

		List<ChargeBackOrder> chargeBackOrders = new ArrayList<ChargeBackOrder>();
		ChargeBackOrder chargeBackOrder = new ChargeBackOrder();
		chargeBackOrder.setTradeOrderNo(Long.valueOf(tradeOrderNo));
		chargeBackOrder.setOperator(operator);
		chargeBackOrder.setCpType(Integer.valueOf(cpType));
		chargeBackOrder.setChargeBackAmount(chargeBackAmount);
		chargeBackOrder.setChargeBackMsg(chargeBackMsg);
		chargeBackOrder.setCpdDate(cpdDate);
		chargeBackOrder.setOldRefNo(oldRefNo);
		chargeBackOrder.setCurrencyCode(currencyCode);
		chargeBackOrder.setRefNo(refNo);
		chargeBackOrder.setAmountType(amountType);
		chargeBackOrders.add(chargeBackOrder);
		Map<String, String> resultMap = chargeBackService
				.addChargeBackOrder(chargeBackOrders);
		String resultCode = resultMap.get("responseCode");
		String resultMsg = resultMap.get("responseDesc");

		return new ModelAndView(resultView).addObject("resultCode", resultCode)
				.addObject("resultMsg", resultMsg);
	}

	/**
	 * 拒付审核
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView audit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String[] orderIds = request.getParameterValues("orderId");
		if (orderIds == null) {
			String orderId = request.getParameter("orderId");
			orderIds = new String[1];
			orderIds[0] = orderId;
		}

		String cpType = StringUtil.null2String(request.getParameter("cpType"));

		String status = StringUtil.null2String(request.getParameter("status"));
		String auditMsg = StringUtil.null2String(request
				.getParameter("auditMsg"));

		String operator = SessionUserHolderUtil.getLoginId();

		List<ChargeBackOrder> chargeBackOrders = new ArrayList<ChargeBackOrder>();
		for(String orderId : orderIds){
			ChargeBackOrder chargeBackOrder = new ChargeBackOrder();
			chargeBackOrder.setOperator(operator);
			chargeBackOrder.setCpType(Integer.valueOf(cpType));
			chargeBackOrder.setStatus(Integer.valueOf(status));
			chargeBackOrder.setOrderId(Long.valueOf(orderId));
			chargeBackOrder.setAuditOperator(operator);
			chargeBackOrder.setAuditMsg(auditMsg);
			chargeBackOrders.add(chargeBackOrder);
		}
		
		Map<String, String> resultMap = chargeBackService
				.updateChargeBackOrder(chargeBackOrders,null);
		String resultCode = resultMap.get("responseCode");
		String resultMsg = resultMap.get("responseDesc");

		return new ModelAndView(resultView).addObject("resultCode", resultCode)
				.addObject("resultMsg", resultMsg);
	}

	/**
	 * 下载拒付订单
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView download(HttpServletRequest request,
			HttpServletResponse response, ChargeBackOrder chargeBackCondition) {

		OutputStream os = null;
		try {

			Map resultMap = chargeBackService.queryChargeBackOrder(
					chargeBackCondition, null);

			List<Map> listMap = (List<Map>) resultMap.get("result");

			List<ChargeBackOrder> chargeBackorders = MapUtil.map2List(
					ChargeBackOrder.class, listMap);

			String[] sheetName = { "拒付订单列表" };
			String[] titles = { "拒付订单列表", };

			List<String[]> columnHeaders = new ArrayList<String[]>();
			String[] columnHeader1 = ChargeBackOrder.columnHeader;
			columnHeaders.add(columnHeader1);

			List<String[]> properties = new ArrayList<String[]>();
			String[] properties1 = ChargeBackOrder.properties;
			properties.add(properties1);

			String fileName = RECONCILE_EXCEL_DOWNLOAD_NAME;
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
			// fileName += format.format(new Date());
			fileName = fileName + ".xlsx";

			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.reset();
			response.setHeader("Expires", "0");
			response.setHeader("Pragma", "public");
			response.setHeader("Cache-Control",
					"must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Cache-Control", "public");
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.addHeader("Content-Disposition", "attachment; filename="
					+ URLEncoder.encode(fileName, "UTF8"));
			response.setBufferSize(1024);

			List listData = new ArrayList();
			listData.add(chargeBackorders);
			Workbook wb = PoiExcelHelper.writeToFile(titles, sheetName,
					columnHeaders, listData, properties);

			os = response.getOutputStream();
			wb.write(os);

			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			os = null;
		}
	}

	/**
	 * 拒付录入检查逻辑
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView checkPartRefund(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String tradeOrderNo = StringUtil.null2String(request
				.getParameter("tradeOrderNo"));

		Map paraMap = new HashMap();
		paraMap.put("tradeOrderNo", tradeOrderNo);

		Map resultMap = gatewayOrderQueryService.queryRefundOrder(paraMap);
		List<Map> refundOrders = (List<Map>) resultMap.get("result");
		Long refundAmount = 0L;
		String currencyCode = "";
		if (refundOrders != null && !refundOrders.isEmpty()) {
			for (Map map : refundOrders) {
				if ("1".equals(map.get("status"))
						|| "2".equals(map.get("status"))) {
					refundAmount += Long.valueOf(map.get("refundAmount") + "");
					currencyCode = map.get("currencyCode") + "";
				}
			}
		}

		response.getWriter().print(refundAmount/1000.00 +" "+ currencyCode);
		return null;
	}

}
