package com.pay.pe.manualbooking.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.pe.manualbooking.util.VouchDetailSearchCriteria;
import com.pay.pe.manualbooking.util.VouchSearchCriteria;

/**
 * 
 *
 */
public class BaseVouchSearchController extends MultiActionController {	
	private static final Log LOG = LogFactory.getLog(BaseVouchSearchController.class);
	//手工记账申请查询条件
	public static final String VOUCH_SEQ = "vouchSeq";
	public static final String VOUCH_CODE = "vouchCode";
	public static final String VOUCH_STATUS = "vouchStatus";
	public static final String DATE_FROM = "dateFrom";
	public static final String DATE_TO = "dateTo";
	public static final String PAGE_NUM = "page";
	public static final String AMOUNT_FROM = "amountFrom";
	public static final String AMOUNT_TO = "amountTo";
	public static final String REMARK = "remark";

	
	public static final String ACCT_CODE = "accountCode";
	
	//页面变量名称
	public static final String VOUCH_SEARCH_CRITERIA_NAME = "vouchSearchCriteria";
	public static final String SEARCH_RESULT_NAME = "vouchDataDtos";
	
	public static final String VOUCH_DETAIL_SEARCH_CRITERIA_NAME = "vouchDetailSearchCriteria";
	public static final String DETAIL_SEARCH_RESULT_NAME = "vouchDataDetailSearchDtos";
	
	public static final String VOUCH_DATA_ID = "vouchDataId";
	public static final String BATCH_VOUCH_DATA_ID = "batchVouchDataId";

	public static final String VOUCH_DATA = "vouchData";
	
	public static final String BATCH_VOUCH_DATA = "batchVouchData";

	public static final String VOUCH_MESSAGE = "vouchMessage";
	
	public static final String VOUCH_DATA_MSG = "记账凭证：";

	public static final String CANCEL_SUCC_MESSAGE = "删除成功";
	public static final String CANCEL_FAIL_MESSAGE = "删除失败";
	public static final String APPROVE_SUCC_MESSAGE = "审核通过成功";
	public static final String APPROVE_FAIL_MESSAGE = "审核通过失败";
	public static final String REJECT_SUCC_MESSAGE = "审核不通过成功";
	public static final String REJECT_FAIL_MESSAGE = "审核不通过失败";
	
	/**
	 * @param request
	 * @return
	 * 取得手工记账明细查询条件
	 */
	protected VouchDetailSearchCriteria getVouchDetailSearchCriteria(HttpServletRequest request) {
		LOG.info("Start");
		String accountCode = request.getParameter(ACCT_CODE);
		String strDateFrom = request.getParameter(DATE_FROM);
		String strDateTo = request.getParameter(DATE_TO);
		
		Date dateFrom = null;
		if (StringUtils.isNotEmpty(strDateFrom)) {
			dateFrom = parseDate(strDateFrom);
		}
		
		Date dateTo = null;
		if (StringUtils.isNotEmpty(strDateTo)) {
			dateTo = parseDate(strDateTo);
		}
		
		VouchDetailSearchCriteria vouchDetailSearchCriteria = 
			new VouchDetailSearchCriteria(accountCode, dateFrom, dateTo);
		
		LOG.debug("Account code : " + accountCode);
		LOG.debug("Date from : " + dateFrom);
		LOG.debug("Date to : " + dateTo);
		
		LOG.info("End");
		return vouchDetailSearchCriteria;
	}
	
	/**
	 * @param request
	 * @return
	 * 取得手工记帐查询条件
	 */
	protected VouchSearchCriteria getVouchSearchCriteria(HttpServletRequest request) {
		LOG.info("Start");
		
		String vouchSeq = request.getParameter(VOUCH_SEQ);
		String vouchCode = request.getParameter(VOUCH_CODE);
		String strVouchStatus = request.getParameter(VOUCH_STATUS);
		String strDateFrom = request.getParameter(DATE_FROM);
		String strDateTo = request.getParameter(DATE_TO);
		String strPageNum = request.getParameter(PAGE_NUM);
		String strAmountFrom=request.getParameter(AMOUNT_FROM);
		String strAmountTo=request.getParameter(AMOUNT_TO);
		String remark=request.getParameter(REMARK);

		
		
		Integer vouchStatus = 0;
		
		if (StringUtils.isNotEmpty(strVouchStatus)) {
			vouchStatus = Integer.parseInt(strVouchStatus);
		}
		
		Date dateFrom = null;
		if (StringUtils.isNotEmpty(strDateFrom)) {
			dateFrom = parseDate(strDateFrom);
		}
		
		Date dateTo = null;
		if (StringUtils.isNotEmpty(strDateTo)) {
			dateTo = parseDate(strDateTo);
		}
		
		Integer pageNum = 1;
		if (StringUtils.isNotEmpty(strPageNum)) {
			pageNum = Integer.parseInt(strPageNum);
		}
		BigDecimal amountFrom=null;
		if (StringUtils.isNotEmpty(strAmountFrom)) {
			amountFrom =new BigDecimal(strAmountFrom);
		}
		BigDecimal amountTo=null;
		if (StringUtils.isNotEmpty(strAmountTo)) {
			amountTo =new BigDecimal(strAmountTo);
		}
		VouchSearchCriteria vouchSearchCriteria = new VouchSearchCriteria();
		vouchSearchCriteria.setVouchSeq(vouchSeq);
		vouchSearchCriteria.setVouchCode(vouchCode);
		vouchSearchCriteria.setVouchStatus(vouchStatus);
		vouchSearchCriteria.setDateFrom(dateFrom);
		vouchSearchCriteria.setDateTo(dateTo);
		vouchSearchCriteria.setAmountFrom(amountFrom);
		vouchSearchCriteria.setAmountTo(amountTo);		
		vouchSearchCriteria.setPage(pageNum);
		vouchSearchCriteria.setRemark(remark);
		LOG.debug("Vouch seq : " + vouchSeq);
		LOG.debug("Vouch code : " + vouchCode);
		LOG.debug("Vouch status : " + vouchStatus);
		LOG.debug("Date from : " + dateFrom);
		LOG.debug("Date to : " + dateTo);
		LOG.debug("amount from : " + amountFrom);
		LOG.debug("amount to : " + amountTo);
		LOG.debug("Page : " + pageNum);
		
		LOG.info("End");
		return vouchSearchCriteria;
	}
	
	private Date parseDate(String strDate) {
		Date date = null;
		try {
			date = DateUtils.parseDate(strDate, new String[] { "yyyy-MM-dd" });
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
}
