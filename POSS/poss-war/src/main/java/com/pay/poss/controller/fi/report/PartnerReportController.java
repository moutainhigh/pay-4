package com.pay.poss.controller.fi.report;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.poss.client.ReportService;
import com.pay.util.StringUtil;

/**
 * 入款通道管理(新)
 * 
 * @author sandy
 *
 */
public class PartnerReportController extends MultiActionController {

	private final Log logger = LogFactory.getLog(PartnerReportController.class);
	private ReportService reportService;
	private String indexView;
	private String resultView;

	/**
	 * 
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) {

		ModelAndView view = new ModelAndView(indexView);

		return view;
	}

	/**
	 * 
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView query(HttpServletRequest request,
			HttpServletResponse response) {

		String tradeOrderNo = request.getParameter("tradeOrderNo");
		String accountNo = request.getParameter("accountNo");
		String partnerId = request.getParameter("partnerId");
		String orderStatus = request.getParameter("orderStatus");
		String payType = request.getParameter("payType");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		Map<String, String> paraMap = new HashMap<String, String>();
		if (!StringUtil.isEmpty(partnerId) && !"chaoyue-pc".equals(partnerId)) {
			paraMap.put("partnerId", "1111");
		}
		if (!StringUtil.isEmpty(accountNo) && !"test@163.com".equals(accountNo)) {
			paraMap.put("partnerId", "1111");
		}

		paraMap.put("tradeOrderNo", tradeOrderNo);
		paraMap.put("orderStatus", orderStatus);
		paraMap.put("startTime", startTime);
		paraMap.put("endTime", endTime);
		paraMap.put("payType", payType);

		List<Map> result = reportService.partnerReportQuery(paraMap);
		return new ModelAndView(resultView).addObject("resultList", result);
	}

	/**
	 * 下载
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public ModelAndView download(HttpServletRequest request,
			HttpServletResponse response) throws FileNotFoundException,
			IOException {

		String tradeOrderNo = request.getParameter("tradeOrderNo");
		String accountNo = request.getParameter("accountNo");
		String partnerId = request.getParameter("partnerId");
		String orderStatus = request.getParameter("orderStatus");
		String payType = request.getParameter("payType");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		Map<String, String> paraMap = new HashMap<String, String>();
		if (!StringUtil.isEmpty(partnerId) && !"chaoyue-pc".equals(partnerId)) {
			paraMap.put("partnerId", "1111");
		}
		if (!StringUtil.isEmpty(accountNo) && !"test@163.com".equals(accountNo)) {
			paraMap.put("partnerId", "1111");
		}

		paraMap.put("tradeOrderNo", tradeOrderNo);
		paraMap.put("orderStatus", orderStatus);
		paraMap.put("startTime", startTime);
		paraMap.put("endTime", endTime);
		paraMap.put("payType", payType);

		List<Map> list = reportService.partnerReportQuery(paraMap);

		String fullPath = request.getSession().getServletContext()
				.getRealPath("/WEB-INF/jsp/report/partner/partnerExcel.xls");
		HSSFWorkbook book;
		OutputStream os = null;
		// 创建workbook
		book = new HSSFWorkbook(new FileInputStream(fullPath));
		HSSFFont fontHei = book.createFont();
		fontHei.setFontHeightInPoints((short) 11);
		fontHei.setFontName("黑体");
		HSSFSheet sheet = book.getSheetAt(0);
		// 取得第一张表
		try {
			// 创建workbook
			int ROWNUM_DATA = 2; // 数据的起始行位置
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			int i = 0;
			for (; i < list.size(); i++) {
				Map record = list.get(i);
				HSSFRow row = sheet.createRow(ROWNUM_DATA + i);
				int j = 0;
				row.createCell(j++).setCellValue(
						String.valueOf(record.get("orderTime")));
				row.createCell(j++).setCellValue("chaoyue-pc");
				row.createCell(j++).setCellValue(
						String.valueOf(record.get("orderId")));
				row.createCell(j++).setCellValue(
						new BigDecimal(
								String.valueOf(record.get("orderAmount")))
								.divide(new BigDecimal("1000")).toString());
				row.createCell(j++).setCellValue("test@163.com");

				String orderAmount = String.valueOf(record.get("orderAmount"));
				String paymentAmount = String.valueOf(record
						.get("paymentAmount"));
				String couponValue = String.valueOf(record.get("couponValue"));
				if (new BigDecimal(orderAmount).longValue() != (new BigDecimal(
						paymentAmount).longValue() + new BigDecimal(couponValue)
						.longValue())) {
					row.createCell(j++).setCellValue("是");
				} else {
					row.createCell(j++).setCellValue("否");
				}

				String vPayType = (String) record.get("payType");
				if ("alipay".equals(vPayType)) {
					row.createCell(j++).setCellValue("支付宝");
				} else {
					row.createCell(j++).setCellValue("微信");
				}

				row.createCell(j++).setCellValue(
						new BigDecimal(paymentAmount).divide(
								new BigDecimal("1000")).toString());

				if (new BigDecimal(couponValue).longValue() != 0) {
					row.createCell(j++).setCellValue("是");
				} else {
					row.createCell(j++).setCellValue("否");
				}

				row.createCell(j++).setCellValue(
						new BigDecimal(couponValue).longValue() / 1000);

			}
			;

			// 设置格式
			// 输出
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.addHeader(
					"Content-Disposition",
					(new StringBuilder("attachment;   filename=\""))
							.append(new String("第三方支付平台对账报表.xls"
									.getBytes("gbk"), "ISO-8859-1"))
							.append("\"").toString());
			os = response.getOutputStream();
			book.write(os);
			os.flush();

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("写excel报错IO", e);
		} finally {
			try {
				if (os != null)
					os.close();
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("关闭流出错", e);
			}
		}
		return null;

	}

	public void setIndexView(String indexView) {
		this.indexView = indexView;
	}

	public void setResultView(String resultView) {
		this.resultView = resultView;
	}

	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

}
