package com.pay.app.controller.external;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.pay.base.common.enums.LoginTypeEnum;
import com.pay.base.service.member.MemberService;
import com.pay.util.CheckUtil;
import com.pay.util.SSOSignatureUtil;


public class TemproaryCheckController extends AbstractController{
	private MemberService memberService;
	
    private static final Log logger = LogFactory.getLog(TemproaryCheckController.class);
    
    protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/plain;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = null;
        out = response.getWriter();
        boolean resultObj=doCheckCreateTemporary(request);
        out.print(resultObj);
        out.flush();
        out.close();
		return null;
	}
    
    
    private boolean doCheckCreateTemporary(HttpServletRequest request){
    	boolean result=false;
		String mobile=request.getParameter("memberName");
		String sign = request.getParameter("signMsg");
		
		String text = "memberName="+mobile;
		
		try {
			if(!SSOSignatureUtil.doSignature4Mall(sign, text)){
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		if(StringUtils.isNotBlank(mobile) && CheckUtil.checkPhone(mobile)){
			if(memberService.checkLoginNameByRegister(mobile,LoginTypeEnum.mobile.getValue())){
				result=true;
			}else{
				result=false;
			}
		}else{
			result=false;
		}
		return result;
	}
    
    public MemberService getMemberService() {
		return memberService;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}
}
