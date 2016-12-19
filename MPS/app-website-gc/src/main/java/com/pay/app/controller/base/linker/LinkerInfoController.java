/**
 *  File: LinkerInfoController.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-21   lihua     Create
 *
 */
package com.pay.app.controller.base.linker;

/**
 * 联系人相关控制
 */
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.app.base.api.annotation.HasFeature;
import com.pay.app.base.api.annotation.OperatorPermission;
import com.pay.app.base.api.common.constans.CutsConstants;
import com.pay.app.base.common.pagination.PageUtil;
import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.base.dto.LinkerDTO;
import com.pay.base.service.linker.LinkerService;
import com.pay.util.StringUtil;
@HasFeature({CutsConstants.FEATURE_MAXTRIX,CutsConstants.FEATURE_NORMAL})
public class LinkerInfoController extends MultiActionController {

	private LinkerService linkerService;

	// 成功
	private String success;
	// 失败
	private String error;
	// 跳转至添加联系人页面
	private String addlinkerpage;
	
	@OperatorPermission(operatPermission = "OPERATOR_LINKER_QUERY")
	public ModelAndView linkerinfo(HttpServletRequest request,
			HttpServletResponse response, LinkerDTO linkerDto) throws Exception {

		String memberCode = "";
		String loginName = "";
		ModelAndView mv = null;
		LoginSession loginSession = SessionHelper.getLoginSession();
		if (!StringUtil.isNull(loginSession)) {
			memberCode = loginSession.getMemberCode();
			loginName = loginSession.getLoginName();
		}
		request.getSession()
				.setAttribute("linkerlist", new ArrayList<Object>());
		int totalCount = 0;
		List<LinkerDTO> linkerinfolist = null;
		int pager_offset = 1;
		int page_size = 5;
		if (!StringUtil.isNull(memberCode)) {
			try{
			linkerDto.setMemberCode(memberCode);
			pager_offset = request.getParameter("pager_offset") == null ? 1
					: Integer.parseInt(request.getParameter("pager_offset"));
			}catch(NumberFormatException e){
				pager_offset = 1;
			}
			totalCount = null == linkerService.queryLinkByCount(linkerDto) ? 0
					: linkerService.queryLinkByCount(linkerDto);

			PageUtil pu = new PageUtil(pager_offset, page_size, totalCount);// 分页处理
			int beginNum = pu.getStartIndex();// 开始
			linkerDto.setBeginNum(beginNum);
			linkerDto.setEndNum(beginNum + page_size);
			linkerinfolist = linkerService.queryLinkByPage(linkerDto);
			mv = new ModelAndView(success);
			mv.addObject("pu", pu);
			mv.addObject("loginName", loginName);
			mv.addObject("linkerinfolist", linkerinfolist);
		}
		return mv;
	}

	/**
	 * 批量删除
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@OperatorPermission(operatPermission = "OPERATOR_LINKER_DELETE")
	public ModelAndView deletelinkerbatch(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String[] linker = request.getParameterValues("linker");
		boolean result = true;
		if (null != linker && linker.length > 0) {
			for (int i = 0; i < linker.length; i++) {
				linkerService.delete(Long.valueOf(linker[i]
						.toString()));
			}
		}
		String url = "/app/linkerinfo.htm";
        LoginSession loginSession = SessionHelper.getLoginSession();
        if (!StringUtil.isNull(loginSession)) {
            if(loginSession.getScaleType() == 2){
                url = "/corp/linkerinfo.htm";
            }
        }
            
        return new ModelAndView("redirect:"+url);

	}

	/**
	 * 单个删除联系人
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@OperatorPermission(operatPermission = "OPERATOR_LINKER_DELETE")
	public ModelAndView deletelinker(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id").toString();
		linkerService.delete(Long.valueOf(id));
		String url = "/app/linkerinfo.htm";
		LoginSession loginSession = SessionHelper.getLoginSession();
		if (!StringUtil.isNull(loginSession)) {
			if (loginSession.getScaleType() == 2) {
				url = "/corp/linkerinfo.htm";
			}
		}

		return new ModelAndView("redirect:" + url);
	}

	/**
	 * 跳转至添加联系人页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@OperatorPermission(operatPermission = "OPERATOR_LINKER_INSERT")
	public ModelAndView toaddlinkerpage(HttpServletRequest request,
			HttpServletResponse response) {

		ArrayList<LinkerDTO> linkerlist = new ArrayList<LinkerDTO>();
		request.getSession().setAttribute("linkerlist", linkerlist);
		return new ModelAndView(addlinkerpage);
	}
	
	public void setLinkerService(LinkerService linkerService) {
		this.linkerService = linkerService;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public void setError(String error) {
		this.error = error;
	}

	public void setAddlinkerpage(String addlinkerpage) {
		this.addlinkerpage = addlinkerpage;
	}

}
