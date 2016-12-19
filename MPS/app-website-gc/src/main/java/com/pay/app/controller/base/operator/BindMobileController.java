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
 * desc: 操作员绑定手机号
 * @author DaiDeRong
 * 2011-11-17 上午11:24:09
 *
 */
public class BindMobileController extends MultiActionController {

	private static final String BIND_MOBILE_TOKEN = "BIND_MOBILE_TOKEN"; 
	private static final String BIND_MOBILE = "BIND_MOBILE"; 
	private BindMobileService bindMobileService;
	private OperatorServiceFacade operatorServiceFacade;
	private AccountInfoService accountInfoService;
	private boolean bindNew = false;
	
	public BindMobleSmsType getBindMobleSmsType(){
		if(bindNew){
			return BindMobleSmsType.MODIFY_BIND_SMS;
		}
		return BindMobleSmsType.BIND_SMS;
	}
	

	/**
	 * @param isBindNew the isBindNew to set
	 */
	public void setBindNew(boolean isBindNew) {
		this.bindNew = isBindNew;
	}





	/**
	 * 输入手机号页面 
	 */
	private String inputMobileView;
	
	/**
	 * 输入手机等 信息页面 
	 */
	private String confirmMobileView;
	
	/**
	 * 绑定结果页面 
	 */
	private String bindResultView;
	
	
	
	
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




	/**
	 * @param inputMobileView the inputMobileView to set
	 */
	public void setInputMobileView(String inputMobileView) {
		this.inputMobileView = inputMobileView;
	}

	/**
	 * @param confirmMobileView the confirmMobileView to set
	 */
	public void setConfirmMobileView(String confirmMobileView) {
		this.confirmMobileView = confirmMobileView;
	}

	/**
	 * @param bindResultView the bindResultView to set
	 */
	public void setBindResultView(String bindResultView) {
		this.bindResultView = bindResultView;
	}
   
	
	
	
	//////////////流程方法/////////////
	

	/**
	 * 到输入手机 号页面 
	 * @author DaiDeRong
	 * 2011-11-17 上午11:30:28
	 */
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response){
		
		
		if(! bindNew){
			String mobile = operatorServiceFacade.getBindMobileByMeberCodeOperatorId(Long.valueOf(SessionHelper.getMemeberCodeBySession()), Long.valueOf(SessionHelper.getOperatorIdBySession()));
			if(mobile!=null){
				//已经绑定手机 了
				return new ModelAndView(bindResultView).addObject("status", false).addObject("flag","1").addObject("errorMsg", "已经绑定手机了，号码是"+mobile);
			}
		}
		
		String token = UUID.randomUUID()+"";
		request.getSession().setAttribute(BIND_MOBILE_TOKEN, token);
		return new ModelAndView(inputMobileView).addObject("tk", token);
	}
	
	/**
	 * 输入手机 号，去接收验证码
	 * @author DaiDeRong
	 * 2011-11-17 上午11:30:28
	 */
	public ModelAndView sendCode(HttpServletRequest request,HttpServletResponse response){
		String mobile = ServletRequestUtils.getStringParameter(request, "mobile","");
		String token = UUID.randomUUID()+"";
		if(ValidateCodeUtils.validateCode(request, BIND_MOBILE_TOKEN, "tk")){
			request.getSession().setAttribute(BIND_MOBILE_TOKEN, token);
			LoginSession loginSession =   SessionHelper.getLoginSession();
			bindMobileService.sendConfirmCode(mobile, Long.valueOf(loginSession.getMemberCode()), getBindMobleSmsType());
			request.getSession().setAttribute(BIND_MOBILE, mobile);
			
			return new ModelAndView(confirmMobileView)
			.addObject("mobile", mobile.replaceAll("(\\d{3})(\\d{4})(\\d{4})", "$1****$3"))
			.addObject("tk", token);
		}else{
			return index(request, response).addObject("tkError", "页面已过期");
		}
		
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
		if(ValidateCodeUtils.validateCode(request, BIND_MOBILE_TOKEN, "tk")){
			LoginSession loginSession =   SessionHelper.getLoginSession();
			String mobile = (String )request.getSession().getAttribute(BIND_MOBILE);
			bindMobileService.sendConfirmCode(mobile, Long.valueOf(loginSession.getMemberCode()), getBindMobleSmsType());
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
		String token  =  (String) request.getSession().getAttribute(BIND_MOBILE_TOKEN);  
		if(token==null){
			errorMsg = "页面已过期!";
			return new ModelAndView(bindResultView).addObject("status", false).addObject("errorMsg", errorMsg);
		}
		String mobile = ((String) request.getSession().getAttribute(BIND_MOBILE)).replaceAll("(\\d{3})(\\d{4})(\\d{4})", "$1****$3");
		if(! ValidateCodeUtils.validateCode(request, "randCode")){
			errorMsg = "验证码不正确，请重新输入";
			request.getSession().setAttribute(BIND_MOBILE_TOKEN, token);
			return new ModelAndView(confirmMobileView).addObject("errorRandCode", errorMsg).addObject("mobile", mobile)
					.addObject("tk", token);
		}
		LoginSession loginSession =   SessionHelper.getLoginSession();
		String validateCode = ServletRequestUtils.getStringParameter(request, "validateCode","");
		String payPwd = ServletRequestUtils.getStringParameter(request, "payPwd","");
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
			request.getSession().setAttribute(BIND_MOBILE_TOKEN, token);
			return new ModelAndView(confirmMobileView).addObject("errorPayPwd", "支付密码不正确!").addObject("mobile", mobile)
					.addObject("tk", token);
		}
		
		//查找数据库验证码
		if(! bindMobileService.validateConfirmCode(Long.valueOf(loginSession.getMemberCode()), validateCode,getBindMobleSmsType())){
			request.getSession().setAttribute(BIND_MOBILE_TOKEN, token);
			return new ModelAndView(confirmMobileView).addObject("errorCheckCode", "手机 验证码输入不正确!")
					.addObject("mobile", mobile).addObject("tk", token);
		}
		
		//更新手机号
		String bindMobile = (String) request.getSession().getAttribute(BIND_MOBILE);
		Operator op = operatorServiceFacade.getAdminOperator(Long.valueOf( loginSession.getMemberCode()));
		op.setMobile(bindMobile);
		boolean status = operatorServiceFacade.updateOperator(op) == 1;
		if(status){
			logger.info(op.getName()+":"+op.getMemberCode()+"绑定手机 成功--> "+bindMobile );
			try{
				bindMobileService.sendBindSuccessEmail(loginSession.getLoginName(), loginSession.getUserName(), bindMobile);
				logger.info("绑定手机 发送邮件成功--> "+loginSession.getLoginName() );
			}catch (Exception e) {
				e.printStackTrace();
				logger.info("绑定手机 发送邮件失败--> "+loginSession.getLoginName() );
			}
		}else{
			logger.info(op.getName()+":"+op.getMemberCode()+"绑定手机 失败--> "+bindMobile );
		}
		
		
		request.getSession().removeAttribute(BIND_MOBILE);
		request.getSession().removeAttribute(BIND_MOBILE_TOKEN);
		return new ModelAndView(bindResultView).addObject("status", status);
	} 
	
	
	
	
	
	
	
	
    
	

	
}
