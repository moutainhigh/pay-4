package com.pay.app.controller.base.cert;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.app.base.session.SessionHelper;
import com.pay.acc.cert.model.MemberCert;
import com.pay.acc.cert.service.MemberCertService;
import com.pay.acc.service.cert.CertQueryService;

public class CertMerberQueryController extends MultiActionController{


	final int TYPE_DN = 1; 
	final int TYPE_CN = 2; 
	
	/** 操作员服务 */

	private MemberCertService  memberCertService;
	/**
	 * @param certQueryService the certQueryService to set
	 */
	/**
	 * @param memberCertService the memberCertService to set
	 */
	public void setMemberCertService(MemberCertService memberCertService) {
		this.memberCertService = memberCertService;
	}

	
	public ModelAndView queryCurrDn(HttpServletRequest request,HttpServletResponse response) throws IOException{
		response.getWriter().write(getDnOrCn(TYPE_DN));
		return null;
	}
	public ModelAndView queryCurrCn(HttpServletRequest request,HttpServletResponse response) throws IOException{
		response.getWriter().write(getDnOrCn(TYPE_CN));
		return null;
	}
	
	
	public String getDnOrCn (int type) throws IOException{
		Long memberCode = Long.valueOf(SessionHelper.getMemeberCodeBySession());
		Long operatorId = Long.valueOf(SessionHelper.getOperatorIdBySession());
		MemberCert cr = memberCertService.queryMemberCert(memberCode, operatorId);
		String str = "";
		if(cr!=null){
			str = type==1 ? cr.getUserDn() :cr.getCN();
		}
		return str;
	}
	
	
	public ModelAndView isCertUser(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Long memberCode = Long.valueOf(SessionHelper.getMemeberCodeBySession());
		Long operatorId = Long.valueOf(SessionHelper.getOperatorIdBySession());
		boolean isCert = false;
		response.getWriter().write(isCert ? "1":"0");
		return null;
	}
	


}
