/**
* Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
*/
package com.pay.app.controller.external;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.app.common.enums.BspCommEnum;
import com.pay.app.controller.common.UserHelper;
import com.pay.app.filter.AppFilterCommon;
import com.pay.app.service.external.ExternalLoginValidate;
import com.pay.app.service.external.ExternalResourcesHelper;
import com.pay.app.service.external.MessageSignatureVerify;
import com.pay.base.common.enums.MemberStatusEnum;
import com.pay.base.common.enums.ScaleEnum;
import com.pay.base.common.enums.ServiceLevelEnum;
import com.pay.base.dto.MemberDto;
import com.pay.base.model.Operator;
import com.pay.base.service.enterprise.EnterpriseBaseService;
import com.pay.base.service.member.MemberOperateService;
import com.pay.base.service.member.MemberService;
import com.pay.base.service.operator.OperatorServiceFacade;
import com.pay.inf.service.IMessageDigest;
import com.pay.inf.service.impl.MD5MessageDigestImple;
import com.pay.util.SignatureHelper;
import com.pay.util.StringUtil;
import com.pay.util.WebUtil;

/**
 * 外部访问服务
 * @author fjl
 * @date 2011-6-23
 */
public class ExternalRedirectController extends AbstractController {
	
	/** 会员服务 */
	private MemberService memberService;
	
	/** 操作员服务 */
    private OperatorServiceFacade operatorServiceFacade;
	
    private MemberOperateService  memberOperateService;
    
    /** 企业会员基本信息 */
    private EnterpriseBaseService  enterpriseBaseService;
	
    /** 会员在外部系统是否登录验证 */
	private ExternalLoginValidate externalLoginValidate;
	
	/** 会员对应的资源 */
	private ExternalResourcesHelper externalResourcesHelper;
	
	private UserHelper userHelper;
	
	/** 加签，验签 */
//	private SignatureVerify signatureVerify;
	
	private MessageSignatureVerify messageSignatureVerify;
	
	private final Log log = LogFactory.getLog(ExternalRedirectController.class);

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		/*
		步骤：
		（１）验签
		（２）验证client ip 是否相同
		（３）验证会员的合法性（memberCode，uid，originCode，在支付库中的一致性）
		（４）是否登录
		（５）模拟登录，放入session
		（６）跳转到目标地址
		*
		*补充 originCode 改成商户号-->改为交易中心的会员编号。
		*1.先用memberCode 到t_enterprise_base 查是否为交易中心管理员，不是
		*2.用originCode 和 memberCode 到T_MEMBER_RELATION 是否存在父子关系。如果存在继续。
		*/
		
		
		String uid = request.getParameter("uid");
		String userName = request.getParameter("userName");
		String originCode = request.getParameter("originCode");
		ModelAndView mv = getFailureModelView(originCode);
		mv.addObject("uid",uid);
		mv.addObject("userName",userName);
		
		//(1) 验签
		if(!doVerify(request)){
			mv.addObject("errorCode", String.valueOf(BspCommEnum.SignatureVerifyFailure.getCode()));
			mv.addObject("errorMsg", BspCommEnum.SignatureVerifyFailure.getMessage());
			mv.addObject("signMsg",getErrorSignature(uid,userName,String.valueOf(BspCommEnum.SignatureVerifyFailure.getCode()),
					BspCommEnum.SignatureVerifyFailure.getMessage(),originCode));
			return mv;
		}
		
		//(2)ip 验证
		String clientIp = request.getParameter("clientIp");
		String ip = getIpAddr(request);
		//取消ip 验证
		if(!clientIp.equals(ip)){
			log.info("clientIp=" + clientIp +",request get clientIp=" + ip);
			/*
			mv.addObject("errorCode", String.valueOf(BspCommEnum.ClientIpNoIdentical.getCode()));
			mv.addObject("errorMsg", BspCommEnum.ClientIpNoIdentical.getMessage());
			mv.addObject("signMsg",getErrorSignature(uid,userName,String.valueOf(BspCommEnum.ClientIpNoIdentical.getCode()),
					 BspCommEnum.ClientIpNoIdentical.getMessage(),originCode));
			return mv;
			*/
		}
		
