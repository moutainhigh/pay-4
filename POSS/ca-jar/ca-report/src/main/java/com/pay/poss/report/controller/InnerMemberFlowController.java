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
import com.pay.poss.report.dto.InnerMemberDTO;
import com.pay.poss.report.dto.InnerMemberFlowDTO;
import com.pay.poss.report.dto.MemberFlowDTO;
import com.pay.poss.report.service.InnerMemberFlowService;
import com.pay.poss.report.service.InnerMemberService;
import com.pay.poss.report.service.MemberFlowService;
import com.pay.util.StringUtil;

/**
 * 分子公司流量统计
 * 
 * @Description
 * @file InnerMemberFlowController
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 */
public class InnerMemberFlowController extends MultiActionController {

	private String viewList;

	private String toView;

	private String innerMemberFlowexcel;

	private MemberFlowService memberFlowService;

	private InnerMemberService innerMemberService;

	private InnerMemberFlowService innerMemberFlowService;

	public void setToView(String toView) {
		this.toView = toView;
	}

	public void setViewList(String viewList) {
		this.viewList = viewList;
	}

	public void setInnerMemberFlowexcel(String innerMemberFlowexcel) {
		this.innerMemberFlowexcel = innerMemberFlowexcel;
	}

	public void setMemberFlowService(MemberFlowService memberFlowService) {
		this.memberFlowService = memberFlowService;
	}

	public void setInnerMemberService(InnerMemberService innerMemberService) {
		this.innerMemberService = innerMemberService;
	}

	public void setInnerMemberFlowService(
			InnerMemberFlowService innerMemberFlowService) {
		this.innerMemberFlowService = innerMemberFlowService;
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

		List<InnerMemberDTO> list = innerMemberService.findAllInnerMember();

		return new ModelAndView(toView).addObject("startDate", startDate)
				.addObject("endDate", endDate).addObject("list", list);
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
		String optType = StringUtil
				.null2String(request.getParameter("optType"));

		if (!StringUtil.isEmpty(startDate)) {
			map.put("startDate", startDate.trim());
		}
		if (!StringUtil.isEmpty(endDate)) {
			map.put("endDate", endDate.trim());
		}
		if (!StringUtil.isEmpty(memberCode)) {
			map.put("memberCode", memberCode.trim());
		}
		if (StringUtil.isEmpty(optType) || "1".equalsIgnoreCase(optType)) {
			Page<InnerMemberFlowDTO> page = PageUtils.getPage(request);
			List<InnerMemberFlowDTO> result = new ArrayList<InnerMemberFlowDTO>();
			String export = StringUtil.null2String(request
					.getParameter("export"));

			if (!StringUtils.isEmpty(export)) {
				String fileName = "分子公司流量下载";
				result = innerMemberFlowService.queryInnerMemberFlow(map);
				response.setHeader("Expires", "0");
				response.setHeader("Pragma", "public");
				response.setHeader("Cache-Control",
						"must-revalidate, post-check=0, pre-check=0");
				response.setHeader("Cache-Control", "public");
				response
						.setContentType("application/vnd.ms-excel;charset=utf-8");
				response.setHeader("Content-Disposition",
						"attachment;filename="
								+ new String((fileName + ".xls")
										.getBytes("GBK"), "ISO8859_1"));
				return new ModelAndView(innerMemberFlowexcel).addObject(
						"result", result);
			} else {
				page = innerMemberFlowService.queryInnerMemberFlow(page, map);
				return new ModelAndView(viewList).addObject("page", page);
			}
		} else {// 商户明细查询
			Page<MemberFlowDTO> page = PageUtils.getPage(request);
			List<MemberFlowDTO> result = new ArrayList<MemberFlowDTO>();
			String export = StringUtil.null2String(request
					.getParameter("export"));

			if (!StringUtils.isEmpty(export)) {
				String fileName = "商户流量下载";
				result = memberFlowService.queryInnerMemberFlowDetail(map);
				response.setHeader("Expires", "0");
				response.setHeader("Pragma", "public");
				response.setHeader("Cache-Control",
						"must-revalidate, post-check=0, pre-check=0");
				response.setHeader("Cache-Control", "public");
				response
						.setContentType("application/vnd.ms-excel;charset=utf-8");
				response.setHeader("Content-Disposition",
						"attachment;filename="
								+ new String((fileName + ".xls")
										.getBytes("GBK"), "ISO8859_1"));
				return new ModelAndView(innerMemberFlowexcel).addObject(
						"result", result).addObject("optType", 2);
			} else {
				page = memberFlowService.queryInnerMemberFlowDetail(page, map);
				return new ModelAndView(viewList).addObject("page", page)
						.addObject("optType", 2);
			}
		}
	}

}
