package com.pay.app.controller.base.matrixcard;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.acc.checkcode.dto.CheckCodeDto;
import com.pay.acc.comm.CheckCodeOriginEnum;
import com.pay.acc.comm.Resource;
import com.pay.app.base.exception.LoginTimeOutException;
import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.app.common.helper.AppConf;
import com.pay.app.service.sms.SmsService;
import com.pay.base.dto.MemberCriteria;
import com.pay.base.service.member.MemberService;
import com.pay.util.FormatDate;
import com.pay.util.StringUtil;

/**
 * 解绑短信服务
 * @author Administrator
 *
 */
public class ReSendUnBindSmsController extends MultiActionController{
	
	private String login;
	
	private String toMobile;
	
	private String retoMobile;
	
	public void setRetoMobile(String retoMobile) {
		this.retoMobile = retoMobile;
	}
	private String toFail;       
	
	private SmsService smsService;
	
    private String reSendSMS="reSendSMS";
    
    private MemberService memberService;
	
	public String LoginName(String type,HttpServletRequest request){
		LoginSession loginSession=null;
		try {
			loginSession = SessionHelper.getLoginSession(request);
		} catch (LoginTimeOutException e) {
			e.printStackTrace();
		}
		String email="";
		String mobileNo="";
		String name = "";
		Long memberCode=Long.parseLong(loginSession.getMemberCode());
		MemberCriteria memberCriteria = memberService.queryMemberCriteriaByMemberCodeNsTx(memberCode);
		String loginName=loginSession.getLoginName();
		if(null!=memberCriteria){
			mobileNo=memberCriteria.getMobileNo();
			email=memberCriteria.getEmail();
		}
		if(StringUtils.isNotEmpty(loginName) && loginName.contains("@")){
			email=loginName;
		}else{
			mobileNo=loginName;
		}
		
		if(StringUtils.isNotEmpty(type) && type.equals("E")){
		    name=email;	
		}else{
			name=mobileNo;
		}
		return name;
   }

	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 加入发短信功能
		if (request.getSession().getAttribute("memberCode") == null || request.getSession().getAttribute("loginName") == null) {
			return new ModelAndView(login);
		}
		String memberCode=request.getSession().getAttribute("memberCode").toString();
		String mobile=LoginName("F",request);
		String verifyName=request.getSession().getAttribute("verifyName").toString();
		String origin=request.getParameter("origin")==null?CheckCodeOriginEnum.MATRIX_UNBIND.getValue():request.getParameter("origin");
		String reSend=request.getParameter("reSendSMS");
		Map<String, Object> paraMap = new HashMap<String, Object>();
		List<String> mobiles = new ArrayList<String>(1);
		mobiles.add(mobile);
		if(mobile!=null && !"".equals(mobile) && smsService.sendSms(memberCode, origin, verifyName, mobiles, Resource.SMSUNBIND_TEMPLATEID)){
            if(null!=reSend && reSend.equals(reSendSMS)) {
            	return new ModelAndView("redirect:/app/reSendUnBindSms.htm?method=toMobile&reSendSMS="+reSend).addObject("pi",mobile);
            }
            return new ModelAndView("redirect:/app/reSendUnBindSms.htm?method=toMobile").addObject("pi",mobile);
		}
		return new ModelAndView(toFail, paraMap).addObject("message","手机号码不正确!");
	}
	
	public ModelAndView toMobile(HttpServletRequest request,
			HttpServletResponse response){
		if (request.getSession().getAttribute("memberCode") == null || request.getSession().getAttribute("loginName") == null) {
			return new ModelAndView(login);
		}
		String memberCode=request.getSession().getAttribute("memberCode").toString();
		String reSend=request.getParameter("reSendSMS");
		CheckCodeDto  checkcode=smsService.getByMemerCode(memberCode,CheckCodeOriginEnum.MATRIX_UNBIND);
		Date createtime=checkcode.getCreateTime();
		Integer minuttes=new Integer(AppConf.get(AppConf.sms_interval));
		String createtime2String=String.valueOf(FormatDate.sceondOfTwoDate(createtime, minuttes));
		if (request.getSession().getAttribute("memberCode") == null || request.getSession().getAttribute("loginName") == null) {
			return new ModelAndView(login);
		}
		String mobile = request.getParameter("pi");
		if(null == mobile || "".equals(mobile)){
			return new ModelAndView(toMobile).addObject("isFail","fail");		
		}
		if(null!=gettimePage(request,mobile,createtime2String,reSend))
		{
		    return this.gettimePage(request,mobile,createtime2String,reSend);			
		}
		return new ModelAndView(toMobile).addObject("mobileNo",mobile);	
	}
	
    private ModelAndView  gettimePage(HttpServletRequest request,String mobile,String endtime,String reSMS)
    {
		if(!StringUtil.isEmpty(endtime))
		{
			if(null!=reSMS && reSMS.equals(reSendSMS)){
				return new ModelAndView(retoMobile).addObject("mobileNo",mobile).addObject("createtime2String",endtime);
				}
			return new ModelAndView(toMobile).addObject("mobileNo",mobile).addObject("createtime2String",endtime);	
		}
		return null;
    }
    
	public void setLogin(String login) {
		this.login = login;
	}

	public void setToMobile(String toMobile) {
		this.toMobile = toMobile;
	}

	public void setToFail(String toFail) {
		this.toFail = toFail;
	}
	public void setSmsService(SmsService smsService) {
		this.smsService = smsService;
	}
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}
}

