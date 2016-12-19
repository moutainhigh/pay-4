/**
*Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
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
import com.pay.acc.cert.model.CertManage;
import com.pay.acc.cert.model.MemberCert;
import com.pay.acc.cert.service.CertManageService;
import com.pay.acc.cert.service.MemberCertService;
import com.pay.acc.checkcode.CheckCodeService;
import com.pay.acc.checkcode.dto.CheckCodeDto;
import com.pay.acc.comm.CheckCodeOriginEnum;
import com.pay.acc.comm.Resource;
import com.pay.acc.service.cert.CertService;
import com.pay.acc.service.cert.dto.ImportCertDto;
import com.pay.app.base.session.SessionHelper;
import com.pay.app.common.helper.MessageConvertFactory;
import com.pay.app.service.sms.SmsService;
import com.pay.base.model.Operator;
import com.pay.base.service.operator.OperatorServiceFacade;
import com.pay.inf.service.IMessageDigest;
import com.pay.util.TokenUtil;

/**
 * 导入证书
 * @author fjl
 * @date 2011-11-28
 */
public class CertImportController extends MultiActionController {
	
	private CertService  certService;
	private MemberCertService memberCertService;
	private SmsService smsService;
	private OperatorServiceFacade operatorServiceFacade;
	private CheckCodeService  checkCodeService;
	private CertManageService certManageService;
	private IMessageDigest iMessageDigest;
	
	private String importIndexPage;
	private String inputCheckCodePage;
	private String importCertPage;
	private String importResultPage;
	private String importCountErrorPage;
	
	private final static String CERT_IMPORT_TOKEN = "cert_import_token";
	private final static int CERT_MAX_COUNT = 5;
	
