/*
 * pay.com Inc.
 * Copyright (c) 2006-2010 All Rights Reserved.
 */
package com.pay.app.controller.base.operator;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.acc.comm.Resource;
import com.pay.acc.service.account.AccountInfoService;
import com.pay.acc.service.account.constantenum.AcctTypeEnum;
import com.pay.acc.service.account.dto.MaResultDto;
import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.app.common.util.ValidateCodeUtils;
import com.pay.app.service.operator.BindMobileService;
import com.pay.app.service.operator.statusEnum.BindMobleSmsType;
import com.pay.base.model.Operator;
import com.pay.base.service.operator.OperatorServiceFacade;
import com.pay.util.RSAHelper;

/**
 * 
 * desc: 操作员解除绑定手机 号
 * @author DaiDeRong
 * 2011-11-21 上午11:24:09
 *
 */
public class UnBindMobileController extends MultiActionController {

	private static final String UN_BIND_MOBILE_TOKEN = "UN_BIND_MOBILE_TOKEN"; 
	private BindMobileService bindMobileService;
	private OperatorServiceFacade operatorServiceFacade;
	private AccountInfoService accountInfoService;
	
	

	
	/**
	 * 输入手机等 信息页面 
	 */
	private String confirmUnBindView;
	
	/**
	 * 绑定结果页面 
	 */
	private String unBindResultView;
	
	
	
	
	/**
	 * @param operatorServiceFacade the operatorServiceFacade to set
	 */
	public void setOperatorServiceFacade(OperatorServiceFacade operatorServiceFacade) {
		this.operatorServiceFacade = operatorServiceFacade;
	}

	/**
	 * @param bindMobileService the bindMobileService to set
	 */
	public void setBindMobileService(BindMobileService bindMobileService) {
		this.bindMobileService = bindMobileService;
	}

	/**
	 * @param accountInfoService the accountInfoService to set
	 */
	public void setAccountInfoService(AccountInfoService accountInfoService) {
		this.accountInfoService = accountInfoService;
	}


	
	


	
	
	
	//////////////流程方法/////////////
	


	/**
	 * @param confirmUnBindView the confirmUnBindView to set
	 */
	public void setConfirmUnBindView(String confirmUnBindView) {
		this.confirmUnBindView = confirmUnBindView;
	}

	/**
	 * @param unBindResultView the unBindResultView to set
	 */
	public void setUnBindResultView(String unBindResultView) {
		this.unBindResultView = unBindResultView;
	}

