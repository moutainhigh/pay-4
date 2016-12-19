package com.pay.app.controller.external;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.pay.app.base.session.LoginSession;
import com.pay.app.common.helper.AppConf;
import com.pay.app.common.helper.MessageConvertFactory;
import com.pay.app.filter.AppFilterCommon;
import com.pay.base.common.enums.ErrorCodeEnum;
import com.pay.base.common.enums.SecurityLvlEnum;
import com.pay.base.dto.MemberCriteria;
import com.pay.base.dto.ResultDto;
import com.pay.base.dto.matrixcard.MatrixCardDto;
import com.pay.base.service.matrixcard.login.MatrixCardLoginService;
import com.pay.base.service.user.UserLoginService;
import com.pay.util.RSAHelper;
import com.pay.util.SSOSignatureUtil;
import com.pay.util.SignatureHelper;


public class ExternalLoginController extends AbstractController{
   
	private final static String ORGIN_FROM_SECURITY="security";
	private final static String KEY = "pay.com";
	private static final String GET_METHOD_PREFIX = "GET";
	   /** 用户登录服务 */
    private UserLoginService    userLoginService;
	private static final String FLAGACTIVESUCESS="1"; 

	private String autoJump;
  

	private MatrixCardLoginService matrixCardLoginService;
	private String externalLogout;
	private static final Log log = LogFactory.getLog(ExternalLoginController.class);
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		boolean isSecurity=false;
		String jumpUrl="https://sso.pay.com/trust_login.php";
		String loginName = request.getParameter("loginName")==null?"":request.getParameter("loginName").trim();
		String passWord = request.getParameter("passWord")==null?"":request.getParameter("passWord");
		String hasSecurity = request.getParameter("hasSecurity")==null?"":request.getParameter("hasSecurity");
		String hascode = request.getSession().getAttribute("hascode")==null?"":(String)request.getSession().getAttribute("hascode");//判断是否有验证码
		String service=StringUtils.isBlank(request.getParameter("service"))?MessageConvertFactory.getMessage("mall.defaultmall")
				:request.getParameter("service");
		ModelAndView mv = new ModelAndView(externalLogout);
		mv.addObject("service",service);
		//如果是安全控件输入的密码 要用私钥解密
		if(ORGIN_FROM_SECURITY.equals(hasSecurity)){
            isSecurity=true;
            try{
                String pkey=RSAHelper.public_key_String;
                passWord=RSAHelper.getRSAString(passWord); 
                mv.addObject("pkey",pkey);
            }catch(Exception e){
                log.error("ExternalLoginController login Error!",e);
            }
            mv.addObject("isSecurity",isSecurity);
        }
		
		
		//校验验证码
		if(StringUtils.equals(hascode, "1") || request.getMethod().equals(GET_METHOD_PREFIX)){
			String randCode = request.getSession().getAttribute("rand") == null? "": request.getSession().getAttribute("rand").toString();
			String code = request.getParameter("randCode") == null ? "" : request.getParameter("randCode");
			request.getSession().removeAttribute("rand");
			if ("".equals(randCode) || "".equals(code) || !code.equalsIgnoreCase(randCode)) { 
				request.getSession().removeAttribute("rand");
				mv.addObject("msgStr",MessageConvertFactory.getMessage("randCode"));
				request.getSession().setAttribute("hascode", "1");
				return mv;
			}
		}
		request.getSession().removeAttribute("rand");
		ResultDto rDto=userLoginService.individualMemberLogin(loginName, passWord);
		MemberCriteria memberCriteria=null;
		memberCriteria=(MemberCriteria)rDto.getObject();
		if(rDto.getErrorCode()!=null){
			request.getSession().setAttribute("hascode", "1");
		    mv.addObject("msgStr",rDto.getErrorMsg());
		    return mv;
		}
		request.getSession().setAttribute("hascode", null);
		request.getSession().setAttribute("memberCode", memberCriteria.getMemberCode());
		request.getSession().setAttribute("loginName", loginName);
		request.getSession().setAttribute("verifyName", memberCriteria.getVerifyName());
		// 会员上次登录时间
		request.getSession().setAttribute("epLastLoginTime", memberCriteria.getLastLoginTime());
		
		 if(FLAGACTIVESUCESS.equals(memberCriteria.getStatus())) {
		
			LoginSession loginSession= new LoginSession();
			loginSession.setVerifyName(memberCriteria.getVerifyName());
			loginSession.setSalutatory(memberCriteria.getSalutatory());
			loginSession.setUpdateDate(memberCriteria.getUpdateDate());
			loginSession.setActiveStatus(memberCriteria.getStatus());
			loginSession.setMemberCode(memberCriteria.getMemberCode());
			loginSession.setLoginName(memberCriteria.getLoginName());
			loginSession.setServiceLevel(memberCriteria.getServiceLevel());
			
			
			MatrixCardDto mc=matrixCardLoginService.findByBindMemberCode(Long.valueOf(memberCriteria.getMemberCode()));
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
			log.debug("member login signa sessionId is :#####################"+request.getSession().getId());
			Map<String,String> pMaps=generateMallUrls(service,memberCriteria.getMemberCode());
			return new ModelAndView(autoJump).addObject(AppConf.actionUrl,jumpUrl).addObject("pMaps",pMaps);
		}
		
		 	mv.addObject("msgStr",ErrorCodeEnum.MEMBER_INVALID.getMessage());
		    return mv;

	}
	
	private Map<String,String>  generateMallUrls(String service,String memberCode){
		
		Map<String,String> pMaps=new HashMap<String,String>(3);
		pMaps.put("id",memberCode);
		pMaps.put("goto",service);
		pMaps.put("signMsg",generateMallSign(service,memberCode));
		return pMaps;
	}
	
	private String generateMallSign(String service,String memberCode){
		String sign = "id="+memberCode+"&goto="+service;
		return SSOSignatureUtil.encryptOfRSAKey4SSO(sign);
	}
	
	/**
	private String signWithMd5(String src){
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			BASE64Encoder base64en = new BASE64Encoder();
			String n = base64en.encode(md5.digest((src+KEY).getBytes("UTF-8")));
			return n;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}**/
	
	
	public UserLoginService getUserLoginService() {
		return userLoginService;
	}
	public void setUserLoginService(UserLoginService userLoginService) {
		this.userLoginService = userLoginService;
	}
	public String getExternalLogout() {
		return externalLogout;
	}
	public void setExternalLogout(String externalLogout) {
		this.externalLogout = externalLogout;
	}
	 public MatrixCardLoginService getMatrixCardLoginService() {
			return matrixCardLoginService;
	}

	 public void setMatrixCardLoginService(
				MatrixCardLoginService matrixCardLoginService) {
			this.matrixCardLoginService = matrixCardLoginService;
		}
	  public String getAutoJump() {
			return autoJump;
		}

		public void setAutoJump(String autoJump) {
			this.autoJump = autoJump;
		}
}