		//(3)验证帐户的合法性
		String memberCode = request.getParameter("memberCode");
		MemberDto member = memberService.findByMemberCode(Long.valueOf(memberCode));
		if(!isExistMember(member,memberCode,uid,originCode)){
			mv.addObject("errorCode",String.valueOf(BspCommEnum.MerchantNoExists.getCode()));
			mv.addObject("errorMsg", BspCommEnum.MerchantNoExists.getMessage());
			mv.addObject("signMsg",getErrorSignature(uid,userName,String.valueOf(BspCommEnum.MerchantNoExists.getCode()),
					BspCommEnum.MerchantNoExists.getMessage(),originCode));
			return mv;
		}
		//帐户是否正常
		if(member.getStatus() != MemberStatusEnum.NORMAL.getCode()){
			mv.addObject("errorCode", String.valueOf(BspCommEnum.AccountException.getCode()));
			mv.addObject("errorMsg", BspCommEnum.AccessException.getMessage()
					+":" + MemberStatusEnum.getByCode(member.getStatus()).getMessage());
			mv.addObject("signMsg",getErrorSignature(uid,userName,String.valueOf(BspCommEnum.AccountException.getCode()),
					BspCommEnum.AccessException.getMessage()
					+":" + MemberStatusEnum.getByCode(member.getStatus()).getMessage(),originCode));
			log.info("uid=" + uid + ",memberCode="+memberCode+", 帐户状态不正常");
			return mv;
		}
		
		//(4) 外部系统是否登录
		Map<String,String> loginInfoMap = new HashMap<String,String>();
		loginInfoMap.put("memberCode", memberCode);
		loginInfoMap.put("originCode", originCode);
		loginInfoMap.put("uid", uid);
		loginInfoMap.put("userName", userName);
		loginInfoMap.put("time", String.valueOf(new Date().getTime()));
		
		if(!externalLoginValidate.isLogin(loginInfoMap)){
			mv.addObject("errorCode", String.valueOf(BspCommEnum.NoLoginOrigin.getCode()));
			mv.addObject("errorMsg",  BspCommEnum.NoLoginOrigin.getMessage());
			mv.addObject("signMsg",getErrorSignature(uid,userName,String.valueOf(BspCommEnum.NoLoginOrigin.getCode()),
					 BspCommEnum.NoLoginOrigin.getMessage(),originCode));
			log.info("uid=" + uid + ",memberCode="+memberCode+", 没有登录。");
			return mv;
		}
		
		//目标地址是否存在
		String serviceUrl = request.getParameter("serviceUrl");
		if(StringUtil.isEmpty(serviceUrl)){
			mv.addObject("errorCode",  String.valueOf(BspCommEnum.ServiceUrlNoExists.getCode()));
			mv.addObject("errorMsg", BspCommEnum.ServiceUrlNoExists.getMessage());
			mv.addObject("signMsg",getErrorSignature(uid,userName,String.valueOf(BspCommEnum.ServiceUrlNoExists.getCode()),
					BspCommEnum.ServiceUrlNoExists.getMessage(),originCode));
			return mv;
		}
		
		//(5)模拟登录，放入session
		try {
			mv = doLogin(request,response);
		} catch (Exception e) {

			e.printStackTrace();
			logger.error("商户模拟登录时出错："+e.getMessage(),e);
			mv.addObject("errorCode", String.valueOf(BspCommEnum.AccessException.getCode()));
			mv.addObject("errorMsg", BspCommEnum.AccessException.getMessage());
			mv.addObject("signMsg",getErrorSignature(uid,userName,String.valueOf(BspCommEnum.AccessException.getCode()),
					BspCommEnum.AccessException.getMessage(),originCode));
		}