	/**
	 * 检测手机 是否可以解绑
	 * @author DaiDeRong
	 * 2011-11-17 上午11:30:28
	 */
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response){
		
		//判断是否安装证书,如果有安装,则当前机器上需要安装证书
		//TODO:判断是否安装证书
		LoginSession loginSession =   SessionHelper.getLoginSession();
//		boolean  isCert = certQueryService.isCertUser(Long.valueOf(loginSession.getMemberCode()), SessionHelper.getOperatorIdBySession());
		boolean  isCert = false;
		//告诉用户客户端证书未安装
//		boolean  isMachineInstall = ServletRequestUtils.getStringParameter(request, "install", "0").equals("1");
		if(isCert ){
			//需要安装证书才能解除
			return new ModelAndView(unBindResultView).addObject("status", false)
					.addObject("errorMsg", "您是数字证书用户，不能解除手机绑定，若要解除请先取消数字证书！");
		}
		
		Operator operator =  operatorServiceFacade.getAdminOperator(Long.valueOf(loginSession.getMemberCode()));
		String mobile = operator.getMobile();
		//发送手机 验证码
		bindMobileService.sendConfirmCode(mobile, Long.valueOf(loginSession.getMemberCode()), BindMobleSmsType.UNBIND_SMS);
		
		
		String token = UUID.randomUUID()+"";
		request.getSession().setAttribute(UN_BIND_MOBILE_TOKEN, token);
		return new ModelAndView(confirmUnBindView)
		.addObject("mobile", mobile.replaceAll("(\\d{3})(\\d{4})(\\d{4})", "$1****$2"))
		.addObject("tk", token);
	}
	
	
	
	
	/**
	 * 输入手机 号，去接收验证码
	 * @author DaiDeRong
	 * 2011-11-17 上午11:30:28
	 * @throws IOException 
	 */
	public ModelAndView reSendCode(HttpServletRequest request,HttpServletResponse response) throws IOException{
	
		response.setContentType("text/plain;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		
		String result = "N";
		PrintWriter out = response.getWriter();
		if(ValidateCodeUtils.validateCode(request, UN_BIND_MOBILE_TOKEN, "tk")){
			LoginSession loginSession =   SessionHelper.getLoginSession();
			Operator operator =  operatorServiceFacade.getAdminOperator(Long.valueOf(loginSession.getMemberCode()));
			String mobile = operator.getMobile();
			bindMobileService.sendConfirmCode(mobile, Long.valueOf(loginSession.getMemberCode()), BindMobleSmsType.UNBIND_SMS);
			result = "Y";
		}
		else{
			result = "页面已失效了";
		}
		out.print(result);
		out.flush();
		out.close();
		return null;
		
	}
	
	
	
	
	
	public ModelAndView confirmInfo(HttpServletRequest request,HttpServletResponse response){
		
		
		String errorMsg = "";
		String token  =  (String) request.getSession().getAttribute(UN_BIND_MOBILE_TOKEN);  
		if(token==null){
			errorMsg = "页面已过期!";
			return new ModelAndView(unBindResultView).addObject("status", false).addObject("errorMsg", errorMsg);
		}
		LoginSession loginSession =   SessionHelper.getLoginSession();
		Operator operator =  operatorServiceFacade.getAdminOperator(Long.valueOf(loginSession.getMemberCode()));
		String mobile = operator.getMobile();
		mobile = mobile.replaceAll("(\\d{3})(\\d{4})(\\d{4})", "$1****$2");
		if(! ValidateCodeUtils.validateCode(request, "randCode")){
			errorMsg = "验证码不正确，请重新输入";
			request.getSession().setAttribute(UN_BIND_MOBILE_TOKEN, token);
			return new ModelAndView(confirmUnBindView).addObject("errorRandCode", errorMsg).addObject("mobile", mobile)
					.addObject("tk", token);
		}
	
		String payPwd  = ServletRequestUtils.getStringParameter(request, "payPwd","");
		String hasSecurity = request.getParameter("hasSecurity")==null?"":request.getParameter("hasSecurity");
		
		//如果是安全控件输入的密码 要用私钥解密
		if(Resource.ORGIN_FROM_SECURITY.equals(hasSecurity)){
            try{
            	payPwd = RSAHelper.getRSAString(payPwd);
            }catch(Exception e){
            	e.printStackTrace();
            }
        }
		
		//验证支付密码
		MaResultDto maResultDto =  accountInfoService.doVerifyPayPassword(Long.valueOf(loginSession.getMemberCode()),  AcctTypeEnum.BASIC_CNY.getCode(), payPwd, loginSession.getOperatorId());
		
		if(maResultDto.getResultStatus() != 1){
			request.getSession().setAttribute(UN_BIND_MOBILE_TOKEN, token);
			return new ModelAndView(confirmUnBindView).addObject("errorPayPwd", "支付密码不正确!").addObject("mobile", mobile)
					.addObject("tk", token);
		}
	
		
		//查找数据库验证码
		String validateCode = ServletRequestUtils.getStringParameter(request, "validateCode","");
				if(! bindMobileService.validateConfirmCode(Long.valueOf(loginSession.getMemberCode()), validateCode,BindMobleSmsType.UNBIND_SMS)){
					request.getSession().setAttribute(UN_BIND_MOBILE_TOKEN, token);
					return new ModelAndView(confirmUnBindView).addObject("errorCheckCode", "手机 验证码输入不正确!")
							.addObject("mobile", mobile).addObject("tk", token);
				}
		//更新手机号
		Operator op = operatorServiceFacade.getAdminOperator(Long.valueOf( loginSession.getMemberCode()));
		op.setMobile(" ");//不需要手机号传空格
		boolean status = operatorServiceFacade.updateOperator(op) == 1;
		
			request.getSession().removeAttribute(UN_BIND_MOBILE_TOKEN);
		return new ModelAndView(unBindResultView).addObject("status", status);
	} 
	
    
	

	
}
