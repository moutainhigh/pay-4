package com.pay.app.controller.base.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

import com.pay.acc.service.member.MemberQueryService;
import com.pay.app.base.session.LoginSession;
import com.pay.app.common.helper.MessageConvertFactory;
import com.pay.app.filter.AppFilterCommon;
import com.pay.base.common.enums.SecurityLvlEnum;
import com.pay.base.dto.IndividualInfoDto;
import com.pay.base.dto.MemberDto;
import com.pay.base.dto.matrixcard.MatrixCardDto;
import com.pay.base.service.individual.IndividualInfoService;
import com.pay.base.service.matrixcard.login.MatrixCardLoginService;
import com.pay.base.service.member.MemberService;
import com.pay.util.SSOSignatureUtil;
import com.pay.util.SignatureHelper;

/**
 * 模拟登录
 * @author jerry_jin
 *
 */
public class Login4SSOController extends MultiActionController {
	
	private MatrixCardLoginService matrixCardLoginService;
	
	private MemberQueryService memberQueryService;
	
	private IndividualInfoService individualInfoService;
	
	private MemberService memberService;
	
	static final String REDIRECT_LOGIN = "redirect:logout.htm?method=out&mtype=1";
	
	static final String REDIRECT_INDEX = "index.htm";
	
	static long LAST_LOAD_TIME = 0L;
	
	private final Log log = LogFactory.getLog(Login4SSOController.class);
	
	public ModelAndView redirect4Mall(HttpServletRequest request,HttpServletResponse response){
		
		//验签
		if(doSignature(request)){
			log.debug("模拟登录验签成功!");
			String urlKey = request.getParameter("method");
			try {
				String prefix = this.getMethodNameResolver().getHandlerMethodName(request);
				String jumpURL = MessageConvertFactory.getMessage(prefix+"."+urlKey);
				jumpURL = jumpURL==null?REDIRECT_INDEX:jumpURL;
				jumpURL+=((jumpURL.indexOf("?")!=-1?"&":"?")+"r="+System.currentTimeMillis());
				log.debug("模拟登录成功：跳转URL,"+jumpURL);
				return doLogin(request,response,jumpURL);
			} catch (NoSuchRequestHandlingMethodException e) {
				log.error("模拟登录方法名映射错误:",e);
			}
		}
		
		return new ModelAndView(REDIRECT_LOGIN);
	}
	
	/**
	 * 验签
	 * @param request
	 * @return
	 */
	private boolean doSignature(HttpServletRequest request){
		String memberCode = request.getParameter("memberCode");
		String method = request.getParameter("method");
		String signMsg = request.getParameter("signMsg");
		try {
			if(null== memberCode||null==signMsg||null==method){
				log.debug("模拟登录：验签失败，memberCode="+memberCode+";signMsg="+signMsg);
				return false;
			}
			String sign = "method="+method+"&memberCode="+memberCode;
			return SSOSignatureUtil.doSignature4Mall(signMsg,sign);
		}catch(Exception e){
			log.error("模拟登录:验签失败:",e);
			return false;
		}
	}
	
	
	private ModelAndView doLogin(HttpServletRequest request,
            HttpServletResponse response,String jumpUrl){
		ModelAndView result = null;
		String memberCode = request.getParameter("memberCode");
		HttpSession session = request.getSession();
		Object o_userSession = null;
		if((o_userSession=session.getAttribute("userSession"))!=null){
			if(o_userSession instanceof LoginSession){
				LoginSession us = (LoginSession)o_userSession;
				if(us.getMemberCode()!=null){
					if(us.getMemberCode().equals(memberCode)){
						//如果已经是登录状态
						log.debug("模拟登录：请求用户已经登录");
						result =  new ModelAndView("redirect:"+jumpUrl);
						return result;
					}
				}
			}
			session.setMaxInactiveInterval(0);//设置SESSION过期
		}
		
		if(memberCode==null){
			return new ModelAndView(REDIRECT_LOGIN);
		}
		
		MemberDto member = memberService.findByMemberCode(Long.valueOf(memberCode));

		if(member!=null){
			IndividualInfoDto individualInfo = individualInfoService.queryIndividualInfoByMemberCode(member.getMemberCode());
			if(individualInfo!=null){
				//如果会员状态正常
				if(member.getStatus()==1){
					log.debug("模拟登录:开始初始化用户sesssion");
					 // 获取上次登录时间
			        String loginTime = memberQueryService.queryLastLoginTime(member.getMemberCode());
					LoginSession loginSession= new LoginSession();
					loginSession.setVerifyName(individualInfo.getName());
					loginSession.setSalutatory(member.getGreeting());
					loginSession.setUpdateDate(member.getUpdateDate());
					loginSession.setActiveStatus(String.valueOf(member.getStatus()));
					loginSession.setMemberCode(String.valueOf(member.getMemberCode()));
					loginSession.setLoginName(member.getLoginName());
					loginSession.setServiceLevel(member.getServiceLevelCode());
					request.getSession().setAttribute("memberCode", String.valueOf(member.getMemberCode()));
					request.getSession().setAttribute("loginName", member.getLoginName());
					request.getSession().setAttribute("verifyName", individualInfo.getName());
					request.getSession().setAttribute("epLastLoginTime",loginTime);
					MatrixCardDto mc=matrixCardLoginService.findByBindMemberCode(member.getMemberCode());
					// 口令卡登录
					if(mc!=null){
					    loginSession.setSerialNo(mc.getSerialNo());
					    loginSession.setSecurityLvl(SecurityLvlEnum.UNMAXTRIX.getValue());
					}else{
					    loginSession.setSecurityLvl(SecurityLvlEnum.NORMAL.getValue());
					}
					
					request.getSession().setAttribute("userSession", loginSession);
					//用户sessionId+ip 加签之后 放入session
					request.getSession().setAttribute("memberSignatureData",SignatureHelper.generateAppSignature(AppFilterCommon.getSignData()));
					result = new ModelAndView("redirect:"+jumpUrl);
					log.debug("模拟登录：初始化用户session成功，loginName="+member.getLoginName());
					return result;
				}
			}
		}
		result = new ModelAndView(REDIRECT_LOGIN);
		return result;
	}

	public void setMatrixCardLoginService(
			MatrixCardLoginService matrixCardLoginService) {
		this.matrixCardLoginService = matrixCardLoginService;
	}

	public void setMemberQueryService(MemberQueryService memberQueryService) {
		this.memberQueryService = memberQueryService;
	}

	public void setIndividualInfoService(IndividualInfoService individualInfoService) {
		this.individualInfoService = individualInfoService;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}
	
	
	
}
