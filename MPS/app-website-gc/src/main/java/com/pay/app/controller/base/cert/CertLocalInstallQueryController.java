package com.pay.app.controller.base.cert;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.acc.service.cert.CertQueryService;
import com.pay.acc.service.cert.dto.CertManageDto;

public class CertLocalInstallQueryController  extends MultiActionController{
	
//	private CertQueryService certQueryService;
	
	/**
	 * 非登录用户检查本地证书安装状态
	 * 
	 * @see 登录用户
	 * com.pay.app.controller.base.login.MemberController#doCheckLocalCert
	 * 
	 */
	public void doCheckLocalInstall(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/plain;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		String machineIdentifier = request.getParameter("machineId");
		String memberCode = request.getParameter("memberCode");
		String operatorId = request.getParameter("operatorId");

		PrintWriter out = null;
		out = response.getWriter();
		boolean result = false;
//		if (StringUtils.isNotBlank(machineIdentifier)
//				&& StringUtils.isNotBlank(memberCode)
//				&& StringUtils.isNotBlank(operatorId)) {
//			CertManageDto cmDto = certQueryService.queryUsePalce(
//					Long.valueOf(memberCode), Long.valueOf(operatorId),
//					machineIdentifier);
//			if (cmDto != null) {
//				result = true;
//			}
//		}
		out.print(result);
		out.flush();
		out.close();
	}

}
