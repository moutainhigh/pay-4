package com.pay.app.controller.base.cert;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.acc.cert.service.MemberCertService;
import com.pay.acc.checkcode.CheckCodeService;
import com.pay.acc.checkcode.impl.SendMail;
import com.pay.acc.comm.CheckCodeOriginEnum;
import com.pay.acc.comm.Resource;
import com.pay.acc.constant.CertIdCardEnum;
import com.pay.acc.service.member.dto.ApplyCertRequest;
import com.pay.app.base.session.SessionHelper;
import com.pay.app.common.helper.MessageConvertFactory;
import com.pay.app.service.sms.SmsService;
import com.pay.base.model.Operator;
import com.pay.base.service.operator.OperatorServiceFacade;
import com.pay.util.CheckUtil;

public class CertApplyController extends MultiActionController{

//	private CertService  certService;
	private MemberCertService memberCertService;
	private SendMail sendMail;
	/** 操作员服务 */
	private OperatorServiceFacade operatorServiceFacade;
	private SmsService smsService;
	private CheckCodeService  checkCodeService;
	private String indexView;
	private String notindexView;
	private String applyView;
	private String mobileView;
	private String applyCertView;
	private String bindView;
	private String resultView;
	private final static String certBean="certBean";
	private final static int smsCheckSeconds=60;
	
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) {
		Long operatorId=SessionHelper.getOperatorIdBySession();
		Long memberCode=Long.valueOf(SessionHelper.getMemeberCodeBySession());
		Map<String,Object> model = new HashMap<String,Object>();
		
		/*if(certQueryService.isCertUser(memberCode, operatorId)){
			MemberCert memberCert = memberCertService.queryMemberCert(memberCode, operatorId);
			//从session 取当前用户机器中是否安装证书,存放到model
			model.put("memberCert", memberCert);
			if(SessionHelper.isLocalCertUser()){
				model.put("isLocalCertUser", "1");
			}else{
				model.put("isLocalCertUser", "0");
			}
			
			if(operatorId == null || operatorId.longValue() <= 0){
				model.put("isAdmin", "1");
			}else{
				model.put("isAdmin", "0");
			}
			
			if(!SessionHelper.isCertUser()){
				SessionHelper.setCertUser();
			}
			return new ModelAndView(indexView).addAllObjects(model);
		}*/
		//不是证书用户
		if(SessionHelper.isCertUser()){
			SessionHelper.removeCertUser();
		}
		return new ModelAndView(notindexView);
		
	}

	
	/**
	 * 获取绑定的手机号
	 * @return
	 */
	private String getBindMobile(){
		Long operatorId=SessionHelper.getOperatorIdBySession();
		Long memberCode=Long.valueOf(SessionHelper.getMemeberCodeBySession());
		Operator operator=null;
		if(operatorId>0L){
			operator=operatorServiceFacade.getOperatorById(operatorId);
		}else{
			operator=operatorServiceFacade.getAdminOperator(memberCode);
		}
		return operator.getMobile();
	}
	
	/**
	 * 到申请页
	 * @param request
	 * @param response
	 * @param applyCertRequest
	 * @return
	 */
	public ModelAndView apply(HttpServletRequest request,
			HttpServletResponse response,ApplyCertRequest applyCertRequest) {
		Map<String, Object> model = new HashMap<String, Object>();
		String mobile=getBindMobile();
		if(StringUtils.isBlank(mobile)){
			return new ModelAndView(bindView);
		}
		model.put("mobile",mobile);
		model.put("idCardList",CertIdCardEnum.SEARCH_TYPES);
		model.put("applyCertRequest", applyCertRequest);
		return new ModelAndView(applyView).addAllObjects(model);
	}
	
	
	/**
	 * 证书申请第二步
	 * @param request
	 * @param response
	 * @param applyCertRequest
	 * @return
	 */
	public ModelAndView applySecond(HttpServletRequest request,
			HttpServletResponse response,ApplyCertRequest applyCertRequest) {
		ModelAndView mv=new ModelAndView(mobileView);
		Map<String, Object> model = new HashMap<String, Object>();
		String randCode = request.getSession().getAttribute("rand") == null? "": request.getSession().getAttribute("rand").toString();
		String code = request.getParameter("randCode") == null ? "" : request.getParameter("randCode");
		request.getSession().removeAttribute("rand");
		if ("".equals(randCode) || "".equals(code) || !code.equalsIgnoreCase(randCode)) { 
			request.getSession().removeAttribute("rand");
			mv=apply(request,response,applyCertRequest);
			mv.addObject("errMsg",MessageConvertFactory.getMessage("randCode"));
			return mv;
		}
		
		String errMsg=validateCertFrom(applyCertRequest);
		if(StringUtils.isNotBlank(errMsg)){
			mv=apply(request,response,applyCertRequest);
			mv.addObject("errMsg",errMsg);
			return mv;
		}
		String mobile=getBindMobile();
		mv=this.sendSms(mobile, applyCertRequest.getRealName(), SessionHelper.getMemeberCodeBySession());
		applyCertRequest.setMobile(mobile);
		this.setCertSessionBean(request, applyCertRequest);
		model.put("mobile", mobile.replaceAll("(\\d{3})(\\d{4})(\\d{4})", "$1****$3"));
		return mv.addAllObjects(model);
	}
	
	
	
	
	/**
	 * 数字证书申请第三步，验证手机校验码
	 * @param request
	 * @param response
	 * @return
	 */
