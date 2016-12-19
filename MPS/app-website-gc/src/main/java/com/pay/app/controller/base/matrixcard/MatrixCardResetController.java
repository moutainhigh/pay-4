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

import com.pay.acc.checkcode.CheckCodeService;
import com.pay.acc.checkcode.dao.model.CheckCode;
import com.pay.acc.checkcode.dto.CheckCodeDto;
import com.pay.acc.comm.CheckCodeOriginEnum;
import com.pay.acc.comm.Resource;
import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.app.common.helper.AppConf;
import com.pay.app.common.helper.MessageConvertFactory;
import com.pay.app.filter.SafeControllerWrapper;
import com.pay.app.service.sms.SmsService;
import com.pay.base.common.helper.matrixcard.MatrixCardToken;
import com.pay.base.common.helper.matrixcard.MatrixCardTransInfoValStatus;
import com.pay.base.common.helper.matrixcard.MatrixCardTransType;
import com.pay.base.common.helper.matrixcard.MatrixCardVfyRequest;
import com.pay.base.common.helper.matrixcard.MatrixCardVfyRequestType;
import com.pay.base.common.helper.matrixcard.MatrixCardVfyType;
import com.pay.base.common.helper.matrixcard.OperatorInfo;
import com.pay.base.dto.MemberCriteria;
import com.pay.base.dto.ResultDto;
import com.pay.base.dto.matrixcard.transmgr.MatrixCardTransInfoDto;
import com.pay.base.service.acct.AcctService;
import com.pay.base.service.matrixcard.IMatrixCardService;
import com.pay.base.service.matrixcard.reset.MatrixCardResetService;
import com.pay.base.service.member.MemberService;
import com.pay.util.FormatDate;
import com.pay.util.StringUtil;
import com.pay.util.UUIDUtil;
import com.pay.util.WebUtil;

public class MatrixCardResetController extends MultiActionController {

	
	private IMatrixCardService matrixCardService;
	private MatrixCardResetService matrixCardResetService;
	private SmsService smsService;
	private CheckCodeService mailService;
	private MemberService memberService;

	//切换acc库
	private AcctService acctService;

	private String messageView;

	private String step1View;

	private String step2View;

	private String successView;
	
	private String emailView;
	
	private String rephoneView;
	
	private String phoneView;
	
	private String resetView;
	
	private String loginView;
	
	private String reSendSMS="reSendSMS";
	
