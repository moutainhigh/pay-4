package com.pay.poss.controller.fi.query;

import java.io.IOException;
import java.io.PrintWriter;
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
import com.pay.poss.client.ReportService;
import com.pay.redis.RedisClientTemplate;
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
public class PartnerSettlementOrderQueryController extends
		MultiActionController {

	private String initView;
	private String listView;
	private String detailView;
	private String infoView;
	private GatewayOrderQueryService gatewayOrderQueryService;
	private ReportService reportService;
	private RedisClientTemplate redisClient;

	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
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
		return new ModelAndView(initView);
	}

	/**
	 * 重新清算清算失败的清算订单
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView orderReSettlement(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, String> paraMap = new HashMap<String, String>();

		paraMap.put("settlementFlg", "2");

		Map resultMap = reportService.orderReSettlement(paraMap);

		String responseCode = (String) resultMap.get("responseCode");
		String responseDesc = (String) resultMap.get("responseDesc");

		logger.info("refund responseCode: " + responseCode + " ,responseDesc: "
				+ responseDesc);

		return new ModelAndView(infoView).addObject("msg", responseDesc);
	}

	/**
	 * 清除 正在重新清算 之标志
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView delResettleFlag(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		response.setCharacterEncoding("UTF-8");
		PrintWriter out=null;
		try {
			out = response.getWriter();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		Map resultMap = reportService.delResettleFlag();

		String responseCode = (String) resultMap.get("responseCode");
		String responseDesc = (String) resultMap.get("responseDesc");
		
		out.write(responseDesc);

		logger.info("refund responseCode: " + responseCode + " ,responseDesc: "
				+ responseDesc);
		out.flush();
		out.close();
		return null;
	}
	
	
	/**
	 * 清除 正在重新清算保证金 之标志
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView delResettleAssureFlag(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		response.setCharacterEncoding("UTF-8");
		PrintWriter out=null;
		try {
			out = response.getWriter();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		if (redisClient.exists("reAssureSettleFlag")) {
			redisClient.del("reAssureSettleFlag");
		}

		
		out.write("正在进行重新清算保证金标记锁已清除！");

		out.flush();
		out.close();
		return null;
	}
	/**
	 * 重新保证金清算清算失败的清算订单
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView orderReAssureSettlement(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String responseDesc = null;
		if (redisClient.exists("reAssureSettleFlag")) {			
			responseDesc= "不能进行重新清算操作，因为此时重新清算保证金操作正在执行中！";
		} else {
			redisClient.set("reAssureSettleFlag", "1");

			Map<String, String> paraMap = new HashMap<String, String>();

			paraMap.put("assureSettlementFlg", "2");

			Map resultMap = gatewayOrderQueryService
					.orderReAssureSettlement(paraMap);

			String responseCode = (String) resultMap.get("responseCode");
			responseDesc = "重新清算保证金执行完毕，请重新查询清算订单！";
			redisClient.del("reAssureSettleFlag");
			logger.info("refund responseCode: " + responseCode
					+ " ,responseDesc: " + responseDesc);
		}
		return new ModelAndView(infoView).addObject("msg", responseDesc);
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
		Map resultMap = gatewayOrderQueryService
				.queryPartnerSettlementOrder(paraMap);
		List<Map> channelOrders = (List<Map>) resultMap.get("result");

		Map pageMap = (Map) resultMap.get("page");
		page = MapUtil.map2Object(Page.class, pageMap);
		return new ModelAndView(listView).addObject("list", channelOrders)
				.addObject("page", page);

	}

	public ModelAndView downloadPartnerSettlementOrder(
			HttpServletRequest request, HttpServletResponse response) {
		Map paraMap = this.request(request, null);
		Map resultMap = gatewayOrderQueryService
				.queryPartnerSettlementOrder(paraMap);
		List<Map> channelOrders = (List<Map>) resultMap.get("result");
		for (Map map : channelOrders) {
			Long amount = 0L;
			if (map.get("amount") != null) {
				amount = Long.valueOf(map.get("amount") + "");
			}
			map.put("amount",
					new BigDecimal(amount).divide(new BigDecimal(1000)));
			Long assureAmount = 0L;
			if (map.get("assureAmount") != null) {
				assureAmount = Long.valueOf(map.get("assureAmount") + "");
			}
			if (assureAmount == null) {
				map.put("assureAmount", "0.0");
			} else {
				map.put("assureAmount", new BigDecimal(assureAmount)
						.divide(new BigDecimal(1000)));
			}
			Integer assureSettlementFlg = (Integer) map
					.get("assureSettlementFlg");
			if (assureSettlementFlg == 0) {
				map.put("assureSettlementFlg", "未返还");
			} else if (assureSettlementFlg == 1) {
				map.put("assureSettlementFlg", "已返还");
			} else if (assureSettlementFlg == 2) {
				map.put("assureSettlementFlg", "返还失败");
			} else if (assureSettlementFlg == 3) {
				map.put("assureSettlementFlg", "退款返还");
			}
			Integer fee = (Integer) map.get("fee");
			if (fee == null) {
				map.put("fee", "0.0");
			} else {
				map.put("fee", new BigDecimal(fee).divide(new BigDecimal(1000)));
			}
			Integer settlementFlg = (Integer) map.get("settlementFlg");
			if (settlementFlg == 0) {
				map.put("settlementFlg", "未清算");
			} else if (settlementFlg == 1) {
				map.put("settlementFlg", "清算成功");
			} else if (settlementFlg == 2) {
				map.put("settlementFlg", "清算失败");
			} else if (settlementFlg == 4) {
				map.put("settlementFlg", "已退款");
			}
			Long creDate = (Long) map.get("createDate");
			Long comDate = (Long) map.get("settlementDate");
			if (creDate != null) {
				Date date = new Date(creDate);
				SimpleDateFormat sd = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				map.put("createDate", sd.format(date));
			}
			if (comDate != null) {
				Date dates = new Date(comDate);
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				map.put("settlementDate", sdf.format(dates));
			}
		}
		try {
			String[] headers = new String[] { "清算订单号", "会员号", "网关订单号", "清算金额",
					"交易币种", "清算币种", "保证金", "支付订单号", "清算汇率", "保证金返还", "商户订单号",
					"手续费", "是否清算", "创建时间", "清算时间" };
			String[] fields = new String[] { "id", "partnerId", "tradeOrderNo",
					"amount", "currencyCode", "settlementCurrencyCode",
					"assureAmount", "paymentOrderNo", "settlementRate",
					"assureSettlementFlg", "orderId", "fee", "settlementFlg",
					"createDate", "settlementDate" };
			Workbook grid = SimpleExcelGenerator.generateGrid("清 算 订 单 查 询",
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

	public Map request(HttpServletRequest request, Page page) {
		String paymentOrderNo = StringUtil.null2String(request
				.getParameter("paymentOrderNo"));
		String tradeOrderNo = StringUtil.null2String(request
				.getParameter("tradeOrderNo"));
		String orderId = StringUtil
				.null2String(request.getParameter("orderId"));
		String partnerId = StringUtil.null2String(request
				.getParameter("partnerId"));
		String settlementFlg = StringUtil.null2String(request
				.getParameter("settlementFlg"));
		String assureSettlementFlg = StringUtil.null2String(request
				.getParameter("assureSettlementFlg"));
		String status = StringUtil.null2String(request.getParameter("status"));
		String id = StringUtil.null2String(request.getParameter("id"));
		String startTime = StringUtil.null2String(request
				.getParameter("startTime"));
		String endTime = StringUtil
				.null2String(request.getParameter("endTime"));
		String settleStart = StringUtil.null2String(request
				.getParameter("settleStart"));
		String settleEnd = StringUtil.null2String(request
				.getParameter("settleEnd"));

		Map paraMap = new HashMap();

		paraMap.put("paymentOrderNo", paymentOrderNo);
		paraMap.put("tradeOrderNo", tradeOrderNo);
		paraMap.put("status", status);
		paraMap.put("orderId", orderId);
		paraMap.put("partnerId", partnerId);
		paraMap.put("settlementFlg", settlementFlg);
		paraMap.put("assureSettlementFlg", assureSettlementFlg);
		paraMap.put("id", id);
		paraMap.put("startTime", startTime);
		paraMap.put("endTime", endTime);
		paraMap.put("settleStart", settleStart);
		paraMap.put("settleEnd", settleEnd);
		if (page != null) {
			paraMap.put("page", page);
		}
		paraMap.put("source", "POSS");
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

	public String getInfoView() {
		return infoView;
	}

	public void setInfoView(String infoView) {
		this.infoView = infoView;
	}

	/**
	 * @return the redisClient
	 */
	public RedisClientTemplate getRedisClient() {
		return redisClient;
	}

	/**
	 * @param redisClient
	 *            the redisClient to set
	 */
	public void setRedisClient(RedisClientTemplate redisClient) {
		this.redisClient = redisClient;
	}

}
