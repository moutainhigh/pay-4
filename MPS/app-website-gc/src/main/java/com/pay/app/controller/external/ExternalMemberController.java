package com.pay.app.controller.external;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.pay.base.common.enums.ErrorCodeEnum;
import com.pay.base.dto.MemberDto;
import com.pay.base.service.member.MemberService;
import com.pay.util.JSonUtil;

public class ExternalMemberController extends AbstractController{
	private MemberService memberService;
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/plain;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = null;
        out = response.getWriter();
		String memberCode=request.getParameter("memberCode");
		ExternalResult resultObj=new ExternalResult();
		if(StringUtils.isNotBlank(memberCode) && StringUtils.isNumeric(memberCode)){
			MemberDto mdto=memberService.findByMemberCode(Long.valueOf(memberCode));
			if(mdto==null){
				resultObj.setResultMsg(ErrorCodeEnum.MEMBER_LOGINNAME_NULL_ERROR.getMessage());
			}else{
				resultObj.setReturnBool(true);
				resultObj.setResultObj(mdto.getLoginName());
			}
		}else{
			resultObj.setResultMsg(ErrorCodeEnum.MEMBER_CODE_ERROR.getMessage());
		}
		
		String returnJson =JSonUtil.toJSonString(resultObj);
		out.print(returnJson);
        out.flush();
        out.close();
		return null;
	}
	
	 public MemberService getMemberService() {
			return memberService;
		}

		public void setMemberService(MemberService memberService) {
			this.memberService = memberService;
		}
}
