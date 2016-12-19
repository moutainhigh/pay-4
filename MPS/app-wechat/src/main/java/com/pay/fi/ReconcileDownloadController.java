package com.pay.fi;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.fi.dto.ReconcileOrderDTO;
import com.pay.fi.exception.ExceptionCodeEnum;
import com.pay.fi.service.OrderQueryService;
import com.pay.util.FormatDate;

/**
 * @Title: ReconcileDownloadController.java
 * @Package com.hnapay.gateway.controller.query
 * @Description: TODO
 * @author Gavin_Song(foxdog888@gmail.com)
 * @date 2011-4-25 下午05:13:30
 * @version V1.0
 */
public class ReconcileDownloadController extends MultiActionController {

	final static String RECONCILE_EXCEL_DOWNLOAD_NAME = "企业对账单";
	private String toIndex;
	private String excelQueryHistoryTradeBusiness;
	private OrderQueryService orderQueryService;

	public String getToIndex() {
		return toIndex;
	}

	public void setToIndex(String toIndex) {
		this.toIndex = toIndex;
	}

	public String getExcelQueryHistoryTradeBusiness() {
		return excelQueryHistoryTradeBusiness;
	}

	public void setExcelQueryHistoryTradeBusiness(
			String excelQueryHistoryTradeBusiness) {
		this.excelQueryHistoryTradeBusiness = excelQueryHistoryTradeBusiness;
	}

	public OrderQueryService getOrderQueryService() {
		return orderQueryService;
	}

	public void setOrderQueryService(OrderQueryService orderQueryService) {
		this.orderQueryService = orderQueryService;
	}

	/**
	 * 跳转到对账单下载页面
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public ModelAndView index(HttpServletRequest req, HttpServletResponse resp) {

		return new ModelAndView(toIndex).addObject("nowDate", new Date());
	}

	/**
	 * 导出对账单到excel
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public ModelAndView excelDownload(HttpServletRequest request,
			HttpServletResponse response) {
		try {

			String memberCode = (String) request.getSession().getAttribute(
					"memberCode");

			String checktime = request.getParameter("checktime");
			String dayTime = null;
			String monthTime = null;
			if ("monthCheck".equals(checktime)) {
				monthTime = request.getParameter("monthTime");
			} else {
				dayTime = request.getParameter("dayTime");
			}
			List<ReconcileOrderDTO> list = orderQueryService
					.queryReconcileOrder(memberCode, monthTime, dayTime);

			Map<String, Object> paraMap = new HashMap<String, Object>();// 用于设置返回页面参数
			if (list.size() > 10000) {
				paraMap.put("errorMsg",
						ExceptionCodeEnum.RECONCILE_EXCEL_DOWNLOAD_ERROR
								.getDescription());
				return new ModelAndView(toIndex).addAllObjects(paraMap)
						.addObject("nowDate", new Date());
			}

			ModelAndView modelAndView = new ModelAndView(
					excelQueryHistoryTradeBusiness);
			paraMap.put("sDate", monthTime);// 下载时间
			paraMap.put("reconcileList", list);
			Date nowDate = FormatDate.formatStr(FormatDate.getDay());// 当前时间
			paraMap.put("nowDate", nowDate);

			String fileName = RECONCILE_EXCEL_DOWNLOAD_NAME;
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
			return new ModelAndView(toIndex).addObject(
					"errorMsg",
					ExceptionCodeEnum.RECONCILE_EXCEL_DOWNLOAD_ERROR2
							.getDescription()).addObject("nowDate", new Date());
		}
	}

}
