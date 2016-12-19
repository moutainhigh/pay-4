package com.pay.poss.platformmembers.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.memberrelation.model.MemberRelation;
import com.pay.poss.platformmembers.dto.PlatformMembersDTO;
import com.pay.poss.platformmembers.service.PlatformMembersService;
import com.pay.util.JSonUtil;
import com.pay.util.StringUtil;



/**   
* @Title: PlatformMembersController.java 
* @Package com.pay.poss.memberrelation.controller 
* @Description: 会员关联查询管理
* @author cf
* @date 2011-10-18 上午10:35:21 
* @version V1.0   
*/
public class PlatformMembersController extends MultiActionController {
	private static final Log log = LogFactory.getLog(PlatformMembersController.class);

	private PlatformMembersService platformMembrsService;
	private String queryView;
	private String listView;
	
	public ModelAndView init(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(queryView);
	}

	@RequestMapping(params = "method=findByCriteria", method = RequestMethod.POST)
	public ModelAndView findByCriteria(HttpServletRequest request,
			HttpServletResponse response, MemberRelation dto)
			throws Exception {
		Page page = PageUtils.getPage(request);
		Map<String, String> criteria = new HashMap<String, String>();
		if(request.getParameter("fatherMemberCode") != null && !StringUtil.isEmpty(request.getParameter("fatherMemberCode"))) {
			criteria.put("fatherMemberCode", request.getParameter("fatherMemberCode").trim());
		}
		if(request.getParameter("sonMemberCode") != null && !StringUtil.isEmpty(request.getParameter("sonMemberCode"))) {
			criteria.put("sonMemberCode", request.getParameter("sonMemberCode").trim());
		}
		if(request.getParameter("status") != null && !StringUtil.isEmpty(request.getParameter("status"))) {
			criteria.put("status", request.getParameter("status").trim());
		}
		
		List<PlatformMembersDTO> info = platformMembrsService.findByCriteria(criteria, page);//.queryMemberRelationByCondition(dto, page);
		page.setResult(info);
	
		return new ModelAndView(listView)
			.addObject("page", page)
			.addObject("fatherMemberCode", criteria.get("fatherMemberCode"))
			.addObject("sonMemberCode", criteria.get("sonMemberCode"))
			.addObject("status", criteria.get("status"));
	}
	
	@RequestMapping(params = "method=findUnpprovalPlatformMembers", method = RequestMethod.POST)
	public ModelAndView findUnpprovalPlatformMembers(HttpServletRequest request,
			HttpServletResponse response, MemberRelation dto)
			throws Exception {
		Map<String, String> criteria = new HashMap<String, String>();
		if(request.getParameter("status") != null && !StringUtil.isEmpty(request.getParameter("status"))) {
			criteria.put("status", request.getParameter("status").trim());
		}
		
		List<PlatformMembersDTO> info = platformMembrsService.findByCriteria(criteria);//.queryMemberRelationByCondition(dto, page);
		int length = info == null ? 0 : info.size();
		response.getOutputStream().write((""+length).getBytes());
		
		return null;
	}
	
	@RequestMapping(params = "method=updatePlatformMembersStatus", method = RequestMethod.POST)
	public ModelAndView updatePlatformMembersStatus(HttpServletRequest request,
			HttpServletResponse response, MemberRelation dto)
			throws Exception {
		Page page = PageUtils.getPage(request);
		
		log.info("Update platform member " + request.getParameter("id") + " from status " + request.getParameter("srcStatus") + " to status " + request.getParameter("targetStatus"));
		
		Map<String, String> result = platformMembrsService.updatePlatformMembers(
				request.getParameter("id"), request.getParameter("srcStatus"), request.getParameter("targetStatus"), page);
		String resultString = JSonUtil.bean2json(result);
		response.setContentType("application/json;charset=UTF-8");
		response.getOutputStream().write(resultString.getBytes("UTF-8"));
		return null;
	}

	public String getQueryView() {
		return queryView;
	}


	public void setQueryView(String queryView) {
		this.queryView = queryView;
	}


	public String getListView() {
		return listView;
	}


	public void setListView(String listView) {
		this.listView = listView;
	}

	public PlatformMembersService getPlatformMembrsService() {
		return platformMembrsService;
	}

	public void setPlatformMembrsService(
			PlatformMembersService platformMembrsService) {
		this.platformMembrsService = platformMembrsService;
	}
}
