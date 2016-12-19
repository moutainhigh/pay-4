/**
 *  File: UpdateAccountInfoController.java
 *  Description:
 *  Copyright © 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-23   single_zhang     Create
 *
 */
package com.pay.app.controller.base.accountactive;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.app.base.api.annotation.HasFeature;
import com.pay.app.base.api.annotation.OperatorPermission;
import com.pay.app.base.api.common.constans.CutsConstants;
import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.app.common.helper.MessageConvertFactory;
import com.pay.app.validator.UpdateValidator;
import com.pay.app.validator.ValidatorDto;
import com.pay.base.dto.MemberCriteria;
import com.pay.base.dto.MemberInfoDto;
import com.pay.base.dto.ResultDto;
import com.pay.base.model.EnterpriseBase;
import com.pay.base.model.EnterpriseContact;
import com.pay.base.service.enterprise.EnterpriseBaseService;
import com.pay.base.service.enterprise.EnterpriseContactService;
import com.pay.base.service.member.MemberService;
import com.pay.util.DESUtil;

/**
 * 修改账户信息
 */
@HasFeature({CutsConstants.FEATURE_MAXTRIX,CutsConstants.FEATURE_NORMAL})
public class UpdateAccountInfoController extends MultiActionController {

	private String updateAccountInfo;

	private String login;
	
	private String jumpView;

	private String updateAddress;
	/** 修改企业用户联系地址 */
	private String updateCorpAddress;
	/** 企业会员联系信息 */
	private EnterpriseContactService enterpriseContactService;

	private MemberService baseMemberService;

	/** 企业会员基本信息服务 */
	private EnterpriseBaseService enterpriseBaseService;

