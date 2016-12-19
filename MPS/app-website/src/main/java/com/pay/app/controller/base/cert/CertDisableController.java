/**
*Copyright (c) 2004-2013 pay.com . All rights reserved. 版权所有
*/
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

import com.pay.acc.cert.enums.StepEnum;
import com.pay.acc.checkcode.CheckCodeService;
import com.pay.acc.checkcode.dto.CheckCodeDto;
import com.pay.acc.comm.CheckCodeOriginEnum;
import com.pay.acc.comm.Resource;
import com.pay.acc.common.MaConstant;
import com.pay.acc.common.config.VerifyPasswordConfig;
import com.pay.acc.service.account.AccountInfoService;
import com.pay.acc.service.account.constantenum.AcctTypeEnum;
import com.pay.acc.service.account.dto.MaResultDto;
import com.pay.acc.service.cert.CertService;
import com.pay.app.base.session.SessionHelper;
import com.pay.app.common.helper.MessageConvertFactory;
import com.pay.app.service.sms.SmsService;
import com.pay.base.model.Operator;
import com.pay.base.service.operator.OperatorServiceFacade;
import com.pay.util.RSAHelper;
import com.pay.util.TokenUtil;

/**
 * 证书注销
 * @author fjl
 * @date 2011-11-25
 */
public class CertDisableController extends MultiActionController {
	
	private CertService  certService;
	private SmsService smsService;
	private CheckCodeService  checkCodeService;
	private OperatorServiceFacade operatorServiceFacade;
	private AccountInfoService accountInfoService;
	
	private String certDisablePage;
	private String certDisableUnInstallPage;
	private String certDisableResultPage;
	
	private final static String CERT_DISABLE_TOKEN = "cert_disable_token";
	
