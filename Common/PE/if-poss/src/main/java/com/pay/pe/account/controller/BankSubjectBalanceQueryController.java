package com.pay.pe.account.controller;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.WritableWorkbook;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.util.WebUtils;

import com.pay.pe.account.dto.SubjectBalanceDTO;
import com.pay.pe.account.service.EntryQueryService;
import com.pay.util.MfDate;
import com.pay.util.StringUtil;

public class BankSubjectBalanceQueryController extends MultiActionController{
	private static final Log log = LogFactory.getLog(BankSubjectBalanceQueryController.class);
	private String indexView;

	private String listView;
	
	private EntryQueryService entryQueryService;
	
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("today", MfDate.today().toString());
		MfDate date = MfDate.today() ;
		date.minusDays(1);
		model.put("before", date.toString());
		return new ModelAndView(indexView, model);
	}
	
	public ModelAndView query(HttpServletRequest request, HttpServletResponse response,
			SubjectBalanceQueryCommand command) throws Exception {
		Map<String,Object> model=new HashMap<String,Object>() ;
		if (!validate(command, model)) {
			return new ModelAndView(listView, model);
		}
		String acctCode = request.getParameter("acctCode") ;
		String strBeginDate = request.getParameter("beginDate");
		String strEndDate = request.getParameter("endDate");
		Date beginDate = parseDate(strBeginDate);
		Date  endDate = parseDate(strEndDate);
		MfDate beginDateMfDate = new MfDate(beginDate);
		MfDate endDateMfDate = new MfDate(endDate) ;
		
		List<SubjectBalanceDTO> resultList = entryQueryService.getSubjectBalanceList(acctCode, beginDateMfDate, endDateMfDate);
		
	
		
		SubjectBalanceDTO subjectBalanceStatDTO = new SubjectBalanceDTO();
		
		
		
		BigDecimal endBalance = new BigDecimal(0.00);
//		BigDecimal crSubDrStat = new BigDecimal(0.00);
		BigDecimal crAmount = new BigDecimal(0.00);
		BigDecimal drAmount = new BigDecimal(0.00);
		
		if(resultList!=null){
			for(SubjectBalanceDTO subjectBalanceDTO :resultList){
				if(subjectBalanceDTO.getEndingBalance()!=null){
					endBalance = endBalance.add(subjectBalanceDTO.getEndingBalance());
				}
//				if(subjectBalanceDTO.getCrSubDr()!=null){
//					crSubDrStat.add(subjectBalanceDTO.getCrSubDr());
//				}
				if(subjectBalanceDTO.getCrAmount()!=null){
					crAmount=crAmount.add(subjectBalanceDTO.getCrAmount());
				}
				if(subjectBalanceDTO.getDrAmount()!=null){
					drAmount=drAmount.add(subjectBalanceDTO.getDrAmount());
				}
			}
			subjectBalanceStatDTO.setBalanceBy(1);//注意 
			subjectBalanceStatDTO.setEndingBalance(endBalance.multiply(new BigDecimal(1000)));
			subjectBalanceStatDTO.setCrAmount(crAmount.multiply(new BigDecimal(1000)));
			subjectBalanceStatDTO.setDrAmount(drAmount.multiply(new BigDecimal(1000)));
			subjectBalanceStatDTO.setAcctName("合计");
			resultList.add(subjectBalanceStatDTO);
		}
		
		
		WebUtils.setSessionAttribute(request, "BankSubjectBalanceQuery_Command", command);
		WebUtils.setSessionAttribute(request, "BankSubjectBalanceQueryResultList", resultList);
		
//		SubjectBalanceStatDTO subjectBalanceStatDTO = new SubjectBalanceStatDTO();
//		subjectBalanceStatDTO.setEndingDrBalanceStat(endStat);
//		subjectBalanceStatDTO.setCrSubDrStat(crSubDrStat);
//		WebUtils.setSessionAttribute(request, "BankSubjectBalanceQuery_ResultStat", subjectBalanceStatDTO);
		
		
		
		
		model.put("resultList", resultList);
		return new ModelAndView(listView, model);
	}
	
	
	
	/**
	 * 下载
	 */
	public final ModelAndView downloadExcel(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SubjectBalanceQueryCommand command = (SubjectBalanceQueryCommand) WebUtils.getSessionAttribute(request,
				"BankSubjectBalanceQuery_Command");
		
//		SubjectBalanceStatDTO subjectBalanceStatDTO = (SubjectBalanceStatDTO)WebUtils.getSessionAttribute(request, "BankSubjectBalanceQuery_ResultStat");
		
		
	   Map<String, Object> model = new HashMap<String, Object>();
		if (command == null) {
			 model.put("error",  "session已过期或无效,请重新查询后再下载.");
			return new ModelAndView(listView, model);
		}
			List<SubjectBalanceDTO> resultList = (List<SubjectBalanceDTO>) WebUtils.getSessionAttribute(request,
		"BankSubjectBalanceQueryResultList");
		try {
			generateExcel(response, resultList,command);
		} catch (Exception e) {
			logger.error("下载文件[客户资金出账统计.xls]时出错", e);
			 model.put("error",  "下载excel时出错,请稍候重试.");
				return new ModelAndView(listView, model);
		}
		return null;
	}
	
		
	private static final String[] TOTAL_COLVIEW = { "渠道行（全称）", "出账金额(元)", "银行余额(元)"};

	private static final String[] TOTAL_COLPROPERTIES = { "acctName",
		  "crSubDr", "endingDrBalance"};
	
	
	private static final String[] QUERY_COLVIEW = {  "开始时间", "结束时间"};

	private static final String[] QUERY_COLPROPERTIES = {  "beginDate", "endDate"};
	
	private void generateExcel(HttpServletResponse response, List<SubjectBalanceDTO> resultList,
			SubjectBalanceQueryCommand command) throws Exception {
		ByteArrayOutputStream baos = null;
		ServletOutputStream out = null;
		WritableWorkbook book = null;
		try {
			baos = new ByteArrayOutputStream();
			book = Workbook.createWorkbook(baos);

			//生成查询条件 
			List<SubjectBalanceQueryCommand> queryList = new ArrayList<SubjectBalanceQueryCommand>();
			queryList.add(command);
			
			
			book = QueryExcelUtil.getBookNotWrite(book, 0, "客户资金出账统计", QUERY_COLVIEW,
					QUERY_COLPROPERTIES, queryList);

			//生成查询结果 
			QueryExcelUtil.resumeBroken(book.getSheet(0), 3, TOTAL_COLVIEW, TOTAL_COLPROPERTIES, resultList);
			
			
			
			
			book.write();
			book.close();

			response.setContentType("application/vnd.ms-excel;charset=gb2312");
			response.setHeader("Content-disposition", "attachment; filename=" + "bank_subject_balance.xls");
			response.setContentLength(baos.size());

			//将byte流中的数据写入输出流中
			out = response.getOutputStream();
			baos.writeTo(out);
			out.flush();

		} finally {
			if (book != null) {
				try {
					book.close();
				} catch (Exception e) {
					logger.warn("fail to close writable work book resource.");
				}
			}
			if (out != null)
				out.close();
			if (baos != null)
				baos.close();
		}
	}
	
	
	
	 
	
	
	
	
	
	
	

	private boolean validate(SubjectBalanceQueryCommand command, Map<String, Object> model) {
		boolean isValid = true;
		if (StringUtil.isEmpty(command.getAcctCode()) || StringUtil.isEmpty(command.getBeginDate())
				|| StringUtil.isEmpty(command.getEndDate())) {
			String errorMsg = "科目帐号,开始时间,结束时间三个查询参数都不可以为空!";
			model.put("error", errorMsg);
			isValid = false;
		} 
		return isValid;
	}
	
	
	public void setIndexView(String indexView) {
		this.indexView = indexView;
	}

	public void setListView(String listView) {
		this.listView = listView;
	}

	public EntryQueryService getEntryQueryService() {
		return entryQueryService;
	}

	public void setEntryQueryService(EntryQueryService entryQueryService) {
		this.entryQueryService = entryQueryService;
	}
	
	private Date parseDate(String strDate) {
		Date date = null;
		try {
			date = DateUtils.parseDate(strDate, new String[] { "yyyy-MM-dd" });
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	
	
	
	

	
	
}
