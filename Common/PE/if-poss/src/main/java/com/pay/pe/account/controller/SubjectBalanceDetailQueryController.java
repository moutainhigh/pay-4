
package com.pay.pe.account.controller;

import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.pe.account.dto.QueryEntryDTO;
import com.pay.pe.account.dto.SubjectBalanceDTO;
import com.pay.pe.account.dto.SubjectDTO;
import com.pay.pe.account.enums.SubjectLevelEnum;
import com.pay.pe.account.service.EntryQueryService;
import com.pay.pe.account.service.SubjectService;
import com.pay.pe.dto.AcctSpecDTO;
import com.pay.pe.service.account.AcctSpecService;
import com.pay.util.JSonUtil;
import com.pay.util.MfDate;
import com.pay.util.StringUtil;


public class SubjectBalanceDetailQueryController extends MultiActionController {
    	private static final Log logger = LogFactory.getLog(SubjectBalanceDetailQueryController.class);
    	
	//Excel相关字段和信息
	private static final String[] COLPROPERTIES = { "acctCode", "relatedOrderId", "orderId", "value",
			"crdrName", "createdate", "postDate" };

	private static final String[] COLVIEW = { "科目账户", "关联订单号", "交易号", "金额(元)", "借贷", "建立时间", "记账时间" };

	
	private static final String[] TOTAL_COLVIEW = { "科目账户", "开始时间", "结束时间", "期初借方余额(元)", "期初贷方余额(元)",
			"期末借方余额(元)", "期末贷方余额(元)", "本期借方发生额(元)","本期贷方发生额(元)" };

	private static final String[] TOTAL_COLPROPERTIES = { "acctCode", "beginDate", "endDate",
			"beginningDrBalance", "beginningCrBalance", "endingDrBalance", "endingCrBalance", "drAmount","crAmount" };

	final static String RECONCILE_EXCEL_DOWNLOAD_NAME = "==================科目查询明细下载==================";
	     
	private SubjectService subjectService;

	private String indexView;

	private String listView;
	
	private String excelView ;

	private EntryQueryService entryQueryService;

	private AcctSpecService acctSpecService;

	//session中记录查询条件的Key
	private static final String QUERY_CONDITION_COMMAND = "query_condition_command";

	private static final String BALANCE_INFO = "balance_info";
	