	private final static String PAYPWD_ERROR_COUNT = "payPwd_Error_Count";
	
	
	/**
	 * 注销证书确认
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception{

		String memberCode = SessionHelper.getMemeberCodeBySession();
		String isInstall = request.getParameter("isInstall");
		boolean install = false;
		if(StringUtils.isNotBlank(isInstall)){
			
			//从session 中取服务器状态 SessionHelper.isLocalCertUser();
			install = Boolean.parseBoolean(isInstall) && SessionHelper.isLocalCertUser();
		}
		List<String> mobiles = new ArrayList<String>(1);
		mobiles.add(getBindMobile());
		
		smsService.sendCheckCode(memberCode, CheckCodeOriginEnum.CERT_DISABLE.getValue(), 
				mobiles, Resource.DISABLE_CERT_SMS);
		String token = TokenUtil.getUUID() ;
		request.getSession().setAttribute(CERT_DISABLE_TOKEN, token);
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("token", token);
		data.put("mobile",  getBindMobile().replaceAll("(\\d{3})(\\d{4})(\\d{4})", "$1****$3"));
		data.put("install", install);
		ModelAndView view = new ModelAndView();
		if(install){
			view.setViewName(certDisablePage);
		}else{
			view.setViewName(certDisableUnInstallPage);
		}
		view.addAllObjects(data);
		return view;
	}
	
	/**
	 * 重发注销证书校验码短信
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void doSendSms(HttpServletRequest request,
            HttpServletResponse response) throws Exception{
            response.setContentType("text/plain;charset=UTF-8");
            response.setHeader("Cache-Control", "no-cache");
            
            PrintWriter out = null;
            out = response.getWriter();
            String result = "N";
            
            String rnd = (String) request.getSession().getAttribute(CERT_DISABLE_TOKEN);
            String token = request.getParameter("token");
            
            if(rnd != null && rnd.equals(token) ){
            	String memberCode = SessionHelper.getMemeberCodeBySession();
	    		List<String> mobiles = new ArrayList<String>(1);
	    		mobiles.add(getBindMobile());
	    		
	    		boolean bol = smsService.sendCheckCode(memberCode, CheckCodeOriginEnum.CERT_DISABLE.getValue(), 
	    				 mobiles, Resource.DISABLE_CERT_SMS);
	    		result = bol ? "Y" : "N";
            }
           
            out.print(result);
            out.flush();
            out.close();
    }
	
	
	public ModelAndView disableCert(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String rnd = (String)request.getSession().getAttribute(CERT_DISABLE_TOKEN);
		String token = request.getParameter("token");
		
		ModelAndView result = new ModelAndView(certDisableResultPage);
		if(rnd == null || !rnd.equals(token)){
			//出错页面
			result.addObject("flag", Boolean.FALSE);
			return result;
		}
		
		String isInstall = request.getParameter("install");
		boolean install =false;
		if(StringUtils.isNotBlank(isInstall)){
			install = Boolean.parseBoolean(isInstall);
		}
	
		String check = request.getParameter("checkCode");		
		String memberCode = SessionHelper.getMemeberCodeBySession();
		Long operatorId = SessionHelper.getOperatorIdBySession();
		String hasSecurity = request.getParameter("hasSecurity")==null?"":request.getParameter("hasSecurity");
				
		CheckCodeDto checkCodeBean = checkCodeService.getByLastCheckCode(Long.valueOf(memberCode), CheckCodeOriginEnum.CERT_DISABLE, 1);
		if(!StringUtils.isNotBlank(check) || !check.equals(checkCodeBean.getCheckCode())){
			//返回重新输入
			String newToken = TokenUtil.getUUID();
			request.getSession().setAttribute(CERT_DISABLE_TOKEN, newToken);
			Map<String,Object> data = new HashMap<String,Object>();
			data.put("token", newToken);
			data.put("mobile",  getBindMobile().replaceAll("(\\d{3})(\\d{4})(\\d{4})", "$1****$3"));
			data.put("errorCheckCode",MessageConvertFactory.getMessage("cert.checkcode.error"));
			ModelAndView view = new ModelAndView();
			if(install){
				view.setViewName(certDisablePage);
			}else{
				view.setViewName(certDisableUnInstallPage);
			}
			view.addAllObjects(data);
			return view;
		}
		
		//校验支付密码
		if(! install){
			String payPwd = request.getParameter("payPwd");
			boolean verify = false;
			//如果是安全控件输入的密码 要用私钥解密
			if(Resource.ORGIN_FROM_SECURITY.equals(hasSecurity)){
	            try{
	            	payPwd = RSAHelper.getRSAString(payPwd);
	            }catch(Exception e){
	            	e.printStackTrace();
	            }
	        }
			//验证支付密码
			try {
				MaResultDto maResultDto =  accountInfoService.doVerifyPayPassword(Long.valueOf(memberCode),  AcctTypeEnum.BASIC_CNY.getCode(), payPwd, operatorId,false);
				if(maResultDto != null && maResultDto.getResultStatus() == MaConstant.SECCESS){
					verify = true;
				}
			} catch (Exception e) {
				verify = false;
			}
			if(! verify ){
				//返回重新输入
				int payTotalCount = VerifyPasswordConfig.getInstance().getPayTotalCount();
				
				Integer count = (Integer)request.getSession().getAttribute(PAYPWD_ERROR_COUNT);
				if(count == null){
					count = 0;
				}				
				count ++;
				int les = payTotalCount - count;
				if(les > 0){
					Object[] args = new String[]{String.valueOf(les)};
					request.getSession().setAttribute(PAYPWD_ERROR_COUNT,count);
					
					String newToken = TokenUtil.getUUID();
					request.getSession().setAttribute(CERT_DISABLE_TOKEN, newToken);
					Map<String,Object> data = new HashMap<String,Object>();
					data.put("token", newToken);
					data.put("mobile",  getBindMobile().replaceAll("(\\d{3})(\\d{4})(\\d{4})", "$1****$3"));
					data.put("errorPayPwd",MessageConvertFactory.getMessage("payPwdError", args));
					
					ModelAndView view = new ModelAndView(certDisableUnInstallPage);
					view.addAllObjects(data);
					return view;
				}else{
					ModelAndView view = new ModelAndView(certDisableResultPage);
					request.getSession().removeAttribute(PAYPWD_ERROR_COUNT);
					view.addObject("flag", Boolean.FALSE);
					return view;
				}
			}else{
				request.getSession().removeAttribute(PAYPWD_ERROR_COUNT);
			}
		}
		
		checkCodeService.updateCheckCodeStatus2Used(memberCode, CheckCodeOriginEnum.CERT_DISABLE.getValue());
		
		boolean bol = certService.disableCertOfUserRntx(Long.valueOf(memberCode),operatorId);
		if(bol){
			result.addObject("flag", Boolean.TRUE);
			//删除session 中当前用户是数字证书用户标志
			SessionHelper.removeLocalInstallCert();
			SessionHelper.removeCertUser();
			
		}else{
			result.addObject("flag", Boolean.FALSE);
		}
		
		certService.createCertOperateLog(Long.valueOf(memberCode), operatorId, bol, StepEnum.DISABLE.getValue());
		
		request.getSession().removeAttribute(CERT_DISABLE_TOKEN);
		return result;
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

	public void setCertService(CertService certService) {
		this.certService = certService;
	}
	
	public void setOperatorServiceFacade(OperatorServiceFacade operatorServiceFacade) {
		this.operatorServiceFacade = operatorServiceFacade;
	}
	
	public void setSmsService(SmsService smsService) {
		this.smsService = smsService;
	}

	public void setCheckCodeService(CheckCodeService checkCodeService) {
		this.checkCodeService = checkCodeService;
	}

	public void setAccountInfoService(AccountInfoService accountInfoService) {
		this.accountInfoService = accountInfoService;
	}

	public void setCertDisablePage(String certDisablePage) {
		this.certDisablePage = certDisablePage;
	}

	public void setCertDisableUnInstallPage(String certDisableUnInstallPage) {
		this.certDisableUnInstallPage = certDisableUnInstallPage;
	}

	public void setCertDisableResultPage(String certDisableResultPage) {
		this.certDisableResultPage = certDisableResultPage;
	}
	
}
