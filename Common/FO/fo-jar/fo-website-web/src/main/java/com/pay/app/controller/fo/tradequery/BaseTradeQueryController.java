/**
 *  File: TradeSummaryController.java
 *  Description:交易查询父类
 *  Date        Author      Changes
 *  2011-4-11   Sandy 		create
 *  
 *
 */
package com.pay.app.controller.fo.tradequery;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.app.base.api.annotation.HasFeature;
import com.pay.app.base.api.common.constans.CutsConstants;
import com.pay.util.DateUtil;

/**
 * 交易查询父类
 * 
 * @author Sandy
 */
@HasFeature({ CutsConstants.FEATURE_MAXTRIX, CutsConstants.FEATURE_NORMAL })
public class BaseTradeQueryController extends MultiActionController {

	protected transient Log log = LogFactory.getLog(getClass());

	private static Pattern reg = Pattern.compile("^[0-9]+([.]{1}[0-9]{1,})?$");
	/** 每页显示记录数 **/
	protected int pageSize;

	/** 个人收支汇总 **/
	protected String tradeInfoDetail;
	protected String exportTradeInfoDetail;
	/** 个人收支汇总单笔明细 **/
	protected String singleTradeInfoDetail;
	/** 个人提现记录 **/
	protected String withdrawInfoDetail;
	protected String exportWithdrawInfoDetail;
	/** 个人提现记录单笔明细 **/
	protected String singleWithdrawInfoDetail;

	/** 收入汇总页面-企业 **/
	protected String incomeDetailCorp;
	/** 导出收入汇总页面-企业 **/
	protected String exportincomeDetailCorp;
	/** 收支详情页面-企业 **/
	protected String singleIncomeDetailCorp;
	/** 单笔付款汇总页面--企业 **/
	protected String payDetailCorp;
	protected String exportPayDetailCorp;
	protected String exportPayDetailCorpCsv;
	/** 单笔付款详情页面--企业 **/
	protected String singlePayDetailCorp;
	/** 批量付款汇总页面--企业 **/
	protected String payBatchDetailCorp;
	protected String exportPayBatchDetailCorp;
	protected String exportPayBatchDetailCorpCsv;
	/** 批量付款详情页面--企业 **/
	protected String singlePayBatchDetailCorp;
	/** 账户收款--企业 **/
	protected String receiptRecordDetailCorp;
	protected String exportReceiptRecordDetailCorp;

	/** BSP提现列表 **/
	protected String bspWithdrawListCorp;
	/** BSP提现详情 **/
	protected String bspWithdrawDetailCorp;

	/** 资金调拨详情 **/
	protected String fundAllotDetailCorp;
	/** 付款到银行凭证 **/
	protected String payToBankCertificate;

	/** 批量付款到银行凭证 **/
	protected String payToBankBatchCertificate;

	/** 批量付款详细 **/
	protected String payToBankBatchDetail;
	/** 批量付款详细 **/
	protected String payToAcctBatchDetail;

	/** 交易查询--错误提示页面 **/
	protected String errorView;