	public ModelAndView jumpView(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LoginSession loginSession = SessionHelper.getLoginSession();
		String loginName = loginSession.getLoginName();
		Long memberCode=Long.parseLong(loginSession.getMemberCode());
		MemberCriteria memberCriteria=memberService.queryMemberCriteriaByMemberCodeNsTx(memberCode);
		String mobileNo=null;
		String email=null;
		if(null!=memberCriteria){
			mobileNo=memberCriteria.getMobileNo();
			email=memberCriteria.getEmail();
		}
		if(loginName.contains("@")){
			email=loginName;
		}else{
			mobileNo=loginName;
		}
		String type=request.getParameter("type");	
		if(StringUtils.isNotEmpty(type)){
			loginName=type;
		}
		String token = UUIDUtil.uuid();
		request.getSession().setAttribute("token",token);
		if(loginName.contains("@")){
			return new ModelAndView(step2View).addObject("mobileNo",mobileNo)
			  .addObject("email",email).addObject("token",token);
		}else{			
			return new ModelAndView(step1View).addObject("mobileNo",mobileNo)
			.addObject("email",email).addObject("token",token);
		}
	}
	
	
	public String LoginName(String type,HttpServletRequest request){
		LoginSession loginSession=SessionHelper.getLoginSession();
		
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
	
	/**
	 * 找回密码前台第一步,验证相关数据合法性
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView index(HttpServletRequest req, HttpServletResponse response) throws Exception {
		SafeControllerWrapper request = new SafeControllerWrapper(req,new String[]{"opassword"});
		LoginSession loginSession = SessionHelper.getLoginSession();
		Long memberCode = Long.parseLong(loginSession.getMemberCode());
		String token = request.getParameter("form_token");
		String token2 = UUIDUtil.uuid();
		if(token.equals(request.getSession().getAttribute("token"))){
			request.getSession().setAttribute("token", token2);
		}else{
			return new ModelAndView(messageView).addObject("errorMessage","不能重复提交");
		}
		Long uId=null;
		ResultDto resultDtos = null;
		String loginPwd =request.getParameter("opassword");
			if (StringUtils.isNotEmpty(loginPwd)) {// 验证登录密码是否正确(状态:修改)
				if (null != memberCode) {
					resultDtos = memberService.verifyLoginPwdNsTx(memberCode, loginPwd);
				}
			}else{
				return new ModelAndView(messageView).addObject("errorMessage",MessageConvertFactory.getMessage("loginPasswordMsg"));
			}
			
			if (null !=resultDtos && null == resultDtos.getErrorCode()) {   // 验证登录密码正确
				ModelAndView returnView = null;
				returnView = new ModelAndView();
				Map<String, Object> params = new HashMap<String, Object>();
				
				if (!matrixCardService.isBindByMemberCode(memberCode)) {
					params.put("message",MessageConvertFactory.getMessage("noMatrixCardUser"));
					returnView.addAllObjects(params);
					returnView.setViewName(messageView);
					return returnView;
				}

				// 直接到达新口令卡页面
				MatrixCardTransInfoDto infoDto = null;
				String type =request.getParameter("type");
				String mobile="";
				String email ="";
				if(StringUtils.isNotEmpty(type) && type.equals("F")){
					mobile=LoginName(type, request);
				}else if(StringUtils.isNotEmpty(type) && type.equals("E")){
					email=LoginName(type,request);
				}else{
					return new ModelAndView(messageView).addObject("errorMessage","数据输入错误，请稍后再试.");
				}
				
				String ip=WebUtil.getClientIP(request);
				
				ResultDto resultDto = matrixCardResetService.getValidResetTrans(Long.parseLong(loginSession.getMemberCode()));
				if (null != resultDto && null == resultDto.getErrorCode()) {
					infoDto = (MatrixCardTransInfoDto) resultDto.getObject();
				}

				if (infoDto == null) {
					// 重新开始一个重置事务
					infoDto = beginTrans(mobile, uId, memberCode, ip);
					if (infoDto == null) {
						params.put("message", MessageConvertFactory.getMessage("matrixCardResetException"));
						returnView.addAllObjects(params);
						returnView.setViewName(messageView);
						return returnView;
					}
				}
				params.put("transId", infoDto.getTransId());
				String resultView = request.getParameter("view");
				String reSend = request.getParameter("reSendSMS");
				if( null != resultView && resultView.equals("email")){
					if(StringUtils.isNotEmpty(email) && sendEmail(email,request)){
						if(null!=reSend && reSend.equals(reSendSMS)) {
	                        return new ModelAndView("redirect:/app/matrixCardReset.htm?method=toEmail&resendMail="+reSend)
	                        		.addObject("me",email).addObject("token",token2);
						}
						 return new ModelAndView("redirect:/app/matrixCardReset.htm?method=toEmail")
						 			.addObject("me",email).addObject("token",token2);
					}
				}else{
					if(mobile!=null && !"".equals(mobile) && sendSmsCode(mobile,request)){
			            if(null!=reSend && reSend.equals(reSendSMS)) {			            
			            	return new ModelAndView("redirect:/app/matrixCardReset.htm?method=toMobile&reSendSMS="+reSend)
			            			.addObject("pi",mobile).addObject("token",token2);	          
			            }
			            return new ModelAndView("redirect:/app/matrixCardReset.htm?method=toMobile")
			            			.addObject("pi",mobile).addObject("token",token2);				            
					}
				}
				returnView.addAllObjects(params);
				returnView.addObject("loginName",loginSession.getLoginName());
				return returnView;
			} else {      //登录密码输入错误
				return new ModelAndView(messageView).addObject("errorMessage",MessageConvertFactory.getMessage("loginPasswrodError"));
			}
	}


	public ModelAndView toEmail(HttpServletRequest request,
			HttpServletResponse response) {
		String token = request.getParameter("token");
		if(token.equals(request.getSession().getAttribute("token"))){
			request.getSession().setAttribute("token",UUIDUtil.uuid());
		}else{
			return new ModelAndView(messageView).addObject("errorMessage","不能重复提交");
		}
		String resendMail="resendMail";
		if (request.getSession().getAttribute("memberCode") == null
				|| request.getSession().getAttribute("loginName") == null) {
			return new ModelAndView(loginView);
		}
		String email = request.getParameter("me");
		if(null == email || "".equals(email)){
			return new ModelAndView(emailView).addObject("isFail","fail");
		}
		if(null!=request.getParameter("resendMail") && resendMail.equals(request.getParameter("resendMail")))
		{
			return new ModelAndView(emailView).addObject("email", email);	
		}
		return new ModelAndView(emailView).addObject("email", email);
	}
	
	public ModelAndView toMobile(HttpServletRequest request,
			HttpServletResponse response){
		String token = request.getParameter("token");
		if(token.equals(request.getSession().getAttribute("token"))){
			request.getSession().setAttribute("token",UUIDUtil.uuid());
		}else{
			return new ModelAndView(messageView).addObject("errorMessage","不能重复提交");
		}
		if (request.getSession().getAttribute("memberCode") == null || request.getSession().getAttribute("loginName") == null) {
			return new ModelAndView(loginView);
		}
		String memberCode=request.getSession().getAttribute("memberCode").toString();
		String reSend=request.getParameter("reSendSMS");
		CheckCodeDto  checkcode=smsService.getByMemerCode(memberCode,CheckCodeOriginEnum.MATRIX_RESET);
		if(checkcode==null){
			return new ModelAndView(phoneView).addObject("isFail","fail");// 失败
		}
		Date createtime=checkcode.getCreateTime();
		Integer minuttes=new Integer(AppConf.get(AppConf.sms_interval));
		String createtime2String=String.valueOf(FormatDate.sceondOfTwoDate(createtime, minuttes));
		if (request.getSession().getAttribute("memberCode") == null || request.getSession().getAttribute("loginName") == null) {
			return new ModelAndView(loginView);
		}
		String mobile=request.getParameter("pi");
		if(null == mobile || "".equals(mobile)){
			return new ModelAndView(phoneView).addObject("isFail","fail");
		}
		if(null!=gettimePage(request,mobile,createtime2String,reSend))
		{
		return this.gettimePage(request, mobile,createtime2String,reSend);			
		}
		return new ModelAndView(phoneView).addObject("mobileNo",mobile);	
	}
	

    private ModelAndView  gettimePage(HttpServletRequest request,String mobile,String endtime,String reSMS)
    {
		if(!StringUtil.isEmpty(endtime))
		{
			if(null!=reSMS && reSMS.equals(reSendSMS)){
				return new ModelAndView(rephoneView).addObject("mobileNo",mobile).addObject("createtime2String",endtime);
				}
			return new ModelAndView(phoneView).addObject("mobileNo",mobile).addObject("createtime2String",endtime);	
		}
		return null;
    }
	
	private String getdiffTime(Date createtime) {
			Integer minuttes = new Integer(AppConf.get(AppConf.sms_interval));
			String createtime2String = String.valueOf(FormatDate.sceondOfTwoDate(
					createtime, minuttes));
			if (!StringUtil.isEmpty(createtime2String)) {
				return createtime2String;
			} else {
				return "0";
			}
	}
	
	/**发送EMAIL
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public boolean sendEmail(String email,HttpServletRequest request) throws Exception {
		LoginSession loginSession = SessionHelper.getLoginSession();
		String memberCode = loginSession.getMemberCode();
		List<String> emailList=new ArrayList<String>();
		emailList.add(email);
		CheckCodeDto checkCode=new CheckCodeDto();
		checkCode.setMemberCode(Long.parseLong(memberCode));
		checkCode.setOrigin(CheckCodeOriginEnum.MATRIX_RESET.getValue());
		checkCode.setEmail(email);
		return mailService.sendMail(loginSession.getVerifyName(), emailList, checkCode, Resource.MAIL_URL_MATRIXCARDRESET,
				Resource.MATRIXCARDRESET_TEMPLATEID , Resource.MAIL_MATRIXCARD_RESET);
	}
	/**
	 * 发送验证码
	 * 
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public boolean sendSmsCode(String mobiles,HttpServletRequest request) throws Exception {
		LoginSession loginSession = SessionHelper.getLoginSession();
		String memberCode = loginSession.getMemberCode();		
		List<String> mobilesList = new ArrayList<String>();
		String origin=request.getParameter("origin")==null?CheckCodeOriginEnum.MATRIX_RESET.getValue():request.getParameter("origin");
		mobilesList.add(mobiles);
		try {
			smsService.sendSms(memberCode, origin, memberCode, mobilesList, Resource.SMSRESET_TEMPLATEID);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 验证EMAIL
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView CheckEmail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return validCode(request, response, "email");
	}

	/**
	 * 验证手机验证码
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView CheckMobile(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return validCode(request, response, "mobile");
	}

	/**
	 * 验证手机及EMAIL
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView validCode(HttpServletRequest request, HttpServletResponse response, String type) throws Exception {
		ModelAndView returnView = new ModelAndView();
		Map<String, Object> params = new HashMap<String, Object>();
		LoginSession loginSession = SessionHelper.getLoginSession();
		MatrixCardTransInfoDto transInfoDto = null;
		Long uId = Long.parseLong(loginSession.getMemberCode());
		Long memberCode = Long.parseLong(loginSession.getMemberCode());
		ResultDto rsDto = null;
		if (!matrixCardService.isBindByMemberCode(memberCode)) {
			params.put("message", MessageConvertFactory.getMessage("noMatrixCardUser"));
			returnView.addAllObjects(params);
			returnView.setViewName(messageView);
			return returnView;
		}	
		if ("email".equals(type)) {
			String checkCode=request.getParameter("checkCode");
			rsDto = matrixCardResetService.validateEmailReset(memberCode,checkCode);
			if (null != rsDto && null == rsDto.getErrorCode()) {
				transInfoDto = (MatrixCardTransInfoDto) rsDto.getObject();
			}
			if (null == transInfoDto) {
				// TODO 失败处理
				returnView.setViewName(emailView);
				returnView.addObject("errMsg", MessageConvertFactory.getMessage("randExpired"));
				returnView.addObject("email",loginSession.getLoginName());
				return returnView;
			}
		}
		else {// 验证手机
			String validateCode = StringUtil.null2String(request.getParameter("validateCode"));
			String endtime="0";
			if(!"".equals(validateCode)){
				CheckCodeDto ck =  smsService.getByMemerCode(memberCode.toString(),CheckCodeOriginEnum.MATRIX_RESET);
				if (ck != null) {
					endtime= this.getdiffTime(ck.getCreateTime());
				}
			}
			if (StringUtil.isEmpty(validateCode)) {
				returnView.addObject("createtime2String",endtime);
				returnView.addObject("mobileNo",request.getParameter("mobile"));
				returnView.setViewName(phoneView);
				returnView.addObject("errMsg", MessageConvertFactory.getMessage("randExpired"));
				return returnView;
			}
			rsDto = matrixCardResetService.validateSmsCode(uId, validateCode, memberCode);
			if (null != rsDto && null == rsDto.getErrorCode()) {
				// 验证手机验证码
				transInfoDto = (MatrixCardTransInfoDto) rsDto.getObject();
				params.put("validateCode", validateCode);
			}
			if (null == transInfoDto) {
				// TODO 失败处理
				returnView.addObject("createtime2String",endtime);
				returnView.addObject("mobileNo",request.getParameter("mobile"));
				returnView.setViewName(phoneView);
				returnView.addObject("errMsg", MessageConvertFactory.getMessage("randExpired"));
				return returnView;
			}
		}

		MatrixCardVfyRequest mcVfyRequest = new MatrixCardVfyRequest();
		mcVfyRequest.setRequestDate(new Date());
		mcVfyRequest.setRequestId(transInfoDto.getTransId());
		mcVfyRequest.setRequestIp(WebUtil.getClientIP(request));
		mcVfyRequest.setRequestMemberCode(memberCode);
		mcVfyRequest.setRequestType(MatrixCardVfyRequestType.RESET.getValue());
		mcVfyRequest.setRequestUid(uId);
		mcVfyRequest.setTransId(transInfoDto.getTransId());
		mcVfyRequest.setVfyType(MatrixCardVfyType.NEWCARD_VFY.getValue());
		ResultDto resultDto = matrixCardResetService.requestRstBind(mcVfyRequest);
		if (null != resultDto && resultDto.getErrorCode() == null) {
			MatrixCardToken token = (MatrixCardToken) resultDto.getObject();
			params.put("tokenStr", token.getToken());
			params.put("pos1", token.getXy0());
			params.put("pos2", token.getXy1());
			params.put("pos3", token.getXy2());
			params.put("hasValidated", "1");
			returnView.setViewName(resetView);
			returnView.addAllObjects(params);
		}
		else {
			params.put("message", resultDto.getErrorMsg());
			returnView.setViewName(messageView);
			returnView.addAllObjects(params);
		}
		return returnView;
	}

	/**
	 * 重置口令卡
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView resetMatrixCard(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView returnView = new ModelAndView();
		Map<String, Object> params = new HashMap<String, Object>();
		LoginSession loginSession = SessionHelper.getLoginSession();
		Long memberCode = Long.parseLong(loginSession.getMemberCode());
		// TODO LoginSession对象中加入操作员ID loginSession.getU_id()
		Long uId = 0L;
		if (!matrixCardService.isBindByMemberCode(memberCode)) {
			params.put("message", MessageConvertFactory.getMessage("noMatrixCardUser"));
			returnView.setViewName(messageView);
			returnView.addAllObjects(params);
			return returnView;
		}
		String serialNo = StringUtil.null2String(request.getParameter("serialNo")).trim();
		String pos1Value = StringUtil.null2String(request.getParameter("posValue0")).trim();
		String pos2Value = StringUtil.null2String(request.getParameter("posValue1")).trim();
		String pos3Value = StringUtil.null2String(request.getParameter("posValue2")).trim();
		String tokenStr = StringUtil.null2String(request.getParameter("tokenStr"));
		// params.put("tokenStr", token.getToken());
		// 提交参数检查
		if (StringUtil.isEmpty(tokenStr)) {
			returnView = index(request, response);
			returnView.addObject("message", MessageConvertFactory.getMessage("matrixCardRequest"));
			returnView.setViewName(messageView);
			returnView.addAllObjects(params);
			return returnView;
		}

		// 必填参数检查
		if (StringUtil.isEmpty(serialNo) || StringUtil.isEmpty(pos1Value) || StringUtil.isEmpty(pos2Value) || StringUtil.isEmpty(pos3Value)) {
			returnView = index(request, response);
			returnView.addObject("message", MessageConvertFactory.getMessage("matrixCardInputTrue"));
			returnView.setViewName(messageView);
			returnView.addAllObjects(params);
			return returnView;
		}
		serialNo = serialNo.replaceAll(" ", "");
		MatrixCardToken matrixCardToken = new MatrixCardToken();
		matrixCardToken.setToken(tokenStr);
		matrixCardToken.setValue0(pos1Value);
		matrixCardToken.setValue1(pos2Value);
		matrixCardToken.setValue2(pos3Value);
		OperatorInfo operatorInfo = new OperatorInfo();
		operatorInfo.setIp(WebUtil.getClientIP(request));
		operatorInfo.setMemberCode(memberCode);
		operatorInfo.setU_id(uId);
		// 执行验证并绑定新的口令卡
		ResultDto resultDto = matrixCardResetService.toReset(matrixCardToken, operatorInfo, serialNo);
		if (null != resultDto && null==resultDto.getErrorCode()) {
			params.put("serialNo", serialNo);
			returnView.setViewName(successView);
			returnView.addAllObjects(params);
		}
		else {
			params.put("serialNo",serialNo);
			params.put("message",MessageConvertFactory.getMessage("matrixCardBindFail"));
			returnView.setViewName(messageView);
			returnView.addAllObjects(params);
		}
		return returnView;
	}

	/**
	 * 开始一个新的重置事务
	 * 
	 * @param idContent
	 * @param uId
	 * @param memberCode
	 * @param ip
	 * @return
	 */
	private MatrixCardTransInfoDto beginTrans(String idContent, Long uId, Long memberCode, String ip) {
		MatrixCardTransInfoDto infoDto = new MatrixCardTransInfoDto();
		infoDto.setCreationDate(new Date());
		infoDto.setIp(ip);
		infoDto.setMemberCode(memberCode);
		infoDto.setValStatus(MatrixCardTransInfoValStatus.NEW.getValue());
		infoDto.setTransType(MatrixCardTransType.RESET.getValue());
		infoDto.setU_id(uId);
		infoDto.setWrongTime(0);
		ResultDto resultDto = matrixCardResetService.begnResetTrans(infoDto);
		if (null != resultDto && null == resultDto.getErrorCode()) {
			return (MatrixCardTransInfoDto) resultDto.getObject();
		}
		return null;
	}

	public void setMatrixCardService(IMatrixCardService matrixCardService) {
		this.matrixCardService = matrixCardService;
	}

	public void setMatrixCardResetService(
			MatrixCardResetService matrixCardResetService) {
		this.matrixCardResetService = matrixCardResetService;
	}
	
	public void setSmsService(SmsService smsService) {
		this.smsService = smsService;
	}

	public void setMessageView(String messageView) {
		this.messageView = messageView;
	}

	public void setStep1View(String step1View) {
		this.step1View = step1View;
	}

	public void setStep2View(String step2View) {
		this.step2View = step2View;
	}

	public void setSuccessView(String successView) {
		this.successView = successView;
	}

	public void setEmailView(String emailView) {
		this.emailView = emailView;
	}

	public void setPhoneView(String phoneView) {
		this.phoneView = phoneView;
	}

	public void setResetView(String resetView) {
		this.resetView = resetView;
	}
	
	public void setMailService(CheckCodeService mailService) {
    	this.mailService = mailService;
    }

	public AcctService getAcctService() {
		return acctService;
	}

	public void setAcctService(AcctService acctService) {
		this.acctService = acctService;
	}
	
	public void setMemberService(MemberService memberService) {
    	this.memberService = memberService;
    }

	public void setLoginView(String loginView) {
		this.loginView = loginView;
	}

	public void setRephoneView(String rephoneView) {
		this.rephoneView = rephoneView;
	}
	
}