	/**
	 * 导入首页
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		Long operatorId=SessionHelper.getOperatorIdBySession();
		String memberCode = SessionHelper.getMemeberCodeBySession();
		
		int count = certManageService.queryCount(Long.valueOf(memberCode), operatorId);
		String machineId = request.getParameter("machineId");
		CertManage cert = null;
		Map<String,Object> model = new HashMap<String,Object>();
	
		if(count >= CERT_MAX_COUNT ){
			if(StringUtils.isNotBlank(machineId)){
				cert = certManageService.queryCertManage(Long.valueOf(memberCode), operatorId, machineId);
			}
			if(cert == null){
				//如果服务器本机没有证书，安装地点大于或等于5不能导入了，
				//否则本机有可能被人为删除了。可以让其继续导入。
				return new ModelAndView(importCountErrorPage);
			}
		}
		
		MemberCert memberCert = memberCertService.queryMemberCert(Long.valueOf(memberCode), operatorId);
		model.put("memberCert", memberCert);
		
		
		ModelAndView view = new ModelAndView(importIndexPage);
		view.addAllObjects(model);
		return view;
	}
	
	
	public ModelAndView postImportData(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		String memberCode = SessionHelper.getMemeberCodeBySession();
		
		String certFilePath = request.getParameter("certFilePath");
		String usePlace = request.getParameter("usePlace");
		String backupPwd = request.getParameter("backupPwd");
		String dn = request.getParameter("dn");
		
		//System.out.println(certFilePath + ","+ usePlace + "," +  backupPwd);
		
		List<String> mobiles = new ArrayList<String>(1);
		mobiles.add(getBindMobile());
		
		smsService.sendCheckCode(memberCode, CheckCodeOriginEnum.CERT_IMPORT.getValue(), 
				mobiles, Resource.IMPORT_CERT_SMS);
		String token = TokenUtil.getUUID() ;
		request.getSession().setAttribute(CERT_IMPORT_TOKEN, token);
		
		ImportCertDto dto = new ImportCertDto();
		dto.setBackupPwd(backupPwd);
		dto.setCertFilePath(certFilePath);
		dto.setDn(dn);
		dto.setToken(token);
		dto.setUsePlace(usePlace);
		
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("mobile",  getBindMobile().replaceAll("(\\d{3})(\\d{4})(\\d{4})", "$1****$3"));
		data.put("importCertDto", dto);
		data.put("sign", sign(dto.toString()));
		
		ModelAndView view = new ModelAndView(inputCheckCodePage);
		view.addAllObjects(data);
		return view;
	}
	
	private String sign(String src){
		try {
			return iMessageDigest.genMessageDigest(src.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
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
            
            String rnd = (String) request.getSession().getAttribute(CERT_IMPORT_TOKEN);
            String token = request.getParameter("token");
            
            if(rnd != null && rnd.equals(token) ){
            	String memberCode = SessionHelper.getMemeberCodeBySession();
	    		List<String> mobiles = new ArrayList<String>(1);
	    		mobiles.add(getBindMobile());
	    		
	    		boolean bol = smsService.sendCheckCode(memberCode, CheckCodeOriginEnum.CERT_IMPORT.getValue(), 
	    				 mobiles, Resource.IMPORT_CERT_SMS);
	    		result = bol ? "Y" : "N";
            }
           
            out.print(result);
            out.flush();
            out.close();
    }
	
	public ModelAndView verify(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String rnd = (String)request.getSession().getAttribute(CERT_IMPORT_TOKEN);
		request.getSession().removeAttribute(CERT_IMPORT_TOKEN);
		String token = request.getParameter("token");
				
		ModelAndView result = new ModelAndView();
		if(rnd == null || !rnd.equals(token)){
			//出错页面
			result.addObject("flag", Boolean.FALSE);
			return result;
		}
	
		String check = request.getParameter("checkCode");		
		String memberCode = SessionHelper.getMemeberCodeBySession();
		
		String certFilePath = request.getParameter("certFilePath");
		String usePlace = request.getParameter("usePlace");
		String backupPwd = request.getParameter("backupPwd");
		String dn = request.getParameter("dn");
		String sign = request.getParameter("sign");
		
		ImportCertDto dto = new ImportCertDto();
		dto.setBackupPwd(backupPwd);
		dto.setCertFilePath(certFilePath);
		dto.setDn(dn);
		dto.setToken(token);
		dto.setUsePlace(usePlace);
		
		if(!iMessageDigest.validateMessageDigest(dto.toString().getBytes(), sign)){
			result.addObject("flag", Boolean.FALSE);
			return result;
		}
		
		String newToken = TokenUtil.getUUID();
		dto.setToken(newToken);
		request.getSession().setAttribute(CERT_IMPORT_TOKEN, newToken);
		
		CheckCodeDto checkCodeBean = checkCodeService.getByLastCheckCode(Long.valueOf(memberCode), CheckCodeOriginEnum.CERT_IMPORT, 1);
		if(!StringUtils.isNotBlank(check) || !check.equals(checkCodeBean.getCheckCode())){
			//返回重新输入
			Map<String,Object> data = new HashMap<String,Object>();
			data.put("sign", sign(dto.toString()));
			data.put("mobile",  getBindMobile().replaceAll("(\\d{3})(\\d{4})(\\d{4})", "$1****$3"));
			data.put("errorCheckCode",MessageConvertFactory.getMessage("cert.checkcode.error"));
			data.put("importCertDto", dto);
			
			ModelAndView view = new ModelAndView(inputCheckCodePage);
			view.addAllObjects(data);
			return view;
		}
		
		checkCodeService.updateCheckCodeStatus2Used(memberCode, CheckCodeOriginEnum.CERT_IMPORT.getValue());
		ModelAndView view = new ModelAndView(importCertPage);
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("importCertDto", dto);
		view.addAllObjects(data);
		
		return view;
	}
	
	public ModelAndView doImport(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String rnd = (String)request.getSession().getAttribute(CERT_IMPORT_TOKEN);
		request.getSession().removeAttribute(CERT_IMPORT_TOKEN);
		String token = request.getParameter("token");
		
		ModelAndView result = new ModelAndView(importResultPage);
		if(rnd == null || !rnd.equals(token)){
			//出错页面
			result.addObject("flag", Boolean.FALSE);
			return result;
		}
			
		String memberCode = SessionHelper.getMemeberCodeBySession();
		Long operatorId = SessionHelper.getOperatorIdBySession();
		String machineId = request.getParameter("machineId");
		String dn = request.getParameter("dn");
		String usePlace = request.getParameter("usePlace");
		
		if(SessionHelper.isCertUser()){
			CertManage certManage = certManageService.queryCertManage(Long.valueOf(memberCode), operatorId, machineId);
			if(certManage != null){
				certManage.setUsePlace(usePlace);
				certManage.setStatus(CertManage.StatusEnum.VALID.getValue());
				certManageService.modifyCerManage(certManage);
			}else{
				certManage = new CertManage();
				certManage.setMemberCode(Long.valueOf(memberCode));
				certManage.setOperatorId(operatorId);
				certManage.setMachineId(machineId);
				certManage.setStatus(CertManage.StatusEnum.VALID.getValue());
				certManage.setUsePlace(usePlace);
				certManage.setUserDn(dn);
				certManageService.createCerManage(certManage);
			}
			
			certService.createCertOperateLog(Long.valueOf(memberCode), operatorId, true, StepEnum.IMPORT.getValue());
			
			//更新session 中的状态
        	SessionHelper.setLocalInstallCert();
			result.addObject("flag", Boolean.TRUE);
			return result;
		}
		
		result.addObject("flag", Boolean.FALSE);
		return result;
	}
	
	//直接跳转到失败页面
	public ModelAndView importFailure(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		String memberCode = SessionHelper.getMemeberCodeBySession();
		Long operatorId = SessionHelper.getOperatorIdBySession();
		if(memberCode != null && operatorId !=null){
			certService.createCertOperateLog(Long.valueOf(memberCode), operatorId, false, StepEnum.IMPORT.getValue());
		}
		ModelAndView result = new ModelAndView(importResultPage);
		request.getSession().removeAttribute(CERT_IMPORT_TOKEN);
		result.addObject("flag", Boolean.FALSE);
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


	public void setMemberCertService(MemberCertService memberCertService) {
		this.memberCertService = memberCertService;
	}


	public void setSmsService(SmsService smsService) {
		this.smsService = smsService;
	}


	public void setOperatorServiceFacade(OperatorServiceFacade operatorServiceFacade) {
		this.operatorServiceFacade = operatorServiceFacade;
	}
	
	public void setCheckCodeService(CheckCodeService checkCodeService) {
		this.checkCodeService = checkCodeService;
	}


	public void setCertManageService(CertManageService certManageService) {
		this.certManageService = certManageService;
	}


	public void setiMessageDigest(IMessageDigest iMessageDigest) {
		this.iMessageDigest = iMessageDigest;
	}


	public void setImportCertPage(String importCertPage) {
		this.importCertPage = importCertPage;
	}


	public void setImportResultPage(String importResultPage) {
		this.importResultPage = importResultPage;
	}


	public void setImportIndexPage(String importIndexPage) {
		this.importIndexPage = importIndexPage;
	}


	public void setInputCheckCodePage(String inputCheckCodePage) {
		this.inputCheckCodePage = inputCheckCodePage;
	}


	public void setImportCountErrorPage(String importCountErrorPage) {
		this.importCountErrorPage = importCountErrorPage;
	}
	
	

}
