package com.pay.poss.report.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.report.dto.TranDailyReportDto;
import com.pay.poss.report.dto.TranDailyReportResultDto;
import com.pay.poss.report.service.QueryFinancialReportService;
import com.pay.util.CurrencyCodeEnum;
import com.pay.util.SimpleExcelGenerator;
import com.pay.util.StringUtil;

/**
 * 交易日报表
 * 
 * @Description
 * @file FinancialReportController
 * @author mingmingZhang
 * @develop 
 * @version 
 */
public class FinancialReportController extends MultiActionController {

	//交易日报表明细列表页面
	private String detailListView;
	
	//交易日报表汇总列表页面
	private String sumListView ;

	private String toView;

	private QueryFinancialReportService queryFinancialReportService;

	public void setToView(String toView) {
		this.toView = toView;
	}

	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Calendar time = Calendar.getInstance();
		time.add(Calendar.DATE, -1);
		Date startDate = time.getTime();
		Date endDate = startDate;
		CurrencyCodeEnum[] currencyCodes = CurrencyCodeEnum.values() ;
		return new ModelAndView(toView)
				.addObject("startDate", startDate)
				.addObject("endDate", endDate)
				.addObject("currencyCodes", currencyCodes);
	}

	public ModelAndView queryTranDailyReportFlow(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TranDailyReportDto reportvo = new TranDailyReportDto();
		String beginTime = StringUtil.null2String(request.getParameter("startDate"));
		String endTime = StringUtil.null2String(request.getParameter("endDate"));
		String payType = StringUtil.null2String(request.getParameter("payType")) ;
		String optType = StringUtil.null2String(request.getParameter("optType"));
		String partnerId = StringUtil.null2String(request.getParameter("partnerId"));
		String currencyCode = StringUtil.null2String(request.getParameter("currencyCode")) ;
		String payCurrencyCode = StringUtil.null2String(request.getParameter("payCurrencyCode")) ;
		String orgCode = StringUtil.null2String(request.getParameter("orgCode"));
		
		if (!StringUtil.isEmpty(beginTime)) {
			beginTime=beginTime.trim();
		}
		if (!StringUtil.isEmpty(endTime)) {
			endTime=endTime.trim();
		}
		if(StringUtils.isNotEmpty(payType)){
			reportvo.setPayType(payType);
		}
		
		if (!StringUtil.isEmpty(partnerId)) {
			reportvo.setPartnerId(Long.valueOf(partnerId));
		}
		if(StringUtils.isNotEmpty(currencyCode)){
			reportvo.setCurrencyCode(currencyCode);
		}
		if(StringUtils.isNotEmpty(payCurrencyCode)){
			reportvo.setPayCurrencyCode(payCurrencyCode);
		}
		if (!StringUtil.isEmpty(orgCode)) {
			reportvo.setOrgCode(orgCode);
		}
		
		Page<TranDailyReportResultDto> page = PageUtils.getPage(request);
		List<TranDailyReportResultDto> result = new ArrayList<TranDailyReportResultDto>();
		String export = StringUtil.null2String(request.getParameter("export"));

		// 明细查询
		if (StringUtils.isEmpty(optType)
				|| "1".equalsIgnoreCase(optType.trim())) {
			//交易日报表下载
			if (!StringUtils.isEmpty(export)) {
				result = queryFinancialReportService.queryTranDailyReportDetail(beginTime, endTime, reportvo);
				try {
					String[] headers = new String[] { "交易时间", "商户订单号", "网关订单号", "支付订单号","渠道订单号","渠道号","所属渠道","商户号",
							"交易金额","交易币种","交易汇率","支付金额","支付币种","清算币种","清算汇率","手续费汇率",
							"二级渠道号","授权码","渠道结果","渠道支付金额","渠道支付币种","银行比例手续费","银行比例手续费币种","银行固定手续费",
							"银行固定手续费币种","银行存款入账","银行存款入账币种","支付币种兑CNY汇率","渠道支付金额（CNY）","银行固定手续费（CNY）"
							,"银行比例手续费（CNY）","清算金额","清算币种","基本户","保证金","比例费","固定费",
							"清算币种兑CNY汇率","清算金额（CNY)","基本户（CNY)","保证金（CNY)","比例费（CNY)",
							"固定费（CNY)","汇差","汇差收益率","总收入","毛利","毛利率","交易日期","清算日期","数据有效性","是否DCC"};
					String[] fields = new String[] { "createTime", "orderId", "tradeOrderNo","paymentOrderNo","channelOrderNo","orgCode","orgName","partnerId",
							"tranAmount","tranCurrencyCode","payRate","payAmount","payCurrencyCode","settlementCurrencyCode","settlementRate","feeRate",
							"merchantNo","authorisation","status","payAmount","payCurrencyCode","bankPerFee","bankPerCurrencyCode","bankFixedFee",
							"bankFixedCurrencyCode","bankAmount","bankCurrencyCode","payCnyRate","bankPayAmountCny","bankFixedFeeCny",
							"bankPerFeeCny","settlementAmount","settlementCurrencyCode","baseAmount","assureAmount","perFee","fixedFee"
							,"settlementCnyRate","settlementAmountCny","baseAmountCny","assureAmountCny","perFeeCny",
							"fixedFeeCny","rateIncome","rateRate","totalIncome","profit","profitRate","createDate","settlementDate","flag","payType"};
					Workbook grid = SimpleExcelGenerator.generateGrid("交易日报表明细下载",
							headers, fields, result);
					response.setContentType("application/force-download");
					response.setContentType("applicationnd.ms-excel;charset=UTF-8");
					response.setHeader("Content-Disposition", "attachment;filename="
							+ new String("交易日报表明细下载".getBytes("UTF-8"), "ISO-8859-1") + ".xls");
					grid.write(response.getOutputStream());
				} catch (Exception e) {
					logger.info("交易日报表明细下载出错了！");
					e.printStackTrace();
				}
				return null ;
			} else {
				//交易日报表查询
				page = queryFinancialReportService.queryTranDailyReportDetail(page, beginTime, endTime, reportvo);
				return new ModelAndView(this.detailListView).addObject("page", page)
						.addObject("optType", 1);
			}
		} else {// 汇总查询
			if (!StringUtils.isEmpty(export)) {
				result = queryFinancialReportService.queryTranDailyReport(beginTime, endTime, reportvo);
				try {
					String[] headers = new String[] { "交易类型", "渠道号", "日期", "会员号","笔数","渠道支付币种","渠道支付金额",
							"手续费支出","银行存款入账","清算币种","清算金额","基本户清算","保证金清算","比例费收入","固定费收入","汇差收入",
							"总收入","毛利","毛利率"};
					String[] fields = new String[] { "payType", "orgCode", "createDate","partnerId","tranCount","payCurrencyCode","payAmount",
							"tranFee","bankAmount","settlementCurrencyCode","settlementAmount","baseAmount","assureAmount","perFee","fixedFee","rateIncome",
							"totalIncome","profit","profitRate"};
					Workbook grid = SimpleExcelGenerator.generateGrid("交易日报表明细下载",
							headers, fields, result);
					response.setContentType("application/force-download");
					response.setContentType("applicationnd.ms-excel;charset=UTF-8");
					response.setHeader("Content-Disposition", "attachment;filename="
							+ new String("交易日报表总汇".getBytes("UTF-8"), "ISO-8859-1") + ".xls");
					grid.write(response.getOutputStream());
				} catch (Exception e) {
					logger.info("交易日报表明细下载出错了！");
					e.printStackTrace();
				}
				return null ;
			} else {
				page = queryFinancialReportService.queryTranDailyReport(page, beginTime, endTime, reportvo);
				return new ModelAndView(this.sumListView).addObject("page", page)
						.addObject("optType", 2);
			}
		}
	}

	public QueryFinancialReportService getQueryFinancialReportService() {
		return queryFinancialReportService;
	}

	public void setQueryFinancialReportService(
			QueryFinancialReportService queryFinancialReportService) {
		this.queryFinancialReportService = queryFinancialReportService;
	}

	public String getToView() {
		return toView;
	}


	/**
	 * @param detailListView the detailListView to set
	 */
	public void setDetailListView(String detailListView) {
		this.detailListView = detailListView;
	}

	/**
	 * @param sumListView the sumListView to set
	 */
	public void setSumListView(String sumListView) {
		this.sumListView = sumListView;
	}
	
	
	

}
