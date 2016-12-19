/**
 * 
 */
package com.pay.app.controller.base.paylink;


import gui.ava.html.Html2Image;
import gui.ava.html.util.GenerateHtmlBody;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.app.base.common.pagination.PageUtil;
import com.pay.app.base.session.SessionHelper;
import com.pay.fi.chain.client.PayLinkTransactionService;
import com.pay.fi.service.OrderQueryService;
import com.pay.inf.dao.Page;
import com.pay.util.CurrencyCodeEnum;
import com.pay.util.MapUtil;
import com.pay.util.StringUtil;

/**
 * 支付链交易
 * @author PengJiangbo
 *
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class PayLinkTransactionController extends MultiActionController {

	private static final Log logger = LogFactory.getLog(PayLinkTransactionController.class) ;
	private String indexView ;
	private String refundQueryView ;
	private String detailView ;
	private String refundApply ;
	private String excelDownView ;
	private String refundExcelDownView ;
	
	private PayLinkTransactionService payLinkTransactionService ;
	private OrderQueryService orderQueryService ;
	
	/**
	 * 默认页面
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView index(final HttpServletRequest request, final HttpServletResponse response){
		String queryFlag = StringUtil.null2String(request.getParameter("queryFlag")) ;
		String export = StringUtil.null2String(request.getParameter("export")) ;
		CurrencyCodeEnum[] currencyCodes = CurrencyCodeEnum.values() ;
		if(StringUtils.isEmpty(queryFlag)){
			return new ModelAndView(indexView).addObject("currencyCodes", currencyCodes) ;
		}
		String payLinkNo = StringUtil.null2String(request.getParameter("payLinkNo")) ;
		String tradeOrderNo = StringUtil.null2String(request.getParameter("tradeOrderNo")) ;
		String status = StringUtil.null2String(request.getParameter("status")) ;
		String currencyCode = StringUtil.null2String(request.getParameter("currencyCode")) ;
		String startTime = StringUtil.null2String(request.getParameter("startTime")) ;
		String endTime = StringUtil.null2String(request.getParameter("endTime")) ;
		String billEmail = StringUtil.null2String(request.getParameter("billEmail")) ;
		String memberCode = SessionHelper.getMemeberCodeBySession() ;
		Map paramMap = new HashMap() ;
		paramMap.put("payLinkNo", payLinkNo) ;
		paramMap.put("tradeOrderNo", tradeOrderNo) ;
		paramMap.put("status", status) ;
		paramMap.put("currencyCode", currencyCode) ;
		paramMap.put("partnerId", memberCode) ;
		paramMap.put("startTime", startTime) ;
		paramMap.put("endTime", endTime) ;
		paramMap.put("billEmail", billEmail) ;
		//下载
		if(!StringUtils.isEmpty(export)){
			Map resultMap = this.payLinkTransactionService.downloadPayLinkTransactionOrder(paramMap) ;
			List<Map> transactionOrders = (List<Map>) resultMap.get("result");
			this.sdfDate(transactionOrders, "tradeDate");
			this.setHeader(response, "支付链订单明细.xls");
			return new ModelAndView(excelDownView).addObject("transactionOrders", transactionOrders).addObject("nowDate", new Date()) ;
		}
		// 页面参数
		int pager_offset = 1; // 当前页码
		int page_size = 20; // 每页条数
		pager_offset = request.getParameter("pager_offset") == null ? 1: Integer.parseInt(request.getParameter("pager_offset")); // 获取页面传过来的页码
		if (request.getParameter("numCount") != null
				&& !"".equals(request.getParameter("numCount"))) {// 获取页面传入的每页显示记录条数
			page_size = Integer.parseInt(request.getParameter("numCount"));
			if (page_size == 0)
				page_size = 20;// 此处为避免分页计算时出现被0除的情况
		}
		Page<?> page = new Page() ;
		page.setPageNo(pager_offset);
		page.setPageSize(page_size);
		paramMap.put("page", page) ;
		//查询
		Map resultMap = this.payLinkTransactionService.queryPayLinkTransactionOrder(paramMap) ;
		
		List<Map> transactionOrders = (List<Map>) resultMap.get("result");
		sdfDate(transactionOrders, "tradeDate");
		Map pageMap = (Map) resultMap.get("page");
		page = MapUtil.map2Object(Page.class, pageMap);
		PageUtil pu = new PageUtil(page.getPageNo(), page.getPageSize(), page.getTotalCount()) ;
		Map<String, Object> model = new HashMap<String, Object>() ;
		model.put("transactionOrders", transactionOrders) ;
		model.put("pu", pu) ;
		//查询参数回显
		model.put("payLinkNo", payLinkNo) ;
		model.put("tradeOrderNo", tradeOrderNo) ;
		model.put("status", status) ;
		model.put("currencyCode", currencyCode) ;
		model.put("startTime", startTime) ;
		model.put("endTime", endTime) ;
		model.put("billEmail", billEmail) ;
		model.put("currencyCodes", currencyCodes) ;
		return new ModelAndView(indexView).addAllObjects(model) ;
	}

	/**
	 * @param lists
	 * @param key
	 */
	private void sdfDate(final List<Map> lists, final String key) {
		//日期格式化
		if(CollectionUtils.isNotEmpty(lists)){
			for(Map tm : lists){
				Object object = tm.get(key) ;
				tm.put(key, this.object2StringDate(object)) ;
			}
		}
	}
	private void sdfDate(final List<Map> lists, final String key, final String key2) {
		//日期格式化
		if(CollectionUtils.isNotEmpty(lists)){
			for(Map tm : lists){
				Object object1 = tm.get(key) ;
				Object object2 = tm.get(key2) ;
				tm.put(key, this.object2StringDate(object1)) ;
				tm.put(key2, this.object2StringDate(object2)) ;
			}
		}
	}

	/**
	 * 退款查询
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView refundQuery(final HttpServletRequest request, final HttpServletResponse response){
		String queryFlag = StringUtil.null2String(request.getParameter("queryFlag")) ;
		String exports = StringUtil.null2String(request.getParameter("exports")) ;
		CurrencyCodeEnum[] currencyCodes = CurrencyCodeEnum.values() ;
		if(StringUtils.isEmpty(queryFlag)){
			return new ModelAndView(refundQueryView).addObject("currencyCodes", currencyCodes) ;
		}
		String refundOrderNo = StringUtil.null2String(request.getParameter("refundOrderNo")) ;
		String payLinkNo = StringUtil.null2String(request.getParameter("payLinkNo")) ;
		String tradeOrderNo = StringUtil.null2String(request.getParameter("tradeOrderNo")) ;
		String currencyCode = StringUtil.null2String(request.getParameter("currencyCode")) ;
		String status = StringUtil.null2String(request.getParameter("status")) ;
		//原交易时间
		String startCompleteDate = StringUtil.null2String(request.getParameter("startCompleteDate")) ;
		String endCompleteDate = StringUtil.null2String(request.getParameter("endCompleteDate")) ;
		//创建时间
		String startCreateDate = StringUtil.null2String(request.getParameter("startCreateDate")) ;
		String endCreateDate = StringUtil.null2String(request.getParameter("endCreateDate")) ;
		
		String billEmail = StringUtil.null2String(request.getParameter("billEmail")) ;
		String partnerId = SessionHelper.getMemeberCodeBySession() ;
		Map<String, Object> paraMap = new HashMap<String, Object>() ;
		paraMap.put("partnerId", partnerId) ;
		paraMap.put("refundOrderNo", refundOrderNo) ;
		paraMap.put("payLinkNo", payLinkNo) ;
		paraMap.put("tradeOrderNo", tradeOrderNo) ;
		paraMap.put("currencyCode", currencyCode) ;
		paraMap.put("status", status) ;
		//两个时间
		paraMap.put("startCompleteDate", startCompleteDate) ;
		paraMap.put("endCompleteDate", endCompleteDate) ;
		paraMap.put("startCreateDate", startCreateDate) ;
		paraMap.put("endCreateDate", endCreateDate) ;
		
		paraMap.put("billEmail", billEmail) ;
		//下载
		if(!StringUtils.isEmpty(exports)){
			Map resultMap = this.payLinkTransactionService.payLinkRefundQuery(paraMap) ;
			List<Map> listMap = (List<Map>) resultMap.get("result") ;
			this.sdfDate(listMap, "createDate", "completeDate");
			this.setHeader(response, "支付链交易退款明细.xls");
			return new ModelAndView(refundExcelDownView).addObject("listMap", listMap)
					.addObject("nowDate", new Date()); 
		}
		
		// 页面参数
		int pager_offset = 1; // 当前页码
		int page_size = 20; // 每页条数
		pager_offset = request.getParameter("pager_offset") == null ? 1: Integer.parseInt(request.getParameter("pager_offset")); // 获取页面传过来的页码
		if (request.getParameter("numCount") != null
				&& !"".equals(request.getParameter("numCount"))) {// 获取页面传入的每页显示记录条数
			page_size = Integer.parseInt(request.getParameter("numCount"));
			if (page_size == 0)
				page_size = 20;// 此处为避免分页计算时出现被0除的情况
		}
		Page<?> page = new Page() ;
		page.setPageNo(pager_offset);
		page.setPageSize(page_size);
		paraMap.put("page", page) ;
		//查询
		Map resultMap = this.payLinkTransactionService.payLinkRefundQuery(paraMap) ;
		List<Map> listMap = (List<Map>) resultMap.get("result") ;
		this.sdfDate(listMap, "createDate", "completeDate");
		Map pageMap = (Map) resultMap.get("page") ;
		page = MapUtil.map2Object(Page.class, pageMap) ;
		PageUtil pu = new PageUtil(page.getPageNo(), page.getPageSize(), page.getTotalCount()) ;
		paraMap.put("currencyCodes", currencyCodes) ;
		paraMap.put("listMap", listMap) ;
		paraMap.put("pu", pu) ;
		return new ModelAndView(refundQueryView).addAllObjects(paraMap) ;
	}
	
	/**
	 * 下载邮件模板
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView downMailTemplate(final HttpServletRequest request, final HttpServletResponse response) {
		String payLinkNo = StringUtil.null2String(request.getParameter("payLinkNo")) ;
		String tradeOrderNo = StringUtil.null2String(request.getParameter("tradeOrderNo")) ;
		Map paraMap = new HashMap() ;
		paraMap.put("payLinkNo", payLinkNo) ;
		paraMap.put("tradeOrderNo", tradeOrderNo) ;
		try {
			Map resultMap = this.payLinkTransactionService.mailTemplateDown(paraMap) ;
			List<Map> resultList = (List<Map>) resultMap.get("result") ;
			Map<String, Object> map = null ;
			if(CollectionUtils.isNotEmpty(resultList)){
				map = resultList.get(0) ;
			}else
				map = new HashMap<String, Object>() ;
			final Html2Image html2Image = Html2Image.fromHtml(GenerateHtmlBody.desHtml(resultList, map));
			//html2Image.getImageRenderer().saveImage("html.png");
			BufferedImage bufferedImage = html2Image.getImageRenderer().getBufferedImage() ;
			response.setHeader("Expires", "0");
			response.setHeader("Pragma", "public");
			response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Cache-Control", "public");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("image/x-png");
			response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("PaymentInformation" + ".jpg", "UTF8"));
			OutputStream outputStream = response.getOutputStream() ;
			ImageIO.write(bufferedImage, "png", outputStream) ;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("下载邮件模板出错") ;
			e.printStackTrace();
		}
		return null ;
	}
	/**
	 * 支付链交易详情
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView singleTransactionDetail(final HttpServletRequest request, final HttpServletResponse response){
		String tradeOrderNo = StringUtil.null2String(request.getParameter("tradeOrderNo")) ;
		Map paraMap = new HashMap() ;
		paraMap.put("tradeOrderNo", tradeOrderNo) ;
		Map resultMap = this.payLinkTransactionService.singlePayLinkTransactionDetail(paraMap) ;
		if(null == resultMap){
			resultMap = new HashMap() ;
		}
		resultMap.put("chnOrderCreateDate", this.object2StringDate(resultMap.get("chnOrderCreateDate"))) ;
		resultMap.put("tradeCreateDate", this.object2StringDate(resultMap.get("tradeCreateDate"))) ;
		return new ModelAndView(detailView).addObject("map", resultMap) ;
	}
	/**
	 * 支付链交易退款申请
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView transactionRefund(final HttpServletRequest request, final HttpServletResponse response){
		String memberCode = (String) request.getSession().getAttribute(
				"memberCode");
		// 获取参数
		Map<String, Object> map = new HashMap<String, Object>();
		String serialNo = request.getParameter("serialNo");
		
		map.put("memberCode", memberCode);
		map.put("serialNo", serialNo);
		// 查询
		Map<String, Object> resultMap = orderQueryService
				.querySingleIncomeDetailForRefund(map);
		//singleView.addObject("map", resultMap);
		return new ModelAndView(refundApply).addObject("map", resultMap) ;
	}
	/**
	 * 设置响应头信息
	 * @param response
	 */
	private void setHeader(final HttpServletResponse response, final String title){
		response.setHeader("Expires", "0");
		response.setHeader("Pragma" ,"public");
		response.setHeader("Cache-Control","must-revalidate, post-check=0, pre-check=0");
		response.setHeader("Cache-Control", "public");
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		try {
			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String((title).getBytes("UTF-8"),
							"ISO8859_1"));
		} catch (UnsupportedEncodingException e) {
			if(logger.isErrorEnabled()){
				logger.error("响应头信息设置异常！");
			}
			e.printStackTrace();
		}
	}
	/**
	 * Object转格式化日期字符串
	 * @param object
	 * @return
	 */
	private String object2StringDate(final Object object){
		String destTime = "" ;
		if(null != object && !"".equals(object)){
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;
				destTime = sdf.format(object) ;
			} catch (Exception e) {
				logger.error("源对象转日期字符串异常！");
				e.printStackTrace();
			}
		}
		return destTime ;
	}
//	private  String getDateStr2(final long millis) {
//		Calendar cal = Calendar.getInstance();
//		cal.setTimeInMillis(millis);
//		Date date = cal.getTime();
//		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		return sdf.format(date);
//	}
	
	//---------------------setter
	public void setIndexView(final String indexView) {
		this.indexView = indexView;
	}
	public void setRefundQueryView(final String refundQueryView) {
		this.refundQueryView = refundQueryView;
	}
	public void setDetailView(final String detailView) {
		this.detailView = detailView;
	}
	public void setRefundApply(final String refundApply) {
		this.refundApply = refundApply;
	}
	public void setPayLinkTransactionService(
			final PayLinkTransactionService payLinkTransactionService) {
		this.payLinkTransactionService = payLinkTransactionService;
	}
	public void setExcelDownView(final String excelDownView) {
		this.excelDownView = excelDownView;
	}
	public void setOrderQueryService(final OrderQueryService orderQueryService) {
		this.orderQueryService = orderQueryService;
	}
	public void setRefundExcelDownView(final String refundExcelDownView) {
		this.refundExcelDownView = refundExcelDownView;
	}
	
	
}