	/**
	 * 验证时间格式,格式错误返回当天时间,null返回null
	 * 
	 * @param date
	 * @return
	 */
	protected Date validateDate(String date) {
		if (StringUtils.isBlank(date)) {
			return null;
		}
		Date returnDate = null;
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			returnDate = dateFormat.parse(date);
		} catch (ParseException e) {
			dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			try {
				returnDate = dateFormat.parse(date);
			} catch (ParseException e1) {
				dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				try {
					returnDate = dateFormat.parse(date);
				} catch (ParseException e2) {
					log.error("格式化时间出错,使用默认时间");
					returnDate = new Date();
				}
			}
		}
		return returnDate;
	}

	/**
	 * 返回时间
	 * 
	 * @param offset
	 *            向前后偏移的天数 0为当天
	 * @return
	 */
	@SuppressWarnings("static-access")
	protected Date getDate(int offset) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(calendar.DATE, offset);

		return DateUtil.skipDateTime(DateUtil.getTheDate(new Date()), offset);
	}

	protected String getDate(Date date) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(date);
	}

	/**
	 * 月份偏移
	 * 
	 * @param offset
	 * @return
	 */
	protected String getDate2(int offset) {
		DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.add(calendar.MONTH, offset);
		return dateFormat2.format(new Date(calendar.getTimeInMillis()));
	}

	/**
	 * 获取默认开始时间范围
	 * 
	 * @param
	 * @return
	 */
	protected Date getStartTime(String startTime) {
		if (StringUtils.isBlank(startTime)) {
			return getDate(-3);
		} else {
			return validateDate(startTime);
		}
	}

	/**
	 * 获取默认结束时间范围
	 * 
	 * @param
	 * @return
	 */
	protected Date getEndTime(String endTime) {
		if (StringUtils.isBlank(endTime)) {
			return getDate(1);
		} else {
			return validateDate(endTime);
		}

	}

	/**
	 * 获取页码
	 * 
	 * @param request
	 * @return 当前页码-int
	 */
	protected int getPagerOffset(HttpServletRequest request) {
		int pager_offset = 1;
		if (StringUtils.isNumeric(request.getParameter("pager_offset"))) {
			pager_offset = Integer.parseInt(request.getParameter("pager_offset"));
		}
		return pager_offset;
	}

	/**
	 * 判断是否两位小数
	 * 
	 * @param number
	 * @return
	 */
	protected boolean isNumber(String number) {
		if (StringUtils.isBlank(number)) {
			return false;
		}
		return reg.matcher(number).matches();
	}

	/**
	 * 设置文件下载响应
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	protected void setResonseHeader(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setHeader("Expires", "0");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
		response.setHeader("Cache-Control", "public");
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		response.setHeader("Content-Disposition", "attachment;filename=" + new String(("export.xls").getBytes("UTF-8"), "ISO8859_1"));
	}
	
	/**
	 * 设置文件Csv下载响应
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	protected void setCsvResonseHeader(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setHeader("Expires", "0");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
		response.setHeader("Cache-Control", "public");
		response.setContentType("text/plain;charset=GBK");
		response.setHeader("Content-Disposition", "attachment;filename=" + new String(("export.csv").getBytes("UTF-8"), "ISO8859_1"));
	}

	/************** set ***************/
	public void setTradeInfoDetail(String tradeInfoDetail) {
		this.tradeInfoDetail = tradeInfoDetail;
	}

	public void setSingleTradeInfoDetail(String singleTradeInfoDetail) {
		this.singleTradeInfoDetail = singleTradeInfoDetail;
	}

	public void setWithdrawInfoDetail(String withdrawInfoDetail) {
		this.withdrawInfoDetail = withdrawInfoDetail;
	}

	public void setSingleWithdrawInfoDetail(String singleWithdrawInfoDetail) {
		this.singleWithdrawInfoDetail = singleWithdrawInfoDetail;
	}

	public void setIncomeDetailCorp(String incomeDetailCorp) {
		this.incomeDetailCorp = incomeDetailCorp;
	}

	public void setSingleIncomeDetailCorp(String singleIncomeDetailCorp) {
		this.singleIncomeDetailCorp = singleIncomeDetailCorp;
	}

	public void setPayDetailCorp(String payDetailCorp) {
		this.payDetailCorp = payDetailCorp;
	}

	public void setSinglePayDetailCorp(String singlePayDetailCorp) {
		this.singlePayDetailCorp = singlePayDetailCorp;
	}

	public void setPayBatchDetailCorp(String payBatchDetailCorp) {
		this.payBatchDetailCorp = payBatchDetailCorp;
	}

	public void setSinglePayBatchDetailCorp(String singlePayBatchDetailCorp) {
		this.singlePayBatchDetailCorp = singlePayBatchDetailCorp;
	}

	public void setReceiptRecordDetailCorp(String receiptRecordDetailCorp) {
		this.receiptRecordDetailCorp = receiptRecordDetailCorp;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setErrorView(String errorView) {
		this.errorView = errorView;
	}

	public void setExportincomeDetailCorp(String exportincomeDetailCorp) {
		this.exportincomeDetailCorp = exportincomeDetailCorp;
	}

	public void setExportReceiptRecordDetailCorp(String exportReceiptRecordDetailCorp) {
		this.exportReceiptRecordDetailCorp = exportReceiptRecordDetailCorp;
	}

	public void setExportPayDetailCorp(String exportPayDetailCorp) {
		this.exportPayDetailCorp = exportPayDetailCorp;
	}
	
	public void setExportPayDetailCorpCsv(String exportPayDetailCorpCsv) {
		this.exportPayDetailCorpCsv = exportPayDetailCorpCsv;
	}

	public void setExportPayBatchDetailCorp(String exportPayBatchDetailCorp) {
		this.exportPayBatchDetailCorp = exportPayBatchDetailCorp;
	}
	
	public void setExportPayBatchDetailCorpCsv(String exportPayBatchDetailCorpCsv) {
		this.exportPayBatchDetailCorpCsv = exportPayBatchDetailCorpCsv;
	}

	public void setExportTradeInfoDetail(String exportTradeInfoDetail) {
		this.exportTradeInfoDetail = exportTradeInfoDetail;
	}

	public void setExportWithdrawInfoDetail(String exportWithdrawInfoDetail) {
		this.exportWithdrawInfoDetail = exportWithdrawInfoDetail;
	}

	public void setBspWithdrawListCorp(String bspWithdrawListCorp) {
		this.bspWithdrawListCorp = bspWithdrawListCorp;
	}

	public void setBspWithdrawDetailCorp(String bspWithdrawDetailCorp) {
		this.bspWithdrawDetailCorp = bspWithdrawDetailCorp;
	}

	public void setFundAllotDetailCorp(String fundAllotDetailCorp) {
		this.fundAllotDetailCorp = fundAllotDetailCorp;
	}

	public void setPayToBankCertificate(String payToBankCertificate) {
		this.payToBankCertificate = payToBankCertificate;
	}

	public void setPayToBankBatchCertificate(String payToBankBatchCertificate) {
		this.payToBankBatchCertificate = payToBankBatchCertificate;
	}

	public void setPayToBankBatchDetail(String payToBankBatchDetail) {
		this.payToBankBatchDetail = payToBankBatchDetail;
	}
	
	/**
	 * @param payToAcctBatchDetail the payToAcctBatchDetail to set
	 */
	public void setPayToAcctBatchDetail(String payToAcctBatchDetail) {
		this.payToAcctBatchDetail = payToAcctBatchDetail;
	}

}
