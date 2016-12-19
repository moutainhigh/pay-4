package com.pay.poss.controller.fi.crosspay;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;

public class OrderQueryController extends MultiActionController {

	private static Log log = LogFactory.getLog(OrderQueryController.class);

	private String queryInit;

	private String queryView;

	private String detailView;

	private String excelDownloadView;

	final static String EXCEL_DOWNLOAD_NAME = "商户订单";

	// private CrossPayOrderQueryService crossPayOrderQueryService;

	// private ExpressTrackingService expressTrackingService;

	public void setQueryInit(String queryInit) {
		this.queryInit = queryInit;
	}

	public void setQueryView(String queryView) {
		this.queryView = queryView;
	}

	public void setDetailView(String detailView) {
		this.detailView = detailView;
	}

	public void setExcelDownloadView(String excelDownloadView) {
		this.excelDownloadView = excelDownloadView;
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

	/**
	 * 查询交易信息列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		request.setCharacterEncoding("UTF-8");

		String payer = request.getParameter("payer");
		String cardType = request.getParameter("cardType");
		String tradeOrderNo = request.getParameter("tradeOrderNo");// 交易号
		String tradeStatus = request.getParameter("tradeStatus");// 订单状态
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String orderId = request.getParameter("orderId");// 商户订单号

		ModelAndView mv = new ModelAndView(queryView);

		String card_Type = "";// 卡类型：暂未定义
		if ("all".equals(cardType)) {
			card_Type = "";
		} else if ("1".equals(cardType)) {
			// VISA
		} else if ("2".equals(cardType)) {
			// MASTER
		} else if ("3".equals(cardType)) {
			// JCB
		}

		String orderStatus = "";
		String frozenStatus = "";
		String refuseStatus = "";
		if ("all".equals(tradeStatus)) {

		} else if ("1".equals(tradeStatus)) {// 进行中
			orderStatus = "1";
		} else if ("2".equals(tradeStatus)) {// 交易成功
			orderStatus = "2,4";
		} else if ("3".equals(tradeStatus)) {// 拒付
			refuseStatus = "1";
		} else if ("5".equals(tradeStatus)) {// 退款
			orderStatus = "3";
		} else if ("4".equals(tradeStatus)) {// 冻结
			frozenStatus = "1";
		}

		Map<String, Object> queryDetailPara = new HashMap<String, Object>();
		queryDetailPara.put("payer", payer);
		queryDetailPara.put("card_type", card_Type);
		queryDetailPara.put("tradeOrderNo", tradeOrderNo);
		queryDetailPara.put("startTime", startTime);
		queryDetailPara.put("endTime", endTime);
		queryDetailPara.put("orderId", orderId);
		queryDetailPara.put("orderStatus", orderStatus);
		queryDetailPara.put("refuseStatus", refuseStatus);
		queryDetailPara.put("frozenStatus", frozenStatus);

		Page<Map> page = PageUtils.getPage(request);
		// page =
		// crossPayOrderQueryService.queryOrderListInBackGroundForPage(queryDetailPara,
		// page);

		return mv.addObject("page", page);
	}

	/**
	 * 查询订单信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView viewOrder(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		request.setCharacterEncoding("UTF-8");
		String tradeOrderNo = request.getParameter("tradeOrderNo");
		log.info("订单号tradeOrderNo：" + tradeOrderNo);
		Map<String, String> queryDetailPara = new HashMap<String, String>();
		queryDetailPara.put("tradeOrderNo", tradeOrderNo);
		Map resultMap = new HashMap();
		Map result = null;// crossPayOrderQueryService.queryOrderInBackGround(queryDetailPara);

		// ExpressTracking expressTracking = expressTrackingService
		// .findByTradeOrderNo(tradeOrderNo);
		//
		//
		// resultMap.put("result", result);

		return new ModelAndView(this.detailView, resultMap);
	}

	public ModelAndView excelDownload(HttpServletRequest request,
			HttpServletResponse response) {

		String partnerId = request.getParameter("partnerId");
		String tradeOrderNo = request.getParameter("tradeOrderNo");
		String orderId = request.getParameter("orderId");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String status = request.getParameter("status");

		try {
			String orderStatus = "";
			String frozenStatus = "";
			String refuseStatus = "";
			if ("all".equals(status)) {

			} else if ("1".equals(status)) {// 交易成功
				orderStatus = "2,4";
			} else if ("2".equals(status)) {// 拒付
				refuseStatus = "1";
			} else if ("3".equals(status)) {// 退款
				orderStatus = "3";
			} else if ("4".equals(status)) {// 冻结
				frozenStatus = "1";
			}
			Map<String, String> queryDetailPara = new HashMap<String, String>();
			queryDetailPara.put("partnerId", partnerId);
			queryDetailPara.put("tradeOrderNo", tradeOrderNo);
			queryDetailPara.put("orderId", orderId);
			queryDetailPara.put("startTime", startTime);
			queryDetailPara.put("endTime", endTime);
			queryDetailPara.put("orderStatus", orderStatus);
			queryDetailPara.put("refuseStatus", refuseStatus);
			queryDetailPara.put("frozenStatus", frozenStatus);

			// List<OrderQueryResult> resultList = crossPayOrderQueryService
			// .queryOrderDetailLists(queryDetailPara);
			// for (OrderQueryResult result : resultList) {
			// if ("1".equals(status)) {
			// if ("1".equals(result.getFrozenStatus())) {
			// resultList.remove(result);
			// }
			// if ("1".equals(result.getRefuseStatus())) {
			// resultList.remove(result);
			// }
			// }
			// BigDecimal orderAmount = new BigDecimal(result.getOrderAmount());
			// result.setOrderAmount(orderAmount.divide(new BigDecimal(1000))
			// .setScale(2, BigDecimal.ROUND_HALF_UP).toString());
			// BigDecimal extOrderInfo8 = new BigDecimal(
			// result.getExtOrderInfo8());
			// result.setExtOrderInfo8(extOrderInfo8
			// .divide(new BigDecimal(1000))
			// .setScale(2, BigDecimal.ROUND_HALF_UP).toString());
			// }

			Map<String, Object> paraMap = new HashMap<String, Object>();// 用于设置返回页面参数
			// if (resultList.size() > 10000) {
			// paraMap.put("errorMsg",
			// ExceptionCodeEnum.RECONCILE_EXCEL_DOWNLOAD_ERROR
			// .getDescription());
			// return new ModelAndView(this.queryView).addAllObjects(paraMap)
			// .addObject("nowDate", new Date());
			// }
			//
			// Date nowDate = FormatDate.formatStr(FormatDate.getDay());// 当前时间
			// paraMap.put("sDate", nowDate);// 下载时间
			// paraMap.put("resultList", resultList);
			// paraMap.put("nowDate", nowDate);
			ModelAndView modelAndView = new ModelAndView(excelDownloadView);

			String fileName = EXCEL_DOWNLOAD_NAME;
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
			fileName += format.format(new Date());

			// 设置下载格式
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			response.setHeader("Content-Transfer-Encoding", "binary");
			response.setHeader("Cache-Control",
					"must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Pragma", "public");
			String agent = request.getHeader("User-Agent");
			if (agent.contains("MSIE")) {
				response.setHeader(
						"Content-Disposition",
						"attachment;filename="
								+ URLEncoder.encode(fileName, "UTF-8") + ".xls");
			} else {
				response.setHeader(
						"Content-Disposition",
						"attachment;filename="
								+ new String((fileName + ".xls")
										.getBytes("UTF-8"), "ISO8859_1"));
			}

			modelAndView.addAllObjects(paraMap);

			return modelAndView.addAllObjects(paraMap);

		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView(this.queryView).addObject("errorMsg", "")
					.addObject("nowDate", new Date());
		}

	}
}