	public String querySubjectToJSON(){
		List<SubjectDTO> subjectDtoList=this.subjectService.selectSubjectList();
		return JSonUtil.toJSonString(subjectDtoList);
		
	}
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		referenceData(model);
		model.put("json", querySubjectToJSON());
		return new ModelAndView(indexView, model);
	}

	
	
	/**
	 * 查询
	 */
	public ModelAndView query(HttpServletRequest request, HttpServletResponse response,
			SubjectBalanceQueryCommand command) throws Exception {
		Map<String,Object> model=new HashMap<String,Object>() ;	    	
//		//校验数据
		if (!validate(command, model)) {
			return new ModelAndView(listView, model);
		}
		String acctCode = request.getParameter("acctCode") ;
		String strBeginDate = request.getParameter("beginDate");
		String strEndDate = request.getParameter("endDate");
		
		Date beginDate = parseDate(strBeginDate);
		Date endDate = parseDate(strEndDate);
		MfDate beginDateMfDate = new MfDate(beginDate);
		MfDate endDateMfDate = new MfDate(endDate) ;
		
		List<QueryEntryDTO> allEntries = new ArrayList<QueryEntryDTO>(); //getAllEntries(command, vpage);
		
		Page<QueryEntryDTO> page = PageUtils.getPage(request);
		try {
			page =entryQueryService.getAllEntries(acctCode, beginDateMfDate, endDateMfDate, page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//SubjectBalanceDTO balanceInfo = getBalanceInfo(command, request);
		//session中设置查询参数,为下载所用.
		//setSesionQueryParam(request, command, balanceInfo);
		
		
		//-------added
		WebUtils.setSessionAttribute(request, BALANCE_INFO, model);
		
		model.put("page", page);
		//model.put("balanceInfo", balanceInfo);
		model.put("allEntries", allEntries);

		return new ModelAndView(listView, model);
	}

	
		//科目余额明细下载
	
	public ModelAndView excelDownLoad(HttpServletRequest request, HttpServletResponse response,
			SubjectBalanceQueryCommand command) throws Exception {
		
		Map<String,Object> model=new HashMap<String,Object>() ;	    	
			//校验数据
		if (!validate(command, model)) {
			return new ModelAndView(listView, model);
		}
		String acctCode = request.getParameter("acctCode") ;
		String strBeginDate = request.getParameter("beginDate");
		String strEndDate = request.getParameter("endDate");
		
		Date beginDate = parseDate(strBeginDate);
		Date endDate = parseDate(strEndDate);
		MfDate beginDateMfDate = new MfDate(beginDate);
		MfDate endDateMfDate = new MfDate(endDate) ;
		
		List<QueryEntryDTO> allEntries = new ArrayList<QueryEntryDTO>(); 
		List<QueryEntryDTO> list = null ;
		
		try {
			list = entryQueryService.getAllEntries(acctCode, beginDateMfDate, endDateMfDate) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		WebUtils.setSessionAttribute(request, BALANCE_INFO, model);
		
		
		
		ModelAndView mv = new ModelAndView(excelView)
			.addObject("list", list)
			.addObject("allEntries", allEntries);
		
		
		
		
		//设置excle响应格式
		String fileName = RECONCILE_EXCEL_DOWNLOAD_NAME;
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
		fileName += format.format(new Date());
		
		response.setHeader("Expires", "0");
		response.setHeader("Pragma" ,"public");
		response.setHeader("Cache-Control","must-revalidate, post-check=0, pre-check=0");
		response.setHeader("Cache-Control", "public");

		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String(("科目余额明细下载.xls").getBytes("UTF-8"),
						"ISO8859_1"));
		return mv;
	}
	
	
	
	
	
	
	private void generateExcel(HttpServletResponse response, List<QueryEntryDTO> entries,
			SubjectBalanceDTO balanceInfo) throws Exception {
		ByteArrayOutputStream baos = null;
		ServletOutputStream out = null;
		WritableWorkbook book = null;
		try {
			baos = new ByteArrayOutputStream();
			book = Workbook.createWorkbook(baos);

			//生成余额信息
			List<SubjectBalanceDTO> resultList = new ArrayList<SubjectBalanceDTO>();
			resultList.add(balanceInfo);
			book = QueryExcelUtil.getBookNotWrite(book, 0, "科目余额明细交易", TOTAL_COLVIEW,
					TOTAL_COLPROPERTIES, resultList);

			//续传生成明细信息
			QueryExcelUtil.resumeBroken(book.getSheet(0), 3, COLVIEW, COLPROPERTIES, entries);

			book.write();
			book.close();

			response.setContentType("application/vnd.ms-excel;charset=gb2312");
			response.setHeader("Content-disposition", "attachment; filename=" + "subject_balance_details.xls");
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
	
	
	
	
	/**
	 * 下载
	 */
	public final ModelAndView downloadExcel(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	   SubjectBalanceQueryCommand command = getSessionQueryParam(request);
		Map<String, Object> model = new HashMap<String, Object>();
		if (command == null) {
			 model.put("error",  "session已过期或无效,请重新查询后再下载.");
			return new ModelAndView(listView, model);
		}
		String acctCode = command.getAcctCode();
		MfDate beginDate = new MfDate(command.getBeginDate(), "yyyy-MM-dd");
		MfDate endDate = new MfDate(command.getEndDate(), "yyyy-MM-dd");

		List<QueryEntryDTO> entries  =entryQueryService.getAllEntries(acctCode, beginDate, endDate);
		
		
		for (QueryEntryDTO entry : entries) {
			if (entry.getCrdr() == 1) {
				entry.setCrdrName("借");
			} else if (entry.getCrdr() == 2) {
				entry.setCrdrName("贷"); 
			}
		}

		SubjectBalanceDTO balanceInfo = getBalanceInfo(command, request);
		
		balanceInfo.setAcctCode(command.getAcctCode());
		balanceInfo.setBeginDate(command.getBeginDate());
		balanceInfo.setEndDate(command.getEndDate());

		try {
			generateExcel(response, entries, balanceInfo);
		} catch (Exception e) {
			logger.error("下载文件[余额明细交易.xls]时出错", e);
			 model.put("error",  "下载excel时出错,请稍候重试.");
				return new ModelAndView(listView, model);
		}
		return null;
	}
	
	
	
	
	private void setSesionQueryParam(HttpServletRequest request, SubjectBalanceQueryCommand command,
			SubjectBalanceDTO balanceInfo) {
		WebUtils.setSessionAttribute(request, QUERY_CONDITION_COMMAND, command);

		WebUtils.setSessionAttribute(request, BALANCE_INFO, balanceInfo);
	}

	
	private boolean validate(SubjectBalanceQueryCommand command, Map<String, Object> model) {
		boolean isValid = true;
		if (StringUtil.isEmpty(command.getAcctCode()) || StringUtil.isEmpty(command.getBeginDate())
				|| StringUtil.isEmpty(command.getEndDate())) {
			String errorMsg = "科目帐号,开始时间,结束时间三个查询参数都不可以为空!";
			model.put("error", errorMsg);
			isValid = false;
		} else {
//			AcctSpecDTO acctSpec = acctSpecService.getAcctSpec(command.getAcctCode());
//			if (acctSpec == null) {
//				String errorMsg = "此科目帐号不存在!";
//				model.put("error", errorMsg);
//				isValid = false;
//			}else if(null==acctSpec.getAcctLevel() || acctSpec.getAcctLevel()!=4){
//				String errorMsg = "此科目帐号不是末级科目!";
//				model.put("error", errorMsg);
//				isValid = false;
//			}
		}
		return isValid;
	}
	
	
	
	private SubjectBalanceDTO getBalanceInfo(SubjectBalanceQueryCommand command, HttpServletRequest request) {
		
		
		String totalCount = request.getParameter("totalCount"); //判断是否翻页查询
		SubjectBalanceDTO balanceInfo = null;
		if(StringUtil.isEmpty(totalCount) || "undefined".equals(totalCount)){
			
			
			//String acctCode = command.getAcctCode();
			//MfDate beginDate = new MfDate(command.getBeginDate(), "yyyy-MM-dd");
			//MfDate endDate = new MfDate(command.getEndDate(), "yyyy-MM-dd");
			//balanceInfo = entryQueryService.getSubjectBalance(acctCode, beginDate, endDate);
			
			command.setSubjectLevel(""+SubjectLevelEnum.FOUR_LEVER.getCode());
			Integer balanceCount=entryQueryService.countSubjectBalanceListLevel(command);			
			List<SubjectBalanceDTO> list=entryQueryService.getAllSubjectBalanceList(command, balanceCount);
			if(null!=list && list.size()>0){
				balanceInfo = list.get(0);
			}
		}else{
			balanceInfo = getSessionBalanceInfo(request);
		}
		return balanceInfo;
	}
	
	private SubjectBalanceDTO getSessionBalanceInfo(HttpServletRequest request) {
		SubjectBalanceDTO balanceInfo = (SubjectBalanceDTO) WebUtils.getSessionAttribute(request, BALANCE_INFO);
		return balanceInfo;
	}
	
	
	private SubjectBalanceQueryCommand getSessionQueryParam(HttpServletRequest request) {
		SubjectBalanceQueryCommand command = (SubjectBalanceQueryCommand) WebUtils.getSessionAttribute(request,
				QUERY_CONDITION_COMMAND);
		return command;
	}
	
	
	/**
	 * @param model
	 */
	private void referenceData(Map<String, Object> model) {
		model.put("today", MfDate.today().toString());
		MfDate date = MfDate.today() ;
		date.minusDays(1);
		model.put("before", date.toString());
	}

	/**
	 * @param indexView the indexView to set
	 */
	public void setIndexView(String indexView) {
		this.indexView = indexView;
	}



	/**
	 * @param listView the listView to set
	 */
	public void setListView(String listView) {
		this.listView = listView;
	}

	/**
	 * @param acctSpecService the acctSpecService to set
	 */
	public void setAcctSpecService(AcctSpecService acctSpecService) {
		this.acctSpecService = acctSpecService;
	}



	public EntryQueryService getEntryQueryService() {
		return entryQueryService;
	}



	public void setEntryQueryService(EntryQueryService entryQueryService) {
		this.entryQueryService = entryQueryService;
	}
	
	public void setSubjectService(SubjectService subjectService) {
		this.subjectService = subjectService;
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
	public void setExcelView(String excelView) {
		this.excelView = excelView;
	}

}