	/**
	 * 账户信息修改之后就调转到我的账户首页
	 * 
	 * @param request
	 * @param response
	 * @param criteria
	 * @return
	 * @throws Exception
	 */
	@OperatorPermission(operatPermission = "OPERATOR_ACCOUNT_UPDATE")
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response, MemberCriteria memberCriteria)
			throws Exception {
		String msg = "";
		LoginSession loginSession = (LoginSession) request.getSession().getAttribute("userSession");
		String urlRed = "/app/accountInfo.htm";
		if (SessionHelper.isCorpLogin()) {
			urlRed = "/corp/accountInfo.htm";
		}
		ValidatorDto vd= UpdateValidator.validate(memberCriteria);//校验表单提交的内容
		  if(vd.hasErrors()){
		        msg=vd.getError();
		        Map<String, Object> paraMap = new HashMap<String, Object>();
	            paraMap.put("criteria", memberCriteria);
	            paraMap.put("iconNum","2");
	            paraMap.put("jumpUrl",urlRed);
	            paraMap.put("msgStr", msg);
	            return new ModelAndView(jumpView, paraMap);
		    }
		String memberCode = this.getMemberCode(request);
        if (StringUtils.isBlank(memberCode)) {
            return new ModelAndView(login);
        }

		memberCriteria.setMemberCode(memberCode);
		
		Map<String, Object> paraMap = new HashMap<String, Object>();
		// 跳转的URL 个人类型是"/app/accountInfo.htm";企业类型是"/corp/accountInfo.htm"
		if (SessionHelper.isCorpLogin()) {
			// 更新企业会员联系信息
			if (this.updateEnterpriseContact(request) > 0) {
				msg = MessageConvertFactory.getMessage("updateSuccess");
				return new ModelAndView("redirect:" + urlRed);
			}
		} else {
			String redType = memberCriteria.getRegType();
			if (StringUtils.isBlank(redType)) {
				redType = request.getParameter("hiRedType");
			}
			MemberInfoDto memberInfoDto = new MemberInfoDto();
			if(StringUtils.equals(redType, "1")){
                memberInfoDto.setEmail(memberCriteria.getContact());
            } else {
                memberInfoDto.setMobile(memberCriteria.getContact());
                
            }
			memberInfoDto.setTel(memberCriteria.getAptoticPhone());
			memberInfoDto.setFax(memberCriteria.getFaxes());
			memberInfoDto.setQq(memberCriteria.getQq());
			memberInfoDto.setMsn(memberCriteria.getMsn());
			memberInfoDto.setAddr(memberCriteria.getAddress());
			memberInfoDto.setMemberCode(Long.valueOf(memberCriteria
					.getMemberCode()));
			String certificateNo = null;
			if(StringUtils.isNotBlank(memberCriteria.getCardNo())){
			    certificateNo = DESUtil.encrypt(memberCriteria.getCardNo());
			}
			if(StringUtils.isNotBlank(certificateNo)){
				memberInfoDto.setCertificateNo(certificateNo);
			}else{
				memberInfoDto.setCertificateNo(memberCriteria.getCardNo());
			}
			Integer cardType = null;
			if(StringUtils.isNotBlank(request.getParameter("hiCardType"))){
			    cardType = Integer.valueOf(request.getParameter("hiCardType"));
			}
			memberInfoDto.setCertificateType(cardType);
			ResultDto result = baseMemberService
					.doUpdateMemberInfoRnTx(memberInfoDto);

			if (StringUtils.isBlank(result.getErrorMsg())) { // 修改成功
				msg = MessageConvertFactory.getMessage("updateSuccess");
				return new ModelAndView("redirect:/app/accountInfo.htm");
			}
		}

		msg = MessageConvertFactory.getMessage("updateFailed");
		paraMap.put("iconNum", "2");
		paraMap.put("msgStr", msg);
		paraMap.put("jumpUrl", urlRed);

		return new ModelAndView(jumpView, paraMap);
	}

	/** 删除个人联系地址 */
	@OperatorPermission(operatPermission = "OPERATOR_ACCOUNT_UPDATE")
	public ModelAndView deleteAddress(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String memberCode = this.getMemberCode(request);
        if (StringUtils.isBlank(memberCode)) {
            return new ModelAndView(login);
        }
		MemberInfoDto memberInfoDto = new MemberInfoDto();
		memberInfoDto.setMemberCode(Long.valueOf(memberCode));
		// 删除个人联系地址，则将地址设置为空。
		memberInfoDto.setAddr("");
		memberInfoDto.setCity("");
		memberInfoDto.setProvince("");
		ResultDto result = baseMemberService
				.doUpdateMemberInfoRnTx(memberInfoDto);
		String msg = "";
		if (StringUtils.isBlank(result.getErrorMsg())) { // 修改成功
			msg = MessageConvertFactory.getMessage("updateSuccess");
			return new ModelAndView("redirect:/app/accountInfo.htm");
		}
		Map<String, Object> paraMap = new HashMap<String, Object>();
		msg = MessageConvertFactory.getMessage("updateFailed");
		paraMap.put("iconNum", "2");
		paraMap.put("msgStr", msg);
		paraMap.put("jumpUrl", "/app/accountInfo.htm");

		return new ModelAndView(jumpView, paraMap);
	}

	/** 展示修改地址信息 */
	@OperatorPermission(operatPermission = "OPERATOR_ACCOUNT_UPDATE")
	public ModelAndView showUpdateAddress(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String memberCode = this.getMemberCode(request);
        if (StringUtils.isBlank(memberCode)) {
            return new ModelAndView(login);
        }
		MemberCriteria memberCriteria = baseMemberService
				.queryMemberCriteriaByMemberCodeNsTx(Long.valueOf(memberCode));
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("criteria", memberCriteria);
		paraMap.put("showType", request.getParameter("type"));
		return new ModelAndView(updateAddress, paraMap);
	}

	/** 修改地址信息 */
	@OperatorPermission(operatPermission = "OPERATOR_ACCOUNT_UPDATE")
	public ModelAndView updateAddress(HttpServletRequest request,
			HttpServletResponse response, MemberCriteria memberCriteria)
			throws Exception {
		String msg = "";
		ValidatorDto vd= UpdateValidator.validate(memberCriteria);//校验表单提交的内容
		  if(vd.hasErrors()){
		        msg=vd.getError();
		        Map<String, Object> paraMap = new HashMap<String, Object>();
	            paraMap.put("criteria", memberCriteria);
	            paraMap.put("msgStr", msg);
	            return new ModelAndView(jumpView, paraMap);
		    }
		  
		String memberCode = this.getMemberCode(request);
        if (StringUtils.isBlank(memberCode)) {
            return new ModelAndView(login);
        }
		MemberInfoDto memberInfoDto = new MemberInfoDto();
		memberInfoDto.setZip(memberCriteria.getPostCode());
		memberInfoDto.setMobile(memberCriteria.getMobileNo());
		memberInfoDto.setTel(memberCriteria.getAptoticPhone());
		memberInfoDto.setProvince(memberCriteria.getProvince());
		memberInfoDto.setCity(memberCriteria.getCity());
		memberInfoDto.setAddr(memberCriteria.getAddress());
		memberInfoDto.setMemberCode(Long.valueOf(memberCode));
		ResultDto result = baseMemberService
				.doUpdateMemberInfoRnTx(memberInfoDto);
	
		if (StringUtils.isBlank(result.getErrorMsg())) { // 修改成功
			msg = MessageConvertFactory.getMessage("updateSuccess");
			return new ModelAndView("redirect:/app/accountInfo.htm");
		}
		Map<String, Object> paraMap = new HashMap<String, Object>();
		msg = MessageConvertFactory.getMessage("updateFailed");
		paraMap.put("iconNum", "2");
		paraMap.put("msgStr", msg);
		paraMap.put("jumpUrl", "/app/accountInfo.htm");

		return new ModelAndView(jumpView, paraMap);
	}

	/** 展示企业用户修改地址信息 */
	@OperatorPermission(operatPermission = "OPERATOR_ACCOUNT_UPDATE")
	public ModelAndView showUpdateCorpAddress(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String memberCode = this.getMemberCode(request);
        if (StringUtils.isBlank(memberCode)) {
            return new ModelAndView(login);
        }
		Map<String, Object> paraMap = new HashMap<String, Object>();
		// 获取企业会员基本信息
		EnterpriseBase enterpriseBase = enterpriseBaseService
				.findByMemberCode(Long.valueOf(memberCode));
		if (enterpriseBase != null) {
			paraMap.put("enterpriseBase", enterpriseBase);
		}
		// 获取企业会员联系信息
		EnterpriseContact enterpriseContact = enterpriseContactService
				.findByMemberCode(Long.valueOf(memberCode));
		if (enterpriseContact != null) {
			paraMap.put("enterpriseContact", enterpriseContact);
		}
		paraMap.put("showType", request.getParameter("type"));
		return new ModelAndView(updateCorpAddress, paraMap);
	}
	/** 获取当前登录者的memberCode */
	private String getMemberCode(HttpServletRequest request){
		LoginSession loginSession = SessionHelper.getLoginSession();
		if(loginSession != null && StringUtils.isNotBlank(loginSession.getMemberCode())){
			return loginSession.getMemberCode();
		}
		return "";
	}
	
	/** 修改企业用户联系地址 */
	@OperatorPermission(operatPermission = "OPERATOR_ACCOUNT_UPDATE")
	public ModelAndView updateCorpAddress(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String memberCode = this.getMemberCode(request);
        if (StringUtils.isBlank(memberCode)) {
            return new ModelAndView(login);
        }
        EnterpriseBase enterpriseBase = new EnterpriseBase();
        EnterpriseContact enterpriseContact = new EnterpriseContact();
        String enterpriseId = request.getParameter("enterpriseId");
        String contactId = request.getParameter("contactId");
        String region = request.getParameter("region");    
        String city = request.getParameter("city");
        String address = request.getParameter("address");
        String zip = request.getParameter("zip");
        String compayLinkerName = request.getParameter("compayLinkerName");
        String compayLinkerTel = request.getParameter("compayLinkerTel");

        // 修改企业会员联系信息
		int resultUpdate = 0;
        if(StringUtils.isNotBlank(enterpriseId)){
        	enterpriseBase.setEnterpriseId(Long.valueOf(enterpriseId));
        	enterpriseBase.setRegion(region);
        	enterpriseBase.setCity(city);
        	// 修改基本信息
        	resultUpdate = enterpriseBaseService.updateBaseInfo(enterpriseBase);
        }
        if(StringUtils.isNotBlank(contactId)){
        	enterpriseContact.setContactId(Long.valueOf(contactId));
        	enterpriseContact.setZip(zip);
        	enterpriseContact.setAddress(address);
          	enterpriseContact.setCompayLinkerName(compayLinkerName);
          	enterpriseContact.setCompayLinkerTel(compayLinkerTel);
        	// 修改联系信息
        	resultUpdate = resultUpdate + enterpriseContactService.updateContact(enterpriseContact);
        }
		
		// 更新企业会员联系信息
		String msg = "";
		if (resultUpdate > 0) { // 修改成功.
			msg = MessageConvertFactory.getMessage("updateSuccess");
			return new ModelAndView("redirect:/corp/accountInfo.htm");
		}
		Map<String, Object> paraMap = new HashMap<String, Object>();
		msg = MessageConvertFactory.getMessage("updateFailed");
		paraMap.put("iconNum", "2");
		paraMap.put("msgStr", msg);
		paraMap.put("jumpUrl", "/corp/accountInfo.htm");

		return new ModelAndView(jumpView, paraMap);
	}

	/** 更新企业会员联系信息 */
	private int updateEnterpriseContact(HttpServletRequest request) {

		// 联系邮箱地址
		String email = request.getParameter("email");
		// 联系手机号
		String compayLinkerTel = request.getParameter("compayLinkerTel");
		// 联系电话
		String tel = request.getParameter("tel");
		// 传真
		String fax = request.getParameter("fax");
		// 网址
		String website = request.getParameter("website");
		// 基本信息主键ID
		String enterpriseId = request.getParameter("enterpriseId");
		if (StringUtils.isNotBlank(website)
				&& StringUtils.isNotBlank(enterpriseId)) {
			EnterpriseBase enterpriseBase = new EnterpriseBase();
			// 更新基本信息表中的网址
			enterpriseBase.setWebsite(website);
			enterpriseBase.setEnterpriseId(Long.valueOf(enterpriseId));
			return enterpriseBaseService.updateBaseInfo(enterpriseBase);
		}
		// 联系信息主键ID
		String contactId = request.getParameter("contactId");

		if ((StringUtils.isNotBlank(email) || StringUtils.isNotBlank(compayLinkerTel)
				|| StringUtils.isNotBlank(tel) || StringUtils.isNotBlank(fax))
				&& StringUtils.isNotBlank(contactId)) {
			EnterpriseContact enterpriseContact = new EnterpriseContact();
			enterpriseContact.setEmail(email);
			enterpriseContact.setCompayLinkerTel(compayLinkerTel);
			enterpriseContact.setFax(fax);
			enterpriseContact.setTel(tel);

			enterpriseContact.setContactId(Long.valueOf(contactId));
			return enterpriseContactService.updateContact(enterpriseContact);
		}
		return 0;

	}

	public void setUpdateAccountInfo(String updateAccountInfo) {
		this.updateAccountInfo = updateAccountInfo;
	}

	public void setJumpView(String jumpView) {
		this.jumpView = jumpView;
	}

	public void setEnterpriseContactService(
			EnterpriseContactService enterpriseContactService) {
		this.enterpriseContactService = enterpriseContactService;
	}

	public void setBaseMemberService(MemberService baseMemberService) {
		this.baseMemberService = baseMemberService;
	}

	public void setEnterpriseBaseService(
			EnterpriseBaseService enterpriseBaseService) {
		this.enterpriseBaseService = enterpriseBaseService;
	}

	public void setUpdateAddress(String updateAddress) {
		this.updateAddress = updateAddress;
	}

	public void setUpdateCorpAddress(String updateCorpAddress) {
		this.updateCorpAddress = updateCorpAddress;
	}

	public void setLogin(String login) {
		this.login = login;
	}

}
