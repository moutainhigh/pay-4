package com.pay.app.controller.external;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.pay.acc.service.member.dto.MemberCreateResult;
import com.pay.base.common.enums.ErrorCodeEnum;
import com.pay.base.common.enums.LoginTypeEnum;
import com.pay.base.service.member.MemberService;
import com.pay.base.service.member.facade.MemberCreateServiceFacade;
import com.pay.inf.exception.AppException;
import com.pay.util.CheckUtil;
import com.pay.util.JSonUtil;
import com.pay.util.SSOSignatureUtil;

/**
 * 提供给外部  （创建临时会员）
 * 
 * @author zengjin
 * @version
 * @data 2010-12-31 上午12:18:23
 * 
 */
public class TemporaryController extends AbstractController{

	private MemberCreateServiceFacade  memberCreateService;
	private MemberService memberService;
    private static final Log logger = LogFactory.getLog(TemporaryController.class);


	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/plain;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = null;
        out = response.getWriter();
        ExternalResult resultObj=doCreateTemporary(request);
        String returnJson =JSonUtil.toJSonString(resultObj);
		out.print(returnJson);
        out.flush();
        out.close();
		return null;
	}

	private ExternalResult doCreateTemporary(HttpServletRequest request){
		ExternalResult resultObj=new ExternalResult();
		String mobile=request.getParameter("memberMobile");
		String sign = request.getParameter("signMsg");
		String text = "memberMobile="+mobile;
		try {
			if(!SSOSignatureUtil.doSignature4Mall(sign, text)){
				resultObj.setResultMsg(ErrorCodeEnum.SIGN_FAILD.getMessage());
				resultObj.setReturnBool(false);
				return resultObj;
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultObj.setResultMsg(ErrorCodeEnum.SIGN_FAILD.getMessage());
			resultObj.setReturnBool(false);
			return resultObj;
		}
		if(StringUtils.isNotBlank(mobile) && CheckUtil.checkPhone(mobile)){
			if(memberService.checkLoginNameByRegister(mobile,LoginTypeEnum.mobile.getValue())){
				try {
					MemberCreateResult me=memberCreateService.doCreateTempMemberRdTx(mobile);
					resultObj.setReturnBool(true);
					resultObj.setResultMsg("success");
					resultObj.setResultObj(me.getMemberCode());
				} catch (AppException e) {
					logger.error("memberCreateService.doCreateTempMemberRdTx throws error",e);
				}
			}else{
				resultObj.setResultMsg(ErrorCodeEnum.MEMBER_LOGINNAME_IS_EXIST.getMessage());
			}
		}else{
			resultObj.setResultMsg(ErrorCodeEnum.MEMBER_LOGINNAME_PHONENAME.getMessage());
		}
		return resultObj;
	}
	
	
	public MemberService getMemberService() {
		return memberService;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public MemberCreateServiceFacade getMemberCreateService() {
		return memberCreateService;
	}

	public void setMemberCreateService(MemberCreateServiceFacade memberCreateService) {
		this.memberCreateService = memberCreateService;
	}
	
	
}
