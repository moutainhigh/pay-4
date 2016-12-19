/**
 * 
 */
package com.pay.app.controller.base.cert;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.app.service.operator.BindMobileService;
import com.pay.app.service.operator.statusEnum.BindMobleSmsType;
import com.pay.base.model.Operator;
import com.pay.base.service.operator.OperatorServiceFacade;
import com.pay.acc.cert.enums.StepEnum;
import com.pay.acc.service.cert.CertService;
import com.pay.app.common.util.ValidateCodeUtils;

/**
 * desc: 备份数字证书
 * @author DaiDeRong
 * 2011-11-24 上午10:47:35
 * 
 */
public class BackupCertController extends MultiActionController {
	
	private String BACK_CERT_TOKEN = "BACK_CERT_TOKEN";
	private String confirmMobileView;
	private String backupResultView;
	private BindMobileService bindMobileService;
	
	private CertService  certService;
	
	private OperatorServiceFacade operatorServiceFacade;
	
	
	
	
	
	
	
	/**
	 * @param certService the certService to set
	 */
	public void setCertService(CertService certService) {
		this.certService = certService;
	}


	/**
	 * @return the backupResultView
	 */
	public String getBackupResultView() {
		return backupResultView;
	}


	/**
	 * @param backupResultView the backupResultView to set
	 */
	public void setBackupResultView(String backupResultView) {
		this.backupResultView = backupResultView;
	}


	/**
	 * @return the confirmMobileView
	 */
	public String getConfirmMobileView() {
		return confirmMobileView;
	}


	/**
	 * @param confirmMobileView the confirmMobileView to set
	 */
	public void setConfirmMobileView(String confirmMobileView) {
		this.confirmMobileView = confirmMobileView;
	}


	/**
	 * @return the bindMobileService
	 */
	public BindMobileService getBindMobileService() {
		return bindMobileService;
	}


	/**
	 * @param bindMobileService the bindMobileService to set
	 */
	public void setBindMobileService(BindMobileService bindMobileService) {
		this.bindMobileService = bindMobileService;
	}


	/**
	 * @return the operatorServiceFacade
	 */
	public OperatorServiceFacade getOperatorServiceFacade() {
		return operatorServiceFacade;
	}


	/**
	 * @param operatorServiceFacade the operatorServiceFacade to set
	 */
	public void setOperatorServiceFacade(OperatorServiceFacade operatorServiceFacade) {
		this.operatorServiceFacade = operatorServiceFacade;
	}


	public ModelAndView index(HttpServletRequest request,HttpServletResponse response){
		return sendCode(request, response);
	}
	/**
	 * 发送验证码
	 * @author DaiDeRong
	 * 2011-11-24 上午11:30:28
	 */
	public ModelAndView sendCode(HttpServletRequest request,HttpServletResponse response){
		String token = UUID.randomUUID()+"";
		LoginSession loginSession =   SessionHelper.getLoginSession();
		Operator op = null;
		if(loginSession.getOperatorId() == 0 ){
			op = operatorServiceFacade.getAdminOperator(Long.valueOf( loginSession.getMemberCode()));
		}else{
			op = operatorServiceFacade.getOperatorById(SessionHelper.getOperatorIdBySession());
		}
		request.getSession().setAttribute(BACK_CERT_TOKEN, token);
		bindMobileService.sendConfirmCode(op.getMobile(), Long.valueOf(loginSession.getMemberCode()), BindMobleSmsType.BACKUP_CERT);
		return new ModelAndView(confirmMobileView)
		.addObject("tk", token).addObject("isLocalInstallCert", SessionHelper.isLocalCertUser());
	}
	
	
	/**
	 * 验证手机 验证码
	 * @author DaiDeRong
	 * 2011-11-24上午11:30:28
	 * @throws IOException 
	 */
	public ModelAndView reSendCode(HttpServletRequest request,HttpServletResponse response) throws IOException{
	
		
		response.setContentType("text/plain;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		
		String result = "N";
		PrintWriter out = response.getWriter();
		if(ValidateCodeUtils.validateCode(request, BACK_CERT_TOKEN, "tk")){
			LoginSession loginSession =   SessionHelper.getLoginSession();
			Operator op = operatorServiceFacade.getOperatorById(SessionHelper.getOperatorIdBySession());
			bindMobileService.sendConfirmCode(op.getMobile(), Long.valueOf(loginSession.getMemberCode()), BindMobleSmsType.BACKUP_CERT);
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
	
	
	/**
	 * 验证手机验证码
	 * @author DaiDeRong
	 * 2011-11-24 下午2:13:02
	 */
	public ModelAndView validateCode(HttpServletRequest request,HttpServletResponse response) throws IOException{
		
		LoginSession loginSession =   SessionHelper.getLoginSession();
		String validateCode = ServletRequestUtils.getStringParameter(request, "validateCode","");
		
		String result = "N";
		if(! ValidateCodeUtils.validateCode(request, "randCode")){
			result = "验证码输入有误，请重新输入！";
		}
		//查找数据库验证码
		else if(! bindMobileService.validateConfirmCode(Long.valueOf(loginSession.getMemberCode()), validateCode,BindMobleSmsType.BACKUP_CERT)){
			result = "手机验证码输入不正确，请重新输入！";
		}
		else{
			result = "Y";
		}
		String va = (String) request.getSession().getAttribute(BACK_CERT_TOKEN);
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		out.print(result);
		out.flush();
		out.close();
		return null;
	}
	
	
	


	/**
	 * 发送验证码
	 * @author DaiDeRong
	 * 2011-11-24 上午11:30:28
	 */
	public ModelAndView backupResult(HttpServletRequest request,HttpServletResponse response){
		if(! ValidateCodeUtils.validateCode(request, BACK_CERT_TOKEN, "tk")){
			return new ModelAndView(backupResultView)
			.addObject("status", false).addObject("errorMsg", "页面已过期");
		}
		boolean status = ServletRequestUtils.getStringParameter(request, "status","0").equals("1");
		String dir = ServletRequestUtils.getStringParameter(request, "dir","");
		request.getSession().removeAttribute(BACK_CERT_TOKEN);
		LoginSession loginSession =   SessionHelper.getLoginSession();
		certService.createCertOperateLog(Long.valueOf(loginSession.getMemberCode()), SessionHelper.getOperatorIdBySession(), status, StepEnum.BACKUP.getValue());
		return new ModelAndView(backupResultView)
		.addObject("dir", dir).addObject("status", status);
	}

}
