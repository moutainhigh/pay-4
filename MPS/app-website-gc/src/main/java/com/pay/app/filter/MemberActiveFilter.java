package com.pay.app.filter;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.checkcode.dto.CheckCodeDto;
import com.pay.app.base.session.LoginSession;
import com.pay.app.common.helper.AppConf;
import com.pay.app.common.helper.MessageConvertFactory;
import com.pay.app.service.mail.MailService;
import com.pay.base.common.enums.OrginEnum;
import com.pay.util.CheckUtil;
import com.pay.util.FormatDate;
import com.pay.util.StringUtil;

/**
 *  File: MemberActiveFilter.java
 *  Description:会员激活Filter
 *  Copyright © 2004-2013 hnapay.com . All rights reserved. 
 *  Date     2010-10-8     
 *  Author   jerry_jin     
 *  Changes   
 *  Comment
 */
public class MemberActiveFilter implements Filter {
	
	private static Log logger = LogFactory.getLog(MemberActiveFilter.class);
	
	private static final String SET_PAYPWD_URL="/toSetPaypwd.htm";//设置支付密码URL
	
	private static final String LOGIN_URL = "/outapp.htm";
	
	private static final String ACTIVE_READY_URL = "/toActiveReady.htm";

	private static final String FROM_SET_PAYPWD = "from_set_paypwd";
	
	private static final String TO_RESEND_MOBILE = "/reSendMessage.htm?method=toMobile";
	
	private static final String TO_RESEND_EMAIL = "/reSendEmail.htm?method=toEmail";
	
	private static MailService mailService;
	
	private static boolean isNeedValidate = true;
	
	private final static String RESEND_SECONDS = "60";//重新发送限制时间
	
	//支付密码验证表达式
	private static final Pattern paypwdPattern = Pattern.compile("^(?![a-zA-Z]+$)(?![0-9]+$)[a-zA-Z0-9]{6,20}$");
	
	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain filterChain) throws IOException, ServletException {
		
		HttpServletRequest request1 = (HttpServletRequest) req;
		SafeControllerWrapper request = new SafeControllerWrapper(request1,new String[]{"newpaypassword1","newpaypassword2"});
		
		String from = request.getParameter("from");
		
		String checkCode = request.getParameter("code") == null ? "" : request
				.getParameter("code");
		
		CheckCodeDto ck = mailService.getByCheckCode(checkCode);
		String loginName = "";
		String memberCode = "";
		if(null != ck){
			if(!StringUtil.isEmpty(ck.getEmail())){
				loginName = ck.getEmail();
			}else{
				loginName = ck.getPhone();
			}
			LoginSession loginSession = new LoginSession();
			loginSession.setMemberCode(ck.getMemberCode()+"");
			loginSession.setLoginName(loginName);
			loginSession.setVerifyName("");
			request.getSession().setAttribute("userSession", loginSession);
			request.getSession().setAttribute("loginName",loginName);
			request.getSession().setAttribute("memberCode",ck.getMemberCode()+"");
		}
		
		/************验证是否登录**********/
		if (request.getSession().getAttribute("memberCode") == null
				|| request.getSession().getAttribute("loginName") == null) {
			AppFilterCommon.setCallBackUri(request,AppConf.defaultCallBack,OrginEnum.INDIVIDUAL_LOCAL.getValue(),1);
			req.getRequestDispatcher(LOGIN_URL).forward(request, resp);
			return;
		}
		
		/**判断验证码**/
		if(null!=checkCode){
			//CheckCodeDto ck = mailService.getByMemerCode(memberCode,CheckCodeOriginEnum.ACTIVE_REGISTER.getValue());
			/************验证是否激活过**********/
			if(ck==null){
				AppFilterCommon.setCallBackUri(request,AppConf.defaultCallBack,OrginEnum.INDIVIDUAL_LOCAL.getValue(),1);
				req.getRequestDispatcher(LOGIN_URL).forward(request, resp);
				return;
			}
			if(ck.getStatus() == 2){
				// 跳转到已经验证页面
				req.getRequestDispatcher(ACTIVE_READY_URL).forward(request, resp);
				return;
			}
			if(ck!=null && !checkCode.equals(ck.getCheckCode())){
				if(ck.getEmail()!=null && CheckUtil.checkEmail(ck.getEmail())){
					request.setAttribute("errMsg", MessageConvertFactory.getMessage("activeCode"));
					request.setAttribute("email", loginName);
					req.getRequestDispatcher(TO_RESEND_EMAIL).forward(request, resp);
					return;
				}else if(ck.getPhone()!=null && CheckUtil.checkPhone(ck.getPhone())){
					request.setAttribute("errMsg", MessageConvertFactory.getMessage("activeCode"));
					request.setAttribute("mobile", loginName);
					request.setAttribute("resend", RESEND_SECONDS);
					req.getRequestDispatcher(TO_RESEND_MOBILE).forward(request, resp);
					return;
				}
				
			}else{
				if(ck.getEmail()!=null && CheckUtil.checkEmail(ck.getEmail())){
					Integer day=new Integer(AppConf.get(AppConf.mail_interval));
					long time = FormatDate.sceondOfTwoDate(ck.getCreateTime(), day*24*60);
					if(time==0){
						//如果邮件过期
						request.setAttribute("errMsg", MessageConvertFactory.getMessage("emailValid"));
						request.setAttribute("email", loginName);
						req.getRequestDispatcher(TO_RESEND_EMAIL).forward(request, resp);
						return;
					}
				}else if(ck.getPhone()!=null && CheckUtil.checkPhone(ck.getPhone())){
					Integer minuttes=new Integer(AppConf.get(AppConf.sms_interval));
					long time = FormatDate.sceondOfTwoDate(ck.getCreateTime(), minuttes);
					if(time==0){
						//如果短信过期
						request.setAttribute("errMsg", MessageConvertFactory.getMessage("mobileValid"));
						request.setAttribute("mobile", loginName);
						request.setAttribute("resend", "1");
						req.getRequestDispatcher(TO_RESEND_MOBILE).forward(request, resp);
						return;
					}
				}
			}
		}
		
		/************如果是从设置密码页面跳转过来**********/
		if(StringUtils.isNotBlank(from) && from.equals(FROM_SET_PAYPWD)){
			filterChain.doFilter(request, resp);
			return;
		}
		
		/*******跳转到设置支付密码***********/
		String ccode = request.getParameter("code") == null ? "" : request
				.getParameter("code");
		if (!"".equals(ccode)) {
			//CheckCodeDto ck = mailService.getByMemerCode(memberCode);
			if (ck != null) {
				
				if(ck.getEmail()!=null && CheckUtil.checkEmail(ck.getEmail())){
					request.setAttribute("code", checkCode);
					request.setAttribute("loginName", loginName);
					req.getRequestDispatcher(SET_PAYPWD_URL).forward(request, resp);//跳转到设置支付密码页面
					return;
				}
				
				if(ck.getPhone()!=null && CheckUtil.checkPhone(ck.getPhone())){
					request.setAttribute("code", checkCode);
					request.setAttribute("loginName", loginName);
					req.getRequestDispatcher(SET_PAYPWD_URL).forward(request, resp);
					return;
				}
			}
		}
		
		
		filterChain.doFilter(req, resp);
	}
	
	private void setSessionTimeout(HttpServletRequest request){
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(0);
	}

	
	public void setMailService(MailService mailService) {
		MemberActiveFilter.mailService = mailService;
	}


	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}
	
}
