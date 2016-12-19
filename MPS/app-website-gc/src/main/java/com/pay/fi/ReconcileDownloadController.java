package com.pay.fi;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.acc.service.account.constantenum.PseudoAcctTypeEnum;
import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.app.client.TxncoreClientService;
import com.pay.base.model.PseudoAcct;
import com.pay.base.service.acctatrrib.AcctAttribService;
import com.pay.fi.dto.ReconcileOrderDTO;
import com.pay.fi.exception.ExceptionCodeEnum;
import com.pay.fi.reconcile.PartnerReconcileDto;
import com.pay.fi.service.OrderQueryService;
import com.pay.helper.PoiExcelHelper;
import com.pay.util.FormatDate;
import com.pay.util.StringUtil;

/**
 * @Title: ReconcileDownloadController.java
 * @Package com.hnapay.gateway.controller.query
 * @Description: TODO
 * @author chaoyue(foxdog888@gmail.com)
 * @date 2011-4-25 下午05:13:30
 * @version V1.0
 * 
 * 
 */
public class ReconcileDownloadController extends MultiActionController {

	final static String RECONCILE_EXCEL_DOWNLOAD_NAME = "企业对账单";
	private String toIndex;
	private String excelQueryHistoryTradeBusiness;
	private OrderQueryService orderQueryService;
	private TxncoreClientService txncoreClientService;
	private String patnerReconcileFilePath;
	/** 账户属性信息服务 */
	private AcctAttribService acctAttribService;//add by davis.guo 2016-08-30

	public void setAcctAttribService(final AcctAttribService acctAttribService) {
		this.acctAttribService = acctAttribService;
	}

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

	public void setTxncoreClientService(
			TxncoreClientService txncoreClientService) {
		this.txncoreClientService = txncoreClientService;
	}

	public void setPatnerReconcileFilePath(String patnerReconcileFilePath) {
		this.patnerReconcileFilePath = patnerReconcileFilePath;
	}

	/**
	 * 跳转到对账单下载页面
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public ModelAndView index(HttpServletRequest req, HttpServletResponse resp) {
		//add by davis.guo at 2016-08-30
		LoginSession loginSession = SessionHelper.getLoginSession();
		String memberCode = (null == loginSession.getMemberCode() ? "" : loginSession.getMemberCode());
		Map<String, Object> paraMap = new HashMap<String, Object>() ;
		this.extractPseudoAccts(memberCode, paraMap) ;
		paraMap.put("nowDate", new Date()) ;
		return new ModelAndView(toIndex).addAllObjects(paraMap) ;
		//return new ModelAndView(toIndex).addObject("nowDate", new Date());
	}

	/**
	 * 获取账户列表
	 * @author Davis.guo at 2016-08-30
	 * @param memberCode
	 * @param paraMap
	 * @return
	 */
	private List<PseudoAcct> extractPseudoAccts(final String memberCode,
			final Map<String, Object> paraMap) {
		List<PseudoAcct> pseudoAccts = this.acctAttribService.queryAcctCurrencyByMemberCode(Long.valueOf(memberCode)) ;
		if(CollectionUtils.isNotEmpty(pseudoAccts)){
			for(PseudoAcct pseudoAcct : pseudoAccts){
				pseudoAcct.setAcctName(PseudoAcctTypeEnum.getDisplayNameByCurrency(pseudoAcct.getCurrency()));
			}
		}
		paraMap.put("pseudoAccts", pseudoAccts) ;
		return pseudoAccts;
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
			//交易币种
			String currencyCode = request.getParameter("currencyCode");
			
			//交易金额
			String orderAmount = request.getParameter("orderAmount");
			
			
			String dayTime = null;
			String monthTime = null;
			if ("monthCheck".equals(checktime)) {
				monthTime = request.getParameter("monthTime");
			} else {
				dayTime = request.getParameter("dayTime");
			}
			
			//清算币种
//			String settlementCurrencyCode = request.getParameter("settlementCurrencyCode");
			
			//清算币种
			String settlementCurrencyCode =null;
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
			//交易币种
			//paraMap.put("currencyCode", currencyCode);
			//交易金额
			//paraMap.put("orderAmount", orderAmount);
			//清算币种
			paraMap.put("settlementCurrencyCode", settlementCurrencyCode);

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

	/**
	 * 下载对账单
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView download(HttpServletRequest request,
			HttpServletResponse response) {

		OutputStream os = null;
		try {

			String memberCode = (String) request.getSession().getAttribute(
					"memberCode");

			String checktime = request.getParameter("checktime");
			String dayTime = null;
			String monthTime = null;
			String dateTime = null;
			if ("monthCheck".equals(checktime)) {
				monthTime = request.getParameter("monthTime");
				dateTime = monthTime;
			} else {
				dayTime = request.getParameter("dayTime");
				dateTime = dayTime;
			}
			// 设置下载列表内容
			List<PartnerReconcileDto> dataList = txncoreClientService.getReconcileData(memberCode,
					monthTime, dayTime);
			//根据币种账户进行过滤后的数据 add by davis.guo at 2016-08-30 begin
			String account = request.getParameter("account");
			System.out.println("###account="+account);
			List<PartnerReconcileDto> filtedDataList = new ArrayList<PartnerReconcileDto>();
			if(!StringUtil.isEmpty(account) && dataList!=null)
			{
				for(PartnerReconcileDto data : dataList)
				{
					if(account.equalsIgnoreCase(data.getCurrencyCode()))
						filtedDataList.add(data);
				}
			}
			else
			{
				filtedDataList = dataList;
			}
			//end ---------------------------------
			String[] sheetName = { "对账单"};
			String[] titles = null;	
			List<String[]> columnHeaders = new ArrayList<String[]>();
			String[] columnHeader1 = PartnerReconcileDto.columnHeader;
			columnHeaders.add(columnHeader1);

			List<String[]> properties = new ArrayList<String[]>();
			String[] properties1 = PartnerReconcileDto.properties;

			properties.add(properties1);

			String fileName = RECONCILE_EXCEL_DOWNLOAD_NAME;
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
			// fileName += format.format(new Date());
			fileName = fileName + dateTime + ".xlsx";

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

			String filePath = patnerReconcileFilePath;
			if (!patnerReconcileFilePath.endsWith("/")) {
				filePath = patnerReconcileFilePath + "/";
			}

			filePath = filePath + fileName;
			List data = new ArrayList();
			data.add(filtedDataList);// dataList modified by davis.guo at 2016-08-30
			Workbook wb = PoiExcelHelper.writeToFile(titles, sheetName,
					columnHeaders, data, properties);
			
			os = response.getOutputStream();
			wb.write(os);

			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView(toIndex).addObject(
					"errorMsg",
					ExceptionCodeEnum.RECONCILE_EXCEL_DOWNLOAD_ERROR2
							.getDescription()).addObject("nowDate", new Date());
		} finally {
			try {
				if(null != os){
					os.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			os = null;
		}
	}

}
