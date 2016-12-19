package com.pay.pe.account.controller;

import java.io.ByteArrayOutputStream;
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

import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.pe.account.dto.SubjectBalanceDTO;
import com.pay.pe.account.dto.SubjectDTO;
import com.pay.pe.account.enums.SubjectLevelEnum;
import com.pay.pe.account.service.EntryQueryService;
import com.pay.pe.account.service.SubjectService;
import com.pay.util.JSonUtil;
import com.pay.util.MfDate;
import com.pay.util.StringUtil;

public class SubjectBalanceQueryController extends MultiActionController {
	private static final Log log = LogFactory
			.getLog(SubjectBalanceQueryController.class);
	private String indexView;

	private String listView;

	private EntryQueryService entryQueryService;
	private SubjectService subjectService;

	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("today", MfDate.today().toString());
		MfDate date = MfDate.today();
		date.minusDays(1);
		model.put("before", date.toString());
		model.put("json", querySubjectToJSON());
		return new ModelAndView(indexView, model);
	}

	public String querySubjectToJSON() {
		List<SubjectDTO> subjectDtoList = this.subjectService
				.selectSubjectList();
		return JSonUtil.toJSonString(subjectDtoList);

	}

	@SuppressWarnings("unchecked")
	public ModelAndView query(HttpServletRequest request,
			HttpServletResponse response, SubjectBalanceQueryCommand command)
			throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		if (!validate(command, model)) {
			return new ModelAndView(listView, model);
		}

		// 科目级别
		String subjectLevel = request.getParameter("subjectLevel");

		Page page = PageUtils.getPage(request);

		List<SubjectBalanceDTO> resultList = entryQueryService
				.getSubjectBalanceList(command, page);

		page.setResult(resultList);

		WebUtils.setSessionAttribute(request, "SubjectBalanceQuery_Command",
				command);
		WebUtils.setSessionAttribute(request, "SubjectBalanceQueryResultList",
				resultList);

		model.put("page", page);
		model.put("subjectLevel", subjectLevel);
		return new ModelAndView(listView, model);
	}

	/**
	 * 下载
	 */
	public final ModelAndView downloadExcel(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SubjectBalanceQueryCommand command = (SubjectBalanceQueryCommand) WebUtils
				.getSessionAttribute(request, "SubjectBalanceQuery_Command");
		Map<String, Object> model = new HashMap<String, Object>();
		if (command == null) {
			model.put("error", "session已过期或无效,请重新查询后再下载.");
			return new ModelAndView(listView, model);
		}

		List<SubjectBalanceDTO> resultList = (List<SubjectBalanceDTO>) WebUtils
				.getSessionAttribute(request, "SubjectBalanceQueryResultList");
		try {
			generateExcel(response, resultList, command);
		} catch (Exception e) {
			logger.error("下载文件[科目余额.xls]时出错", e);
			model.put("error", "下载excel时出错,请稍候重试.");
			return new ModelAndView(listView, model);
		}
		return null;
	}

	public final ModelAndView downloadOverExcel(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SubjectBalanceQueryCommand command = (SubjectBalanceQueryCommand) WebUtils
				.getSessionAttribute(request, "SubjectBalanceQuery_Command");
		Map<String, Object> model = new HashMap<String, Object>();
		if (command == null) {
			model.put("error", "session已过期或无效,请重新查询后再下载.");
			return new ModelAndView(listView, model);
		}
		int level = Integer.parseInt(command.getSubjectLevel());
		Integer count = 0;
		if (SubjectLevelEnum.FIVE_LEVER.getCode() == level) {
			count = entryQueryService.countSubjectBalanceListFiveLevel(command);
		} else {
			count = entryQueryService.countSubjectBalanceListLevel(command);
		}
		if (count > 5000) {
			model.put("error", "excel表格超出5000行,请用分页下载");
			return new ModelAndView(listView, model);
		}
		List<SubjectBalanceDTO> resultList = entryQueryService
				.getAllSubjectBalanceList(command, count);
		if (null != resultList) {
			System.out.println("resultList  : " + resultList.size());
		}
		try {
			generateExcel(response, resultList, command);
		} catch (Exception e) {
			logger.error("下载文件[科目余额.xls]时出错", e);
			model.put("error", "下载excel时出错,请稍候重试.");
			return new ModelAndView(listView, model);
		}
		return null;
	}

	private static final String[] TOTAL_COLVIEW = { "科目账户", "科目名称",
			"期初借方余额(元)", "期初贷方余额(元)", "本期借方发生额(元)",
			"本期贷方发生额(元)" , "期末借方余额(元)", "期末贷方余额(元)"};

	private static final String[] TOTAL_COLPROPERTIES = { "acctCode",
			"acctName", "beginningDrBalance", "beginningCrBalance", "drAmount", "crAmount",
			"endingDrBalance", "endingCrBalance" };

	private static final String[] QUERY_COLVIEW = { "科目账户", "科目级别", "开始时间",
			"结束时间" };

	private static final String[] QUERY_COLPROPERTIES = { "acctCode",
			"subjectLevelName", "beginDate", "endDate" };

	private void generateExcel(HttpServletResponse response,
			List<SubjectBalanceDTO> resultList,
			SubjectBalanceQueryCommand command) throws Exception {
		ByteArrayOutputStream baos = null;
		ServletOutputStream out = null;
		WritableWorkbook book = null;
		try {
			baos = new ByteArrayOutputStream();
			book = Workbook.createWorkbook(baos);

			// 生成查询条件
			List<SubjectBalanceQueryCommand> queryList = new ArrayList<SubjectBalanceQueryCommand>();
			// 得到科目余额的值
			int level = Integer.parseInt(command.getSubjectLevel());
			command.setSubjectLevelName(SubjectLevelEnum.get(level)
					.getMessage());
			queryList.add(command);
			book = QueryExcelUtil.getBookNotWrite(book, 0, "科目余额",
					QUERY_COLVIEW, QUERY_COLPROPERTIES, queryList);

			// 生成查询结果
			QueryExcelUtil.resumeBroken(book.getSheet(0), 3, TOTAL_COLVIEW,
					TOTAL_COLPROPERTIES, resultList);
			book.write();
			book.close();

			response.setContentType("application/vnd.ms-excel;charset=gb2312");
			response.setHeader("Content-disposition", "attachment; filename="
					+ "subject_balance.xls");
			response.setContentLength(baos.size());

			// 将byte流中的数据写入输出流中
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

	private boolean validate(SubjectBalanceQueryCommand command,
			Map<String, Object> model) {
		boolean isValid = true;
		// if (StringUtil.isEmpty(command.getAcctCode()) ||
		// StringUtil.isEmpty(command.getBeginDate())
		if (StringUtil.isEmpty(command.getBeginDate())
				|| StringUtil.isEmpty(command.getEndDate())) {
			// String errorMsg = "科目帐号,开始时间,结束时间三个查询参数都不可以为空!";
			String errorMsg = "开始时间,结束时间二个查询参数都不可以为空!";
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

	public void setSubjectService(SubjectService subjectService) {
		this.subjectService = subjectService;
	}

}