		return mv;
	}
	
	/**
	 * 验签
	 * @param request
	 * @return
	 */
	private boolean doVerify(HttpServletRequest request){
		//MD5(memberCode=**&uid=**&serviceUrl=**&originCode=300&clientIp=**)
		
		String memberCode = request.getParameter("memberCode");
		String uid = request.getParameter("uid");
		String serviceUrl = request.getParameter("serviceUrl");
		String originCode = request.getParameter("originCode");
		String clientIp = request.getParameter("clientIp");
		String userName = request.getParameter("userName");
		String signMsg = request.getParameter("signMsg");
		try {
			if(null==signMsg||null== memberCode||null==uid||null==serviceUrl
			 ||null ==originCode||null == clientIp || null == userName){
				log.info("模拟登录：验签失败，memberCode=" +memberCode+ ";uid="+uid+";serviceUrl="+serviceUrl
						 +";originCode="+originCode+";clientIp="+clientIp+ "userName=" + userName +";signMsg="+signMsg);
				return false;
			}
			/*
			String sign = "memberCode=" +memberCode+ "&uid="+uid+"&serviceUrl="+serviceUrl
						 +"&originCode="+originCode+"&clientIp="+clientIp;
			
			IMessageDigest md = new MD5MessageDigestImple();
			return md.validateMessageDigest(sign.getBytes(), signMsg);
			*/

			String sign = memberCode + "&" + uid + "&" + userName + "&" + clientIp 
						+ "&" + serviceUrl + "&" + originCode ;
			return messageSignatureVerify.verifyByMerchant(sign, originCode, signMsg);
		}catch(Exception e){
			log.error("模拟登录:验签失败:",e);
			return false;
		}
	}
	
	
	/**
	 * 错误信息加签
	 * @param errorCode
	 * @param errorMsg
	 * @return
	 */
	private String getErrorSignature(String uid,String userName,String errorCode,String errorMsg,String originCode){
		try {
			String signMsg= uid + "&" + userName + "&" + errorCode + "&" + errorMsg;
			
			return messageSignatureVerify.SignatureByMmerchant(signMsg,originCode);
		}  catch (Exception e) {
			e.printStackTrace();
			log.error("加签失败", e);
			return "";
		}
	}
	
	
	/**
	 * 模拟登录，返回
	 * @param request
	 * @param response
	 * @return
	 */
	private ModelAndView doLogin(HttpServletRequest request,
            HttpServletResponse response){
		
		String serviceUrl = request.getParameter("serviceUrl");
		//商户是否已经登录
		String memberCode = request.getParameter("memberCode");
		String uid = request.getParameter("uid");
		String userName = request.getParameter("userName");
		
		try {
			HttpSession session = request.getSession();
			if(session != null){
				LoginSession us = SessionHelper.getLoginSession(request);
				if(us.getMemberCode()!=null){
					if(us.getMemberCode().equals(memberCode)){
						//如果已经是登录状态
						log.debug("模拟登录：请求用户已经登录");
						return getSuccessModelView(serviceUrl);
					}
				}
				session.setMaxInactiveInterval(0);//设置SESSION过期
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		MemberDto member = memberService.findByMemberCode(Long.valueOf(memberCode));

		log.debug("模拟登录:开始初始化用户sesssion");
		Operator operator = operatorServiceFacade.getAdminOperator(member.getMemberCode());
		// 获取上次登录时间
        String loginTime = memberOperateService.queryEpLastLoginTime(member.getMemberCode(), operator.getOperatorId());

		LoginSession loginSession= new LoginSession();
		loginSession.setVerifyName(operator.getName());
		loginSession.setSalutatory(member.getGreeting());
		loginSession.setUpdateDate(member.getUpdateDate());
		loginSession.setActiveStatus(String.valueOf(member.getStatus()));
		loginSession.setMemberCode(String.valueOf(member.getMemberCode()));
		loginSession.setLoginName(member.getLoginName());
		loginSession.setServiceLevel(member.getServiceLevelCode());
		loginSession.setScaleType(ScaleEnum.ENTERPRICE.getValue());
		if(operator.getOperatorId() > 0){
			loginSession.setOperatorExtId(operator.getOperatorId());
			loginSession.setOperatorIdentity(operator.getIdentity());
		}
		request.getSession().setAttribute("memberCode", String.valueOf(member.getMemberCode()));
		request.getSession().setAttribute("loginName", member.getLoginName());
		request.getSession().setAttribute("verifyName", operator.getName());
		request.getSession().setAttribute("epLastLoginTime",loginTime);
		//用户sessionId+ip 加签之后 放入session
		request.getSession().setAttribute("memberSignatureData",SignatureHelper.generateAppSignature(AppFilterCommon.getSignData()));
		
		//
		String originCode = request.getParameter("originCode");
		long fatherMemberCode = Long.parseLong(originCode);
		if(userHelper.handlerSingleBspSession(member.getMemberCode(), fatherMemberCode, loginSession)){

			SessionHelper.setLoginSession(loginSession);

			log.debug("模拟登录：初始化用户session成功，loginName="+member.getLoginName());
			return getSuccessModelView(serviceUrl);
		}
		
		ModelAndView mv = getFailureModelView(originCode);
		mv.addObject("errorCode",String.valueOf(BspCommEnum.IllegalMerchant.getCode()));
		mv.addObject("errorMsg", BspCommEnum.IllegalMerchant.getMessage());
		mv.addObject("signMsg",getErrorSignature(uid,userName,String.valueOf(BspCommEnum.IllegalMerchant.getCode()),
				BspCommEnum.IllegalMerchant.getMessage(),originCode));
		log.debug("模拟登录：初始化用户session失败，loginName="+member.getLoginName());
		return mv;
		
	}
	
	/**
	 * 成功页面的 ModelAndView
	 * @return
	 */
	private ModelAndView getSuccessModelView(String serviceUrl){
		ModelAndView mv = new ModelAndView();
		String url = serviceUrl + ((serviceUrl.indexOf("?")!=-1 ? "&":"?")+"r="+System.currentTimeMillis());
		mv =  new ModelAndView("redirect:"+ url);
		
		return mv;
	}
	
	/**
	 * 错误页面的 ModelAndView
	 * @return
	 */
	private ModelAndView getFailureModelView(String originCode){
		ModelAndView mv = new ModelAndView(getErrorPageUrl(originCode));
		return mv;
	}
	
	/**
	 * 获取客户端ip
	 * @param request
	 * @return
	 */
	private String getIpAddr(HttpServletRequest request) {
	       String clientIp = WebUtil.getIp(request);
	       if (StringUtils.isBlank(clientIp)) {
	   			clientIp = "127.0.0.1";
	   		} 
	       return clientIp; 
	}
	
	/**
	 * 会员是否存在 <br>
	 * (会员编号 、uid、商户号一致)
	 * @param member
	 * @return
	 */
	private boolean isExistMember(MemberDto member,String memberCode,String uid,String originCode){
		if(member == null){
			return false;
		}
		if(!memberCode.equals(String.valueOf(member.getMemberCode())) 
				|| !uid.equals(member.getSsoUserId())
				|| ServiceLevelEnum.TRADING_CENTER.getValue().intValue() != member.getServiceLevelCode()){
			log.info("uid,serviceLevel 不一致！");
			return false;
		}
		/*
		EnterpriseBase eb = enterpriseBaseService.findByMemberCode(member.getMemberCode());
		if(eb == null || !String.valueOf(eb.getMerchantCode()).equals(originCode)){
			return false;
		}
		*/
		return true;
	}


	/**
	 * @return the externalInfoPageUrl
	 */
	public String getErrorPageUrl(String originCode) {
		return "redirect:" + externalResourcesHelper.getErrorPageUrl(originCode);
	}
	

	/**
	 * @param memberService the memberService to set
	 */
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	/**
	 * @param memberOperateService the memberOperateService to set
	 */
	public void setMemberOperateService(MemberOperateService memberOperateService) {
		this.memberOperateService = memberOperateService;
	}

	/**
	 * @param enterpriseBaseService the enterpriseBaseService to set
	 */
	public void setEnterpriseBaseService(EnterpriseBaseService enterpriseBaseService) {
		this.enterpriseBaseService = enterpriseBaseService;
	}

	/**
	 * @param operatorServiceFacade the operatorServiceFacade to set
	 */
	public void setOperatorServiceFacade(OperatorServiceFacade operatorServiceFacade) {
		this.operatorServiceFacade = operatorServiceFacade;
	}
	
	/**
	 * @param externalLoginValidate the externalLoginValidate to set
	 */
	public void setExternalLoginValidate(ExternalLoginValidate externalLoginValidate) {
		this.externalLoginValidate = externalLoginValidate;
	}

	/**
	 * @param externalResourcesHelper the externalResourcesHelper to set
	 */
	public void setExternalResourcesHelper(
			ExternalResourcesHelper externalResourcesHelper) {
		this.externalResourcesHelper = externalResourcesHelper;
	}
	
	/**
	 * @param userHelper the userHelper to set
	 */
	public void setUserHelper(UserHelper userHelper) {
		this.userHelper = userHelper;
	}

	/**
	 * @param messageSignatureVerify
	 */
	public void setMessageSignatureVerify(
			MessageSignatureVerify messageSignatureVerify) {
		this.messageSignatureVerify = messageSignatureVerify;
	}

	public static void main(String[] args) {
		//String s = "memberCode=20000000108&uid=666&serviceUrl=https://localhost:8443/website/corp/myAccount.htm&originCode=515641010005001&clientIp=127.0.0.1";
		String s = "memberCode=20000000098&uid=66&serviceUrl=https://localhost:8443/website/corp/myAccount.htm&originCode=515641010005001&clientIp=127.0.0.1";
		IMessageDigest md = new MD5MessageDigestImple();
		try {
			System.out.println(md.genMessageDigest(s.getBytes("UTF-8")));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
