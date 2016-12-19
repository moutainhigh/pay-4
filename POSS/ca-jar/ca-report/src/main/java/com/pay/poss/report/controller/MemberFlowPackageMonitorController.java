package com.pay.poss.report.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.report.dto.MemberFlowPackageMonitorDTO;
import com.pay.poss.report.service.MemberFlowPackageMonitorService;
import com.pay.util.StringUtil;

/**
 * 分子公司维护
 * 
 * @Description
 * @file MemberFlowController
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 */
public class MemberFlowPackageMonitorController extends MultiActionController {

	private String viewList;

	private String toView;

	private MemberFlowPackageMonitorService memberFlowPackageMonitorService;

	public void setToView(String toView) {
		this.toView = toView;
	}

	public void setViewList(String viewList) {
		this.viewList = viewList;
	}

	public void setMemberFlowPackageMonitorService(
			MemberFlowPackageMonitorService memberFlowPackageMonitorService) {
		this.memberFlowPackageMonitorService = memberFlowPackageMonitorService;
	}

	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(toView);
	}

	/**
	 * 查询会员包量监控列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView query(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String memberCode = StringUtil.null2String(request
				.getParameter("memberCode"));
		String name = StringUtil
				.null2String(request.getParameter("memberName"));
		String warnStatus = StringUtil.null2String(request
				.getParameter("warnStatus"));
		String gatewayStatus = StringUtil.null2String(request
				.getParameter("gatewayStatus"));

		if (!StringUtil.isEmpty(memberCode)) {
			map.put("memberCode", memberCode.trim());
		}
		if (!StringUtil.isEmpty(name)) {
			map.put("memberName", name.trim());
		}
		if (!StringUtil.isEmpty(warnStatus)) {
			map.put("warnStatus", warnStatus.trim());
		}
		if (!StringUtil.isEmpty(gatewayStatus)) {
			map.put("gatewayStatus", gatewayStatus.trim());
		}
		Page<MemberFlowPackageMonitorDTO> page = PageUtils.getPage(request);
		page = memberFlowPackageMonitorService.queryMemberFlowPackageMonitor(
				page, map);
		return new ModelAndView(viewList).addObject("page", page).addObject(
				"warnStatus", "");
	}

}
