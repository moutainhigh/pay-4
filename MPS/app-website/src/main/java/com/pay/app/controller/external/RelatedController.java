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
import com.pay.inf.service.IMessageDigest;
import com.pay.util.JSonUtil;
import com.pay.util.RSAHelper;
import com.pay.util.SSOSignatureUtil;

/**
 * 提供给外部  （社区跟支付用户关联）
 * 
 * @author zengjin
 * @version
 * @data 2010-12-31 上午12:18:23
 * 
 */
public class RelatedController  extends AbstractController{
	private static final String POST_METHOD_PREFIX = "POST";
	private MemberService memberService;
    private IMessageDigest iMessageDigest; 


	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/plain;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = null;
        out = response.getWriter();
        ExternalResult resultObj=doRelated(request);
		String returnJson =JSonUtil.toJSonString(resultObj);
		out.print(returnJson);
        out.flush();
        out.close();
		return null;
	}
	
	
	private ExternalResult doRelated(HttpServletRequest request){
		ExternalResult resultObj=new ExternalResult();
		 String method=request.getMethod();
		 //String ssoUserId=request.getParameter("buyerId");
		try {
			if(true){
					String loginName=request.getParameter("memberName");
					String loginPwd=request.getParameter("memberPwd");
					String signMsg=request.getParameter("signMsg");
					if(StringUtils.isBlank(loginName)){
						resultObj.setResultMsg(ErrorCodeEnum.MEMBER_LOGINNAME_EMPTY.getMessage());
					}else if(StringUtils.isBlank(loginPwd)){
						resultObj.setResultMsg(ErrorCodeEnum.MEMBER_LOGINPWD_EMPTY.getMessage());
					}else if(StringUtils.isBlank(signMsg)){
						resultObj.setResultMsg(ErrorCodeEnum.SIGN_MSGISNULL.getMessage());
					}else{
						//验签数据
						String sign = "memberName="+loginName+"&memberPwd="+loginPwd;
						if(SSOSignatureUtil.doSignature4Mall(signMsg,sign)){
							MemberDto mdto=memberService.findMemberByLoginName(loginName);
							loginPwd = RSAHelper.getRSAString(loginPwd);
							if(mdto==null){
								resultObj.setResultMsg(ErrorCodeEnum.MEMBER_LOGINNAME_PWD_ERROR.getMessage());
							}else if(!mdto.getLoginPwd().equals(iMessageDigest.genMessageDigest(loginPwd.getBytes()))){
								resultObj.setResultMsg(ErrorCodeEnum.MEMBER_LOGINNAME_PWD_ERROR.getMessage());
							}else{
								resultObj.setReturnBool(true);
								resultObj.setResultObj(mdto.getMemberCode());
							}
						}else{
							resultObj.setResultMsg(ErrorCodeEnum.SIGN_FAILD.getMessage());
						}
					}
			}else{
				resultObj.setResultMsg(ErrorCodeEnum.SYSTEM_NO_POST.getMessage());
			}
		} catch (Exception e) {
			resultObj.setResultMsg(e.getLocalizedMessage());
			e.printStackTrace();
		}
		return resultObj;
	}
	
	
//	private ExternalResult doRelated(HttpServletRequest request){
//		ExternalResult resultObj=new ExternalResult();
//		 String method=request.getMethod();
//		 String ssoUserId=request.getParameter("buyerId");
//		if(POST_METHOD_PREFIX.equals(method) && CheckUtil.isNumber(ssoUserId)){
//			MemberDto mdto=memberService.queryMemberBySsoUserid(ssoUserId);
//			if(mdto!=null){
//				resultObj.setResultMsg(ErrorCodeEnum.MEMBER_RELATED_IS_EXIST.getMessage());
//			}else{
//				String loginName=request.getParameter("lname");
//				String loginPwd=request.getParameter("lpwd");
//				
//				if(StringUtils.isBlank(loginName)){
//					resultObj.setResultMsg(ErrorCodeEnum.MEMBER_LOGINNAME_EMPTY.getMessage());
//				}else if(StringUtils.isNotBlank(loginPwd)){
//					resultObj.setResultMsg(ErrorCodeEnum.MEMBER_LOGINPWD_EMPTY.getMessage());
//				}else{
//					mdto=memberService.findMemberByLoginName(loginName);
//					if(mdto==null){
//						resultObj.setResultMsg(ErrorCodeEnum.MEMBER_LOGINNAME_PWD_ERROR.getMessage());
//					}else if(!mdto.getLoginPwd().equals(loginPwd)){
//						resultObj.setResultMsg(ErrorCodeEnum.MEMBER_LOGINNAME_PWD_ERROR.getMessage());
//					}else if(memberService.updateMemberSsoUserid(mdto.getMemberCode(), ssoUserId)){
//						resultObj.setResultFlag(true);
//						resultObj.setResultMsg(ErrorCodeEnum.MEMBER_RELATED_SUCCESS.getMessage());
//					}else{
//						resultObj.setResultMsg(ErrorCodeEnum.MEMBER_RELATED_FAIL.getMessage());
//					}
//				}
//				
//			}
//		}else{
//			resultObj.setResultMsg(ErrorCodeEnum.SYSTEM_NO_POST.getMessage());
//		}
//		return resultObj;
//	}

	public MemberService getMemberService() {
		return memberService;
	}


	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public IMessageDigest getiMessageDigest() {
		return iMessageDigest;
	}


	public void setiMessageDigest(IMessageDigest iMessageDigest) {
		this.iMessageDigest = iMessageDigest;
	}
}