//	public ModelAndView applyThird(HttpServletRequest request,
//			HttpServletResponse response){
//		ModelAndView mv=new ModelAndView(mobileView); 
//		Map<String, Object> model = new HashMap<String, Object>();
//		String code = request.getParameter("checkCode") == null ? "" : request.getParameter("checkCode");
//		ApplyCertRequest applyCertRequest=this.getCertSessionBean(request);
//		if(applyCertRequest==null)
//			return this.apply(request, response, applyCertRequest);
//		CheckCode checkCode = checkCodeService.getByLastCheckCode(Long.valueOf(SessionHelper.getMemeberCodeBySession()), CheckCodeOriginEnum.CERT_APPLY, 1);
//			//smsService.getByMemerCode(SessionHelper.getMemeberCodeBySession(),CheckCodeOriginEnum.CERT_APPLY);
//		if(checkCode!=null && checkCode.getCheckCode().equals(code)){
//			checkCodeService.updateCheckCodeStatus2Used(SessionHelper.getMemeberCodeBySession()+"", CheckCodeOriginEnum.CERT_APPLY.getValue());
//			return doApply(request,response,applyCertRequest);
//		}
//		model.put("mobile", applyCertRequest.getMobile().replaceAll("(\\d{3})(\\d{4})(\\d{4})", "$1****$3"));
//		model.put("errMsg",MessageConvertFactory.getMessage("cert.checkcode.error"));
//		return mv.addAllObjects(model);
//	}
	
	

	private ModelAndView sendSms(String mobile,String verifyName,String memberCode){
		List<String> mobiles = new ArrayList<String>(1);
		mobiles.add(mobile);
		if(mobile!=null && !"".equals(mobile) && smsService.sendCheckCode(memberCode, CheckCodeOriginEnum.CERT_APPLY.getValue(), mobiles, Resource.APPLY_CERT_SMS)){
            return new ModelAndView(mobileView);
		}
		return new ModelAndView(mobileView);
	}
	
	public void doSendSms(HttpServletRequest request,
            HttpServletResponse response) throws Exception{
            response.setContentType("text/plain;charset=UTF-8");
            response.setHeader("Cache-Control", "no-cache");
            ApplyCertRequest applyCertRequest=this.getCertSessionBean(request);
            int result=0;
            PrintWriter out = null;
            out = response.getWriter();
            if(applyCertRequest!=null){
            	 String mobile =applyCertRequest.getMobile();
                 //String verifyName =request.getParameter("verifyName")==null?"":request.getParameter("verifyName");
                 if(mobile!=null && !"".equals(mobile)){
                	 //CheckCode checkCode = checkCodeService.getByLastCheckCode(Long.valueOf(SessionHelper.getMemeberCodeBySession()), CheckCodeOriginEnum.CERT_APPLY, 1);
                	 int seconds=smsCheckSeconds+1;
                	 //数据库时间与应用不一致
                	 // if(checkCode!=null) seconds=FormatDate.getIntervalSeconds(checkCode.getCreateTime(), new Date());
                	 if(seconds>smsCheckSeconds){
                		List<String> mobiles = new ArrayList<String>(1);
                   		mobiles.add(mobile);
                      	boolean flag= smsService.sendCheckCode(SessionHelper.getMemeberCodeBySession(), CheckCodeOriginEnum.CERT_APPLY.getValue(), mobiles, Resource.APPLY_CERT_SMS);
                      	if(flag) result=1;
                	 }
                	
                 }
            }else
            	result=-1;
           
            out.print(result);
            out.flush();
            out.close();
    }
	
	/**
	 * 申请数字证书，正式提交表单 
	 * @param request
	 * @param response
	 * @return
	 */
