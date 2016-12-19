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
import com.pay.poss.report.dto.MemberFlowDTO;
import com.pay.poss.report.service.MemberFlowService;
import com.pay.poss.security.util.SessionUserHolderUtil;
import com.pay.poss.userrelation.dto.NodesDto;
import com.pay.poss.userrelation.service.IUserRelationService;
import com.pay.util.StringUtil;

/**
 * 商户流量统计
 * 
 * @Description
 * @file MemberFlowController
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 */
public class MemberFlowController extends MultiActionController {

	public void setMemberFlowService(MemberFlowService memberFlowService) {
		this.memberFlowService = memberFlowService;
	}

	private String viewName;

	private String toView;

	private String memberFlowexcel;

	private MemberFlowService memberFlowService;
	private IUserRelationService userRelationService;

	public void setUserRelationService(IUserRelationService userRelationService) {
		this.userRelationService = userRelationService;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public void setToView(String toView) {
		this.toView = toView;
	}

	public void setMemberFlowexcel(String memberFlowexcel) {
		this.memberFlowexcel = memberFlowexcel;
	}

	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Calendar time = Calendar.getInstance();
		time.add(Calendar.DATE, -1);
		Date startDate = time.getTime();
		Date endDate = startDate;

		//List<NodesDto> loginSubNodes = getLoginIdSubNodes();

		return new ModelAndView(toView)
				.addObject("startDate", startDate)
				.addObject("endDate", endDate)
				//.addObject(
				//		"loginSubNodes",
						//loginSubNodes == null ? new ArrayList<NodesDto>()
								//: loginSubNodes)
								;
	}

	public ModelAndView queryMemberFlow(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String startDate = StringUtil.null2String(request
				.getParameter("startDate"));
		String endDate = StringUtil
				.null2String(request.getParameter("endDate"));
		String memberType = StringUtil.null2String(request
				.getParameter("memberType"));
		String memberCode = StringUtil.null2String(request
				.getParameter("memberCode"));
		String optType = StringUtil
				.null2String(request.getParameter("optType"));
		String signloginId = StringUtil.null2String(request
				.getParameter("signLoginId"));
		if (!StringUtil.isEmpty(startDate)) {
			map.put("startDate", startDate.trim());
		}
		if (!StringUtil.isEmpty(endDate)) {
			map.put("endDate", endDate.trim());
		}
		if (!StringUtil.isEmpty(memberType)) {
			map.put("memberType", memberType.trim());
		}
		if (!StringUtil.isEmpty(memberCode)) {
			map.put("memberCode", memberCode.trim());
		}

		// 处理所属销售条件 2014/5/12
		if (StringUtils.isEmpty(signloginId)) {
			List<NodesDto> loginSubNodes = getLoginIdSubNodes();
			map.put("signLoginIds", this.convertNodesToString(loginSubNodes));
		} else {
			map.put("signLoginIds", new String[] { signloginId });
		}

		Page<MemberFlowDTO> page = PageUtils.getPage(request);
		List<MemberFlowDTO> result = new ArrayList<MemberFlowDTO>();
		String export = StringUtil.null2String(request.getParameter("export"));

		// 明细查询
		if (StringUtils.isEmpty(optType)
				|| "1".equalsIgnoreCase(optType.trim())) {
			if (!StringUtils.isEmpty(export)) {
				String fileName = "日交易流量下载";
				result = memberFlowService.queryMemberFlow(map);
				response.setHeader("Expires", "0");
				response.setHeader("Pragma", "public");
				response.setHeader("Cache-Control",
						"must-revalidate, post-check=0, pre-check=0");
				response.setHeader("Cache-Control", "public");
				response.setContentType("application/vnd.ms-excel;charset=utf-8");
				response.setHeader(
						"Content-Disposition",
						"attachment;filename="
								+ new String((fileName + ".xls")
										.getBytes("GBK"), "ISO8859_1"));
				return new ModelAndView(memberFlowexcel).addObject("result",
						result).addObject("optType", 1);
			} else {
				page = memberFlowService.queryMemberFlow(page, map);
				return new ModelAndView(viewName).addObject("page", page)
						.addObject("optType", 1);
			}
		} else {// 汇总查询
			if (!StringUtils.isEmpty(export)) {
				String fileName = "日交易汇总下载";
				result = memberFlowService.queryMemberSummarizingFlow(map);
				response.setHeader("Expires", "0");
				response.setHeader("Pragma", "public");
				response.setHeader("Cache-Control",
						"must-revalidate, post-check=0, pre-check=0");
				response.setHeader("Cache-Control", "public");
				response.setContentType("application/vnd.ms-excel;charset=utf-8");
				response.setHeader(
						"Content-Disposition",
						"attachment;filename="
								+ new String((fileName + ".xls")
										.getBytes("GBK"), "ISO8859_1"));
				return new ModelAndView(memberFlowexcel).addObject("result",
						result).addObject("optType", 2);
			} else {
				page = memberFlowService.queryMemberSummarizingFlow(page, map);
				return new ModelAndView(viewName).addObject("page", page)
						.addObject("optType", 2);
			}
		}
	}

	private List<NodesDto> getLoginIdSubNodes() {
		// 登录人的子节点
		String loginId = SessionUserHolderUtil.getLoginId();
		List<NodesDto> loginSubNodes = userRelationService
				.findAllSubLoginId(loginId);
		return loginSubNodes;
	}

	private String[] convertNodesToString(List<NodesDto> loginSubNodes) {
		if (null == loginSubNodes)
			return null;
		// String strs = "";
		String[] signIds = new String[loginSubNodes.size()];
		NodesDto nodesDto = null;
		for (int i = 0; i < loginSubNodes.size(); i++) {
			nodesDto = loginSubNodes.get(i);
			signIds[i] = nodesDto.getLoginId();
		}
		// for (NodesDto nodesDto : loginSubNodes) {
		// strs += "'"+ nodesDto.getLoginId() +"',";
		// }
		// if(strs.length() > 0)
		// return strs.substring(0,strs.length()-1);
		return signIds;
	}

}
