package com.pay.app.controller.base.platform;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pay.app.base.session.SessionHelper;

@Controller
@RequestMapping("/corp")
public class GetPlatformFirstPage {

	@RequestMapping("/relation.do")
	public String platformFirstTimeLoadPage() {
		return "/base/accountsafemanage/corp/platformmembers";
	}

	@RequestMapping("myPlatAccout.do")
	public String platformFirspage() {
		return "/base/accountsafemanage/corp/platformmembers2";
	}

	@RequestMapping("/refreshdata.do")
	@ResponseBody
	public String platformrefreshFirstTimeLoadPage(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "membercode", required = false) String membercode,
			@RequestParam(value = "operatorId", required = false) String operatorId) {
		SessionHelper.getLoginSession().setMemberCode(membercode);
		request.getSession().setAttribute("memberCode", membercode);
	//	SessionHelper.getLoginSession().setOperatorId(Long.valueOf(operatorId));
		System.out.println(SessionHelper.getLoginSession().getMemberCode());

		return "1";
	}
}