//	public ModelAndView doApply(HttpServletRequest request,
//			HttpServletResponse response,ApplyCertRequest applyCertRequest) {
//		Map<String, Object> model = new HashMap<String, Object>();
//		
//		String email=SessionHelper.getLoginSession().getLoginName();
//		String engName=applyCertRequest.getRealName();
//		if(CheckUtil.isChinese(applyCertRequest.getRealName())){
//			engName=FomatString.toPinYin(applyCertRequest.getRealName());
//		}
//		
//		Long memberCode=Long.valueOf(SessionHelper.getMemeberCodeBySession());
//		Long operatorId=SessionHelper.getOperatorIdBySession();
//		CertResultDto  certResultDto=certService.applyCert(applyCertRequest, email, engName,memberCode,operatorId);
//		if(certResultDto.isResultBool()){
//			ApplyCertResponse applyCertResponse=(ApplyCertResponse)certResultDto.getResultObj();
//			model.put("applyCertResponse", applyCertResponse);
//			return new ModelAndView(applyCertView).addAllObjects(model);	
//		}else{
//			return new ModelAndView(mobileView).addObject("errMsg",certResultDto.getErrMsg());
//		}
//	}
	
//	public ModelAndView result(HttpServletRequest request,
//			HttpServletResponse response){
//		Long memberCode=Long.valueOf(SessionHelper.getMemeberCodeBySession());
//		Long operatorId=SessionHelper.getOperatorIdBySession();
//		ApplyCertResponse applyCertResponse=certService.getValidMmeberCert(memberCode, operatorId);
//		return new ModelAndView(resultView).addObject("applyCertResponse",applyCertResponse);
//	}
	
	public ModelAndView faile(HttpServletRequest request,
			HttpServletResponse response){
		return new ModelAndView(resultView).addObject("applyCertResponse",null);
	}
	
	
//	private Long createApplyLog(ApplyCertResponse applyCertResponse,Long memberCode,Long operatorId,int status,int step){
//		MemberCertLogDto memberCertLogDto=new MemberCertLogDto();
//		memberCertLogDto.setMemberCode(memberCode);
//		memberCertLogDto.setOperatorId(operatorId);
//		memberCertLogDto.setRefNo(applyCertResponse.getRefNo());
//		memberCertLogDto.setAuthCode(applyCertResponse.getRefNo());
//		memberCertLogDto.setUserDn(applyCertResponse.getDn());
//		memberCertLogDto.setStatus(status);
//		memberCertLogDto.setStep(step);
//		memberCertLogDto.setSerialNo(certService.getLogSerialNo());
//		return certService.createMemberCertLogRntx(memberCertLogDto);
//	}
	
	
//	public void doSign(HttpServletRequest request,
//            HttpServletResponse response) throws Exception{
//            response.setContentType("text/plain;charset=UTF-8");
//            response.setHeader("Cache-Control", "no-cache");
//            String p10Str =request.getParameter("p10Str")==null?"":request.getParameter("p10Str");
//            Long memberCode=Long.valueOf(SessionHelper.getMemeberCodeBySession());
//    		Long operatorId=SessionHelper.getOperatorIdBySession();
//            PrintWriter out = null;
//            out = response.getWriter();
//            String result="";
//            CertResultDto certResultDto= certService.certSign( p10Str, memberCode, operatorId);
//            if(certResultDto.isResultBool()){
//            	ApplyCertResponse applyCertResponse=(ApplyCertResponse)certResultDto.getResultObj();
//            	result=applyCertResponse.getSignCertPem();
//            	this.createApplyLog(applyCertResponse, memberCode, operatorId, 1, StepEnum.MAKECERT.getValue());
//            }
//            out.print(result);
//            out.flush();
//            out.close();
//    }
	
