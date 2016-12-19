package com.pay.poss.report.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.report.dto.MemberDailyReportDTO;
import com.pay.poss.report.service.MemberDailyReportService;
import com.pay.util.StringUtil;

/**
 * 分子公司流量统计
 * 
 * @Description
 * @file InnerMemberFlowController
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 */
public class MemberDailyReportController extends MultiActionController {

	private String viewList;

	private String toView;

	private String memberDailyReportexcel;

	private MemberDailyReportService memberDailyReportService;

	public void setToView(String toView) {
		this.toView = toView;
	}

	public void setViewList(String viewList) {
		this.viewList = viewList;
	}

	public void setMemberDailyReportexcel(String memberDailyReportexcel) {
		this.memberDailyReportexcel = memberDailyReportexcel;
	}

	public void setMemberDailyReportService(
			MemberDailyReportService memberDailyReportService) {
		this.memberDailyReportService = memberDailyReportService;
	}

	/**
	 * 跳转至查询页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Calendar time = Calendar.getInstance();
		time.add(Calendar.DATE, -1);
		Date startDate = time.getTime();
		Date endDate = startDate;

		return new ModelAndView(toView).addObject("startDate", startDate)
				.addObject("endDate", endDate);
	}

	/**
	 * 查询下载页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView query(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String startDate = StringUtil.null2String(request
				.getParameter("startDate"));
		String endDate = StringUtil
				.null2String(request.getParameter("endDate"));
		String memberCode = StringUtil.null2String(request
				.getParameter("memberCode"));
		String memberType = StringUtil.null2String(request
				.getParameter("memberType"));

		if (!StringUtil.isEmpty(startDate)) {
			map.put("startDate", startDate.trim());
		}
		if (!StringUtil.isEmpty(endDate)) {
			map.put("endDate", endDate.trim());
		}
		if (!StringUtil.isEmpty(memberCode)) {
			map.put("memberCode", memberCode.trim());
		}
		if (!StringUtil.isEmpty(memberType)) {
			map.put("memberType", memberType.trim());
		}
		Page<MemberDailyReportDTO> page = PageUtils.getPage(request);
		List<MemberDailyReportDTO> result = new ArrayList<MemberDailyReportDTO>();
		String export = StringUtil.null2String(request.getParameter("export"));

		if (!StringUtils.isEmpty(export)) {
			String fileName = "综合报表下载";
			result = memberDailyReportService.queryMemberDailyReport(map);
			response.setHeader("Expires", "0");
			response.setHeader("Pragma", "public");
			response.setHeader("Cache-Control",
					"must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Cache-Control", "public");
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String((fileName + ".xls").getBytes("GBK"),
							"ISO8859_1"));
			return new ModelAndView(memberDailyReportexcel).addObject("result",
					result);
		} else {
			page = memberDailyReportService.queryMemberDailyReport(page, map);
			return new ModelAndView(viewList).addObject("page", page);
		}
	}

}
