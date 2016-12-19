package com.pay.app.controller.base.linker;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.base.dto.LinkerDTO;
import com.pay.base.service.linker.LinkerService;
import com.pay.util.StringUtil;

public class AccountLinkerController extends MultiActionController{
	
	private LinkerService linkerService;
	
	/**
	 * 查询联系人个数
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public void linkerCounts(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/plain;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
		String memberCode ="";
		LoginSession loginSession = SessionHelper.getLoginSession();
		if (!StringUtil.isNull(loginSession)) {
			memberCode = loginSession.getMemberCode();
		}
		//统计联系人总数
		LinkerDTO linkerDto = new LinkerDTO();
		linkerDto.setMemberCode(memberCode);
		Integer linkerCount =  linkerService.queryLinkByCount(linkerDto);
		PrintWriter out = null;
        out = response.getWriter();
        out.print(linkerCount);
        out.flush();
        out.close();
		
	}

	public void setLinkerService(LinkerService linkerService) {
		this.linkerService = linkerService;
	}
	
}