//	public void doValid(HttpServletRequest request,
//            HttpServletResponse response) throws Exception{
//            response.setContentType("text/plain;charset=UTF-8");
//            response.setHeader("Cache-Control", "no-cache");
//            String machineIdentifier =request.getParameter("machineId")==null?"":request.getParameter("machineId");
//            Long memberCode=Long.valueOf(SessionHelper.getMemeberCodeBySession());
//    		Long operatorId=SessionHelper.getOperatorIdBySession();
//            PrintWriter out = null;
//            out = response.getWriter();
//            boolean result=false;
//            
//            if(certService.isSignCert(memberCode, operatorId) &&
//            		certService.doMemberCertRntx(machineIdentifier, memberCode, operatorId)){
//            	this.removeCertSessionBean(request);
//            	SessionHelper.setCertUser();
//            	SessionHelper.setLocalInstallCert();
//            	result=true;
//            	LoginSession loginSession =   SessionHelper.getLoginSession();
//            	
//            	Map<String,String> map = new HashMap<String,String>();
//            	map.put("memberName", loginSession.getVerifyName());
//        		map.put("requestDate", DateUtil.formatDateTime("yyyy年M月d日 HH:mm:ss"));
//        		sendMail.sendMail(Arrays.asList(loginSession.getLoginName()), "数字证书申请成功通知", Resource.APPLY_CERT_SUCESS_EMAIL, map);
//
//            }
//            out.print(result);
//            out.flush();
//            out.close();
//    }
	

	
	private String validateCertFrom(ApplyCertRequest applyCertRequest){
		String errMsg=null;
		if(applyCertRequest==null){
			errMsg=MessageConvertFactory.getMessage("cert.apply.submit.error");
		}else if(StringUtils.isBlank(applyCertRequest.getRealName())){
			errMsg=MessageConvertFactory.getMessage("cert.apply.name.empty");
		}else if(!(CheckUtil.isChinese(applyCertRequest.getRealName()) || isEnglishChar(applyCertRequest.getRealName()))){
			errMsg=MessageConvertFactory.getMessage("cert.apply.name.error");
		}else if(StringUtils.isBlank(applyCertRequest.getIdCardTypeNum())){
			errMsg=MessageConvertFactory.getMessage("cert.apply.cerType.empty");
		}else if(StringUtils.isBlank(applyCertRequest.getIdCardNo())){
			errMsg=MessageConvertFactory.getMessage("cert.apply.idCardNo.empty");
		}else if(StringUtils.isBlank(applyCertRequest.getUsePlace())){
			errMsg=MessageConvertFactory.getMessage("cert.apply.useplace.empty");
		}
		
		
		return errMsg;
	}
	
	boolean isEnglishChar(String str) {
		if (StringUtils.isEmpty(str)) {
			return false;
		}
		java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("[a-zA-Z]+");
		java.util.regex.Matcher match = pattern.matcher(str);
		return match.matches();
	}
	
	private void setCertSessionBean(HttpServletRequest request,ApplyCertRequest applyCertRequest){
		request.getSession().setAttribute(certBean, applyCertRequest);
	}
	
	private ApplyCertRequest getCertSessionBean(HttpServletRequest request){
		ApplyCertRequest applyCertRequest=request.getSession().getAttribute(certBean)==null?null
					:(ApplyCertRequest)request.getSession().getAttribute(certBean);
		return applyCertRequest;
	}
	
	private void removeCertSessionBean(HttpServletRequest request){
		request.getSession().removeAttribute(certBean);
	}
	
	public void setCheckCodeService(CheckCodeService checkCodeService) {
		this.checkCodeService = checkCodeService;
	}


	public void setNotindexView(String notindexView) {
		this.notindexView = notindexView;
	}

	public void setIndexView(String indexView) {
		this.indexView = indexView;
	}

	public void setOperatorServiceFacade(OperatorServiceFacade operatorServiceFacade) {
		this.operatorServiceFacade = operatorServiceFacade;
	}

	public void setSmsService(SmsService smsService) {
		this.smsService = smsService;
	}

	public void setApplyView(String applyView) {
		this.applyView = applyView;
	}

	public void setMobileView(String mobileView) {
		this.mobileView = mobileView;
	}
	
	
	public void setApplyCertView(String applyCertView) {
		this.applyCertView = applyCertView;
	}


	public void setBindView(String bindView) {
		this.bindView = bindView;
	}


	public void setResultView(String resultView) {
		this.resultView = resultView;
	}


	public void setMemberCertService(MemberCertService memberCertService) {
		this.memberCertService = memberCertService;
	}


	public void setSendMail(SendMail sendMail) {
		this.sendMail = sendMail;
	}
	
}
