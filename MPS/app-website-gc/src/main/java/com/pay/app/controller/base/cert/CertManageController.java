/**
*Copyright (c) 2004-2013 pay.com . All rights reserved. 版权所有
*/
package com.pay.app.controller.base.cert;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.app.base.session.SessionHelper;
import com.pay.acc.cert.enums.StepEnum;
import com.pay.acc.cert.model.CertManage;
import com.pay.acc.cert.service.CertManageService;
import com.pay.acc.service.cert.CertService;


/**
 * 证书安装地点管理
 * @author fjl
 * @date 2011-11-22
 */
public class CertManageController extends MultiActionController {
	
//	private CertManageService certManageService;
//	private CertService  certService;
//	
//	private String certListPage;
//
//	
//	/**
//	 * 得到当前用户下的证书安装地点列表
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws Exception
//	 */
//	public ModelAndView getCertManageList(HttpServletRequest request,
//			HttpServletResponse response) throws Exception{
//		
//		Long operatorId=SessionHelper.getOperatorIdBySession();
//		Long memberCode=Long.valueOf(SessionHelper.getMemeberCodeBySession());
//		String machineId = request.getParameter("machineId");
//		Map<String, Object> model = new HashMap<String, Object>();
//		List<CertManage> certManages = certManageService.queryCertManage(memberCode, operatorId);
//		
//		model.put("certManages", certManages);
//		model.put("machineId", machineId);
//		ModelAndView view = new ModelAndView(certListPage);
//		view.addAllObjects(model);
//		return view;
//	}
//	
//	/**
//	 * 删除一个证书安装地点
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws Exception
//	 */
//	public ModelAndView delCertManage(HttpServletRequest request,
//			HttpServletResponse response) throws Exception{
//
//		String result = "N";
//		String id = request.getParameter("id");
//		String isCurMachine = request.getParameter("isCurMachine");
//		
//		Long operatorId=SessionHelper.getOperatorIdBySession();
//		Long memberCode=Long.valueOf(SessionHelper.getMemeberCodeBySession());
//		
//		if(request.getMethod().equalsIgnoreCase("POST")){
//			if(StringUtils.isNotBlank(id)){
//				Long pId = Long.valueOf(id);
//				boolean bol = certManageService.logicDeleteById(pId);
//				result = bol ? "Y" : "N";
//				
//				certService.createCertOperateLog(memberCode, operatorId, bol, StepEnum.DELETE.getValue());
//				if(bol){
//					// 删除session 中当前地点证书用户标志 SessionHelper.isLocalCertUser();
//					if(StringUtils.isNotBlank(isCurMachine)){
//						boolean iscur = Boolean.parseBoolean(isCurMachine);
//						if(iscur){
//							SessionHelper.removeLocalInstallCert();
//						}
//					}
//				}
//			}
//		}
//		response.setContentType("text/plain;charset=UTF-8");
//		response.setHeader("Cache-Control", "no-cache");
//		PrintWriter out = response.getWriter();
//		out.print(result);
//		out.flush();
//		out.close();
//
//		return null;
//	}
//	
//	//查询安装地点数量
//	public ModelAndView getCount(HttpServletRequest request,
//			HttpServletResponse response) throws Exception{
//		
//		Long operatorId=SessionHelper.getOperatorIdBySession();
//		Long memberCode=Long.valueOf(SessionHelper.getMemeberCodeBySession());
//		
//		int count = 0;
//		if(request.getMethod().equalsIgnoreCase("POST")){
//			count = certManageService.queryCount(memberCode, operatorId);
//			
//		}
//		response.setContentType("text/plain;charset=UTF-8");
//		response.setHeader("Cache-Control", "no-cache");
//		PrintWriter out = response.getWriter();
//		out.print(count);
//		out.flush();
//		out.close();
//
//		return null;
//	}
//	
//	
//	public void setCertManageService(CertManageService certManageService) {
//		this.certManageService = certManageService;
//	}
//
//	public void setCertService(CertService certService) {
//		this.certService = certService;
//	}
//
//	public void setCertListPage(String certListPage) {
//		this.certListPage = certListPage;
//	}

}
